<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList,java.util.List,java.io.BufferedReader,java.io.FileReader" %>
<%@ page import="model.User" %>
<%@ page import="handler.DbHandler" %>
<%@ page import="model.Project" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="eng">
<head>
    <title>Home</title>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/styles/project.css" rel="stylesheet">
</head>
<body class="container">

<div class="left-side">
    <h2>Hello, <%=session.getAttribute("loggedInUser")%>
    </h2>
    <% User user = DbHandler.getUserByUsername(session.getAttribute("loggedInUser").toString());%>
    <ul>
        <li><a href="/home.jsp" class="nav-link">Home</a></li>
        <li style="color:blueviolet"><a href="projects.jsp" class="nav-link">Projects </a></li>
        <ul>
            <c:forEach var="pr" items="${userProjects}">
                <li><a href="/project/${pr.getId()}" class="nav-link">${pr.getTitle()}</a><a
                        onclick="deleteProjectDialog(${pr.getId()})"> 𝕏</a></li>
            </c:forEach>
            <li><a onclick="createProjectDialog()" class="nav-link">Create new project</a></li>
        </ul>
        <li><a href="/settings.jsp" class="nav-link">Settings</a></li>
    </ul>
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
        if (xhr.status === 200 && xhr.responseText !== "-1") {
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
        if (xhr.status === 200 && xhr.responseText !== "-1") {
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

<div style="flex: 2">
    <div class="drag-container">
        <ul class="drag-list">
            <!-- To Be Done Column -->
            <li class="drag-column to-do-column">
					<span class="header">
						<h1>To Do</h1>
					</span>
                <!-- To Be Done Content -->
                <div id="to-do-content" class="custom-scroll-todo">
                    <ul
                            class="drag-item-list secret_list"
                            id="to-do-list"
                            ondrop="drop(event)"
                            ondragover="allowDrop(event)"
                            ondragenter="dragEnter(0)"
                    ></ul>
                </div>
                <!-- Add Button Group -->
                <div class="add-btn-group">
                    <div class="add-btn" onclick="showInputBox(0)">
                        <span class="plus-sign">+</span>
                        <span>Add Item</span>
                    </div>
                    <div class="add-btn solid" onclick="hideInputBox(0)">
                        <span>Save Item</span>
                    </div>
                </div>
                <div class="add-container">
                    <div class="add-item" contenteditable="true"></div>
                </div>
            </li>
            <!-- Doing Column -->
            <li class="drag-column doing-column">
					<span class="header">
						<h1>Doing</h1>
					</span>
                <!-- Doing Content -->
                <div id="doing-content" class="custom-scroll-doing">
                    <ul
                            class="drag-item-list secret_list"
                            id="doing-list"
                            ondrop="drop(event)"
                            ondragover="allowDrop(event)"
                            ondragenter="dragEnter(1)"
                    ></ul>
                </div>
                <!-- Add Button Group -->
                <div class="add-btn-group">
                    <div class="add-btn" onclick="showInputBox(1)">
                        <span class="plus-sign">+</span>
                        <span>Add Item</span>
                    </div>
                    <div class="add-btn solid" onclick="hideInputBox(1)">
                        <span>Save Item</span>
                    </div>
                </div>
                <div class="add-container">
                    <div class="add-item" contenteditable="true"></div>
                </div>
            </li>
            <!-- Done Column -->
            <li class="drag-column done-column">
					<span class="header">
						<h1>Done</h1>
					</span>
                <!-- Done Content -->
                <div id="done-content" class="custom-scroll-done">
                    <ul
                            class="drag-item-list secret_list"
                            id="done-list"
                            ondrop="drop(event)"
                            ondragover="allowDrop(event)"
                            ondragenter="dragEnter(2)"
                    ></ul>
                </div>
                <!-- Add Button Group -->
                <div class="add-btn-group">
                    <div class="add-btn" onclick="showInputBox(2)">
                        <span class="plus-sign">+</span>
                        <span>Add Item</span>
                    </div>
                    <div class="add-btn solid" onclick="hideInputBox(2)">
                        <span>Save Item</span>
                    </div>
                </div>
                <div class="add-container">
                    <div class="add-item" contenteditable="true"></div>
                </div>
            </li>

        </ul>
    </div>
    <script>
        class TodoTask {
            constructor(id, name, status) {
                this.id = id;
                this.name = name;
                this.status = status;
            }

            toString() {
                return this.name;
            }
        }

        let projectId = ${project.getId()};

        let allTasks = [
            <c:forEach var="task" items="${project.getTasks()}">
            new TodoTask(${task.getId()}, '${task.getTitle()}', '${task.getStatus()}'),
            </c:forEach>
        ]
        let backlogListArray = [];
        let progressListArray = [];
        let completeListArray = [];
        let listArrays = [];

        function getSavedColumns() {
            allTasks.forEach(function (todo) {
                switch (todo.status) {
                    case '0':
                        backlogListArray.push(todo);
                        break;
                    case '1':
                        progressListArray.push(todo);
                        break;
                    case '2':
                        completeListArray.push(todo);
                        break;
                }
            })
        }

    </script>
    <script src="/script.js"></script>
    <h6>Enter recipient name:</h6>
    <form action="/shareProject" method="post">
        <input type="hidden" id="projectId" name="projectId" value="${project.getId()}">
        <label for="recipientName">Enter recipient name:</label><br>
        <input style="width: calc(20% - 20px);
                    padding: 10px;
                    margin-bottom: 15px;
                    border: 1px solid #ccc;"
               type="text" id="recipientName" name="recipientName"><br><br>
        <input style="
                color: white;
                background-color: blueviolet;
                width: calc(30% - 20px);
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 4px;"
               type="submit" value="Share">
    </form>


</div>
</body>
</html>


