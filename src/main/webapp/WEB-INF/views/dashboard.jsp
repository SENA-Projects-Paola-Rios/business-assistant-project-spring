<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sena.BusinessAssistantSpring.model.User" %>

<jsp:include page="partials/header.jsp" />
<jsp:include page="partials/sidebar.jsp" />

<%
    User loggedUser = (User) request.getAttribute("loggedUser");
%>

<!-- Contenido principal -->
<div class="main-content" style="margin-left: 10px; padding: 20px;">
    <div class="container-fluid">
        <h1 class="mt-4">Dashboard</h1>
        <p>Welcome to the Business Assistant system!</p>

        <div class="alert alert-success" role="alert">
           You are now logged in as <strong>${sessionScope.loggedUser.name}</strong>.
        </div>
    </div>
</div>

<jsp:include page="partials/footer.jsp" />
