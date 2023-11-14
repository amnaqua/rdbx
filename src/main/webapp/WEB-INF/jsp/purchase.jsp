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
      <label for="chk">Purchase</label>
      <form class="purchase" action="${pageContext.request.contextPath}/purchase" method="post">
        <input type="input" name="amount" placeholder="Amount"/>
        <input type="input" name="qrCode" placeholder="QR link"/>
        <input type="hidden" name="action" value="purchase"/>
        <button type="submit">Покупка</button>
        <a href="/" class="home-button">Главная</a>
      </form>
    </div>
  </div>
</body>
</html>