<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList,java.util.List,java.io.BufferedReader,java.io.FileReader" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.User" %>
<%@ page import="repository.DbHandler" %>
<%@ page import="model.Project" %>
<!DOCTYPE html>
<html lang="eng">
<head>
    <title>Home</title>
    <meta charset="UTF-8">
    <link href="styles/home.css" rel="stylesheet">
</head>
<body class="container">
<div class="left-side">

    <h2>Hello, <%=session.getAttribute("loggedInUser")%></h2>
    <% User user = DbHandler.getUserByUsername(session.getAttribute("loggedInUser").toString());%>

    <ul>
        <li><a href="home.jsp" class="nav-link">Home</a></li>
        <li style="color:blueviolet">
            <!-- Используйте JavaScript для разворачивания и сворачивания списка -->
            <a href="#" class="nav-link" onclick="toggleProjects()">Projects</a>
            <ul id="projectsList" style="display: none;">
                <% for (Project project : user.getProjects()){ %>
                <li><a href="/project/<%=project.getId()%>" class="nav-link"><%=project.getTitle()%></a></li>

                <%} %>
            </ul>
        </li>
        <li><a href="settings.jsp" class="nav-link">Settings</a></li>
    </ul>

    <script>
        function toggleProjects() {
            var projectsList = document.getElementById("projectsList");
            if (projectsList.style.display === "none") {
                projectsList.style.display = "block";
            } else {
                projectsList.style.display = "none";
            }
        }
    </script>
</div>
<div class="right-side">

</div>
</body>
</html>


