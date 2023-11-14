<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Log in with your account</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/table.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/home_button.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/input.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/forms.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="${contextPath}/resources/js/confirm.js"></script>
  <script src="${contextPath}/resources/js/input.js"></script>
</head>

<body>
<div>
  <a href="/" class="home-button">Главная</a>
  <table class="table">
    <thead>
    <th>ID</th>
    <th>Email</th>
    <th>Password</th>
    <th>Phone Number</th>
    <th>Purchase Sum</th>
    <th>Visits Count</th>
    <th>Roles</th>
    </thead>
    <c:forEach items="${managerUser}" var="user">
      <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.password}</td>
        <td>${user.phoneNumber}</td>
        <td>${user.purchaseSum}</td>
        <td>${user.visitsCount}</td>
        <td>
          <c:forEach items="${user.roles}" var="role">${role.name}; </c:forEach>
        </td>
      </tr>
    </c:forEach>
  </table>
  <form class="addVisit" action="${pageContext.request.contextPath}/manager/addVisit" method="post">
        <button type="submit">Add visit</button>
        <input type="input" name="userId" placeholder="User ID"/>
        <input type="input" name="amount" placeholder="Amount"/>
        <input type="hidden" name="action" value="add"/>
  </form>
  <table class="table">
    <thead>
      <th>ID</th>
      <th>Amount</th>
      <th>QR</th>
    </thead>
    <c:forEach items="${allPurchase}" var="purchase">
      <tr>
        <td>${purchase.id}</td>
        <td>${purchase.amount}</td>
        <td><img src="data:image/png;base64,${purchase.qrCode}" alt="QR Code"></td>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>