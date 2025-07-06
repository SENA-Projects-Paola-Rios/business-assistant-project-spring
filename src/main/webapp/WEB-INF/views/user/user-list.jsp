<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sena.BusinessAssistantSpring.model.User" %>

<jsp:include page="../partials/header.jsp" />
<jsp:include page="../partials/sidebar.jsp" />

<%
    List<User> userList = (List<User>) request.getAttribute("users");
%>

<div class="main-content" style="margin-left: 10px; padding: 20px;">
    <div class="container-fluid">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h2>User List</h2>
            <button class="btn btn-primary" onclick="openNewUserModal()">Add User</button>
        </div>

        <table class="table table-striped table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (userList != null && !userList.isEmpty()) {
                        for (User user : userList) {
                %>
                <tr>
                    <td><%= user.getId() %></td>
                    <td><%= user.getName() %></td>
                    <td><%= user.getEmail() %></td>
                    <td><%= user.getRole() %></td>
                    <td>
                        <button class="btn btn-info btn-sm" onclick="viewUser(<%= user.getId() %>)">View</button>
                        <button class="btn btn-warning btn-sm" onclick="editUser(<%= user.getId() %>)">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="confirmDelete(<%= user.getId() %>)">Delete</button>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center">No users found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="user-modal.jsp" />

<!-- Modal de vista -->
<div class="modal fade" id="viewUserModal" tabindex="-1" aria-labelledby="viewUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="viewUserModalLabel">User Details</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p><strong>ID:</strong> <span id="viewUserId"></span></p>
                <p><strong>Name:</strong> <span id="viewUserName"></span></p>
                <p><strong>Email:</strong> <span id="viewUserEmail"></span></p>
                <p><strong>Role:</strong> <span id="viewUserRole"></span></p>
            </div>
        </div>
    </div>
</div>

<script>
    function confirmDelete(id) {
        if (confirm("Are you sure you want to delete this user?")) {
            window.location.href = "${pageContext.request.contextPath}/users/delete/" + id;
        }
    }

    function viewUser(id) {
        fetch("${pageContext.request.contextPath}/users/json/" + id)
            .then(res => res.json())
            .then(data => {
                document.getElementById("viewUserId").textContent = data.id;
                document.getElementById("viewUserName").textContent = data.name;
                document.getElementById("viewUserEmail").textContent = data.email;
                document.getElementById("viewUserRole").textContent = data.role;
                new bootstrap.Modal(document.getElementById("viewUserModal")).show();
            });
    }

    function editUser(id) {
        fetch("${pageContext.request.contextPath}/users/json/" + id)
            .then(res => res.json())
            .then(data => {
                document.getElementById("userId").value = data.id;
                document.getElementById("name").value = data.name;
                document.getElementById("email").value = data.email;
                document.getElementById("password").value = "";
                document.getElementById("role").value = data.role;
                new bootstrap.Modal(document.getElementById("userModal")).show();
            });
    }

    function openNewUserModal() {
        document.getElementById("userId").value = "";
        document.getElementById("name").value = "";
        document.getElementById("email").value = "";
        document.getElementById("password").value = "";
        document.getElementById("role").value = "";
        new bootstrap.Modal(document.getElementById("userModal")).show();
    }

    function submitUserForm(event) {
        event.preventDefault();

        const id = document.getElementById("userId").value;
        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const role = document.getElementById("role").value;

        fetch("${pageContext.request.contextPath}/users/save-ajax", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                id: id ? parseInt(id) : 0,
                name: name,
                email: email,
                password: password,
                role: role
            })
        })
        .then(response => {
            if (!response.ok) return response.json().then(data => { throw data; });
            return response.text();
        })
        .then(() => {
            alert("User saved successfully!");
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
