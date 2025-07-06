<style>
    body {
        display: flex;
        min-height: 100vh;
    }

    .main-content {
        display: flex;
        flex: 1;
    }

    /* Contenedor lateral con logo y sidebar */
    .sidebar-container {
        width: 200px;
        background-color: #343a40;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .logo-container {
        background-color: #343a40;
		padding: 10px;
        width: 100%;
        text-align: center;
        border-bottom: 1px solid #495057;
    }

    .logo-container img {
        max-height: 60px;
    }

    .sidebar {
        width: 100%;
        padding-top: 20px;
    }

    .sidebar a {
        color: #fff;
        padding: 10px 20px;
        display: block;
        text-decoration: none;
    }

    .sidebar a:hover {
        background-color: #495057;
    }

    .container-fluid {
        flex: 1;
        padding: 20px;
		
    }
</style>

<!-- Contenedor principal -->
<div class="main-content">
    <!-- Contenedor del logo y la barra lateral -->
    <div class="sidebar-container">
        <!-- Logo -->
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Logo">
        </div>

        <!-- Sidebar -->
        <div class="sidebar text-center">
            <a href="${pageContext.request.contextPath}/categories">Categories</a>
            <a href="${pageContext.request.contextPath}/users">Users</a>
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>

    <!-- Contenido principal -->
    <div class="container-fluid">
        <!-- Aquí va tu contenido -->
