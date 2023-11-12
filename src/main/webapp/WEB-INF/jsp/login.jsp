<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Log in with your account</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/registration.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/home_button.css">
</head>

<body>
<sec:authorize access="isAuthenticated()">
  <% response.sendRedirect("/"); %>
</sec:authorize>

<div class="main">
  <div class="signup">
    <label for="chk">Log in</label>
    <form method="POST" action="/login">
      <input name="username" type="text" placeholder="Email" autofocus="true"/>
      <input name="password" type="password" placeholder="Password"/>
      <button type="submit">Log In</button>
    </form>
    <a href="/" class="home-button">Главная</a>
  </div>
</div>

</body>
</html>
