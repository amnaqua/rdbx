<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Регистрация</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/registration.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/home_button.css">
</head>

<body>
  <div class="main">
    <input type="checkbox" id="chk">
    <div class="signup">
      <label for="chk">Sign Up</label>
      <form:form method="POST" modelAttribute="userForm">
        <div>
          <form:input type="text" path="username" placeholder="Email" autofocus="true"></form:input>
          <!-- <c:if test="${usernameError != null}">
            <div class="error">${usernameError}</div>
          </c:if> -->
        </div>
        <div>
          <form:input type="text" path="phoneNumber" placeholder="Phone number"></form:input>
        </div>
        <div>
          <form:input type="password" path="password" placeholder="Password"></form:input>
        </div>
        <div>
          <form:input type="password" path="passwordConfirm" placeholder="Confirm your password"></form:input>
        </div>
        <button type="submit">Зарегистрироваться</button>
        <a href="/" class="home-button">Главная</a>
      </form:form>
    </div>
  </div>
</body>
</html>