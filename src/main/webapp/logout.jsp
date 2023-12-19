<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  // Получаем текущую сессию и инвалидируем её (завершаем сеанс)
  session = request.getSession(false);
  if (session != null) {
    session.invalidate();
  }
%>


<!DOCTYPE html>
<html lang="eng">
<head>
  <title>Logout</title>
  <meta charset="UTF-8">
  <link href="styles/registration.css" rel="stylesheet">
</head>


<body class="container">
<div class="left-side">
  <h2>LOGGED OUT</h2>
  <h3>You have been successfully logged out.</h3>
  <p><a href="/login.jsp">SIGN IN</a></p>
</div>
<div class="right-side"></div>
</body>
</html>


