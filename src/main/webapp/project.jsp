<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList,java.util.List,java.io.BufferedReader,java.io.FileReader" %>
<%@ page import="model.User" %>
<%@ page import="repository.DbHandler" %>
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
    <h1>${project.getTitle()}</h1>
    <h2>Hello, <%=session.getAttribute("loggedInUser")%></h2>
    <ul>
        <li><a href="home.jsp" class="nav-link">Home</a></li>
        <li style="color:blueviolet"><a href="projects.jsp" class="nav-link">Projects </a></li>
        <ul>
            <c:forEach var="pr" items="${userProjects}">
            <li style="${project.getId()==pr.getId() ? 'color:blueviolet' : ''}"><a href="/project/${pr.getId()}" class="nav-link">${pr.getTitle()}</a></li>
            </c:forEach>
        </ul>
        <li><a href="settings.jsp" class="nav-link">Settings</a></li>
    </ul>
</div>

<%--<script>--%>
<%--    progressListArray = ["Work on Droppi project", "Listen to Spotify"];--%>
<%--    completeListArray = ["Submit a PR", "Review my projects code"];--%>
<%--    onHoldListArray = ["Get a girlfriend"];--%>
<%--</script>--%>
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
    <script src="script.js"></script>
</div>
</body>
</html>


