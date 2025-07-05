<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.sena.BusinessAssistantSpring.model.User" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Business Assistant</title>

    <!-- Bootstrap 5 CSS desde CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">

    <!-- Estilos personalizados -->
    <style>
        body {
            display: flex;
            min-height: 100vh;
            flex-direction: column;
        }
        .main-content {
            flex: 1;
            display: flex;
        }
        .sidebar {
            width: 200px;
            background-color: #343a40;
            padding-top: 20px;
            min-height: 100vh;
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
    </style>
</head>
<body>
<div class="main-content">
