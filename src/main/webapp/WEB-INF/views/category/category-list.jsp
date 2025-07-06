<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.sena.BusinessAssistantSpring.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sena.BusinessAssistantSpring.model.Category" %>

<%
    HttpSession sesion = request.getSession(false);
    User loggedUser = (sesion != null) ? (User) sesion.getAttribute("loggedUser") : null;

    if (loggedUser == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    List<Category> categoryList = (List<Category>) request.getAttribute("categories");
%>

<jsp:include page="../partials/header.jsp" />
<jsp:include page="../partials/sidebar.jsp" />

<div class="main-content" style="margin-left: 10px; padding: 20px;">
    <div class="container-fluid">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2>Category List</h2>
            <button class="btn btn-primary" onclick="openNewCategoryModal()">Add Category</button>
        </div>

        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (categoryList != null && !categoryList.isEmpty()) {
                        for (Category category : categoryList) {
                %>
                <tr>
                    <td><%= category.getId() %></td>
                    <td><%= category.getName() %></td>
                    <td><%= category.getDescription() %></td>
                    <td>
                        <button class="btn btn-success btn-sm" onclick="viewCategory(<%= category.getId() %>)">View</button>
                        <button class="btn btn-warning btn-sm" onclick="editCategory(<%= category.getId() %>)">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="confirmDelete(<%= category.getId() %>)">Delete</button>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="4" class="text-center">No categories found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="category-modal.jsp" />

<div class="modal fade" id="viewCategoryModal" tabindex="-1" aria-labelledby="viewCategoryModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="viewCategoryModalLabel">Category Details</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p><strong>ID:</strong> <span id="viewCategoryId"></span></p>
        <p><strong>Name:</strong> <span id="viewCategoryName"></span></p>
        <p><strong>Description:</strong> <span id="viewCategoryDescription"></span></p>
      </div>
    </div>
  </div>
</div>

<script>
    function confirmDelete(id) {
        if (confirm("Are you sure you want to delete this category?")) {
            window.location.href = "${pageContext.request.contextPath}/categories/delete/" + id;
        }
    }

    function viewCategory(id) {
        fetch("${pageContext.request.contextPath}/categories/json/" + id)
            .then(response => response.json())
            .then(data => {
                document.getElementById("viewCategoryId").textContent = data.id;
                document.getElementById("viewCategoryName").textContent = data.name;
                document.getElementById("viewCategoryDescription").textContent = data.description;
                new bootstrap.Modal(document.getElementById("viewCategoryModal")).show();
            });
    }

    function editCategory(id) {
        fetch("${pageContext.request.contextPath}/categories/json/" + id)
            .then(response => response.json())
            .then(data => {
                document.getElementById("categoryId").value = data.id;
                document.getElementById("categoryName").value = data.name;
                document.getElementById("categoryDescription").value = data.description;
                new bootstrap.Modal(document.getElementById("categoryModal")).show();
            });
    }

    function openNewCategoryModal() {
        document.getElementById("categoryId").value = "";
        document.getElementById("categoryName").value = "";
        document.getElementById("categoryDescription").value = "";
        new bootstrap.Modal(document.getElementById("categoryModal")).show();
    }

    function submitCategoryForm(event) {
        event.preventDefault();

        const id = document.getElementById("categoryId").value;
        const name = document.getElementById("categoryName").value;
        const description = document.getElementById("categoryDescription").value;

        fetch("${pageContext.request.contextPath}/categories/save-ajax", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                id: id ? parseInt(id) : 0,
                name: name,
                description: description
            })
        })
        .then(response => {
            if (!response.ok) return response.json().then(data => { throw data; });
            return response.text();
        })
        .then(() => {
            alert("Category saved successfully!");
            location.reload();
        })
        .catch(errors => {
            let msg = "Validation errors:\n";
            errors.forEach(e => msg += "- " + e.defaultMessage + "\n");
            alert(msg);
        });
    }
</script>

<jsp:include page="../partials/footer.jsp" />
