<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList,java.util.List,java.io.BufferedReader,java.io.FileReader" %>
<%@ page import="handler.DbHandler" %>
<%@ page import="model.User" %>
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
        <li><a href="/home.jsp" class="nav-link">Home</a></li>
        <li>
            <a href="#" class="nav-link" onclick="toggleProjects()">Projects</a>
            <ul id="projectsList" style="display: none;">
                <% for (Project project : user.getProjects()){ %>
                <li><a href="/project/<%=project.getId()%>" class="nav-link"><%=project.getTitle()%></a><a onclick="deleteProjectDialog(<%=project.getId()%>)">         𝕏</a></li>
                <% } %>
                <li><a onclick="createProjectDialog()" class="nav-link">Create new project</a></li>
            </ul>
        </li>
        <li ><a href="/settings.jsp" class="nav-link">Settings</a></li>
    </ul>

    <script>
        function deleteProjectDialog(projectId) {
            const url = '/projects';

            let params = {
                projectId: projectId,
                _method: "DELETE",
            };
            const searchParams = new URLSearchParams(params).toString();

            const xhr = new XMLHttpRequest();

            xhr.open('POST', url, false); // Synchronous request

            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); // Set the necessary Content-Type
            xhr.send(searchParams);
            if(xhr.status===200 && xhr.responseText !== "-1"){
                window.location.href = '/home.jsp';
            } else alert(":(");


        }

    </script>

    <script>
        function createProjectDialog() {
            const url = '/projects';
            const projectTitle = prompt(
                "Enter new project title"
            );
            let params = {
                projectTitle: projectTitle,
                owner: "<%=user.getUsername()%>"
            };
            const searchParams = new URLSearchParams(params).toString();

            const xhr = new XMLHttpRequest();

            xhr.open('POST', url, false); // Synchronous request

            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); // Set the necessary Content-Type
            xhr.send(searchParams);
            if(xhr.status===200 && xhr.responseText !== "-1"){
                window.location.href = '/project/' + xhr.responseText;
            } else alert(":(");


        }

    </script>

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

    <script>
        function submitForm() {
            document.getElementById("logoutForm").submit();
        }
    </script>

    <div class="my_button">
        <form id="logoutForm" action="/logout.jsp" method="post">
            <input type="button" value="LOGOUT" onclick="submitForm()"/>
        </form>
    </div>
</div>
<div class="right-side">
    <form action="changePassword" method="post">
        <h2>Change password</h2>
        <div class="input-group">
            <label for="currentPassword">Current password</label>
            <input
                    type="password"
                    id="currentPassword"
                    name="currentPassword"
            />
        </div>
        <div class="input-group">
            <label for="password">New Password   </label>
            <input
                    type="password"
                    id="password"
                    name="password"
            />
        </div>
        <div class="input-group">
            <label for="confirmPassword">Confirm Password</label>
            <input
                    type="password"
                    id="confirmPassword"
                    name="confirmPassword"
            />
        </div>
        <div>
            <button type="submit">Save Changes</button>
        </div>
    </form>
</div>
</body>
</html>


