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
  <h2>SIGN IN</h2>
  <form action="login" method="POST">
    <div class="input-group">
      <label for="username" >Login:</label>
      <input type="text" name="username" id="username">
    </div>
    <div class="input-group">
      <label for="password">Password:</label>
      <input type="password" name="password" id="password">
    </div>
    <div class="my_button">
      <input type="submit" value="SIGN IN" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
    </div>

  </form>
  <p>Don't have an account? <a href="registration.jsp">Sign up</a></p>
</div>
<div class="right-side"></div>
</body>
</html>

