<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList,java.util.List,java.io.BufferedReader,java.io.FileReader" %>
<!DOCTYPE html>
<html lang="eng">
<head>
    <title>Registration</title>
    <meta charset="UTF-8">
    <link href="styles/registration.css" rel="stylesheet">
</head>


<body class="container">
<div class="left-side">
    <h2>SIGN UP</h2>
    <form action="registration" method="POST">
        <div class="input-group">
            <label for="username" >Login:</label>
            <input type="text" name="username" id="username">
        </div>
        <div class="input-group">
            <label for="password">Password:</label>
            <input type="password" name="password" id="password">
        </div>
        <div class="input-group">
            <label for="confPassword">Confirm password:</label>
            <input type="password" name="confPassword" id="confPassword">
        </div>
        <div class="my_button">
            <input type="submit" value="Войти" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
        </div>
        <%
            String responseString = (String) request.getAttribute("responseString");
            if (responseString != null && !responseString.isEmpty()) {
                if (responseString.equals("Registration succesful!")) response.sendRedirect("login.jsp");
            }
        %>
    </form>

    <p>Already have an account? <a href="login.jsp">Sign in</a></p>
</div>
<div class="right-side"></div>
</body>
</html>

