<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Log in with your account</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/table.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/input.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/home_button.css">
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
    <th></th>
    </thead>
    <c:forEach items="${allUsers}" var="user">
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
        <td>
          <form class="deleteUser" action="${pageContext.request.contextPath}/admin/delete" method="post">
            <input type="hidden" name="userId" value="${user.id}"/>
            <input type="hidden" name="action" value="delete"/>
            <button type="submit">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
  </table>
  <div class="forms">
    <form class="addUser" action="${pageContext.request.contextPath}/admin/add" method="post">
      <button type="submit">Add User</button>
      <input type="input" name="userId" placeholder="User ID"/>
      <input type="input" name="username" placeholder="Username"/>
      <input type="input" name="password" placeholder="Password"/>
      <input type="input" name="phoneNumber" placeholder="Phone Number"/>
      <input type="input" name="purchaseSum" placeholder="Purchase Sum"/>
      <input type="input" name="visitsCount" placeholder="Visits Count"/>
      <input type="input" name="roles" placeholder="ROLE_NAME1;ROLE_NAME2..."/>
      <input type="hidden" name="action" value="add"/>
    </form>
    <form class="updateUser" action="${pageContext.request.contextPath}/admin/update" method="post">
      <button type="submit">Update User</button>
      <input type="input" name="userId" placeholder="User ID"/>
      <input type="input" name="username" placeholder="Username"/>
      <input type="input" name="password" placeholder="Password"/>
      <input type="input" name="phoneNumber" placeholder="Phone Number"/>
      <input type="input" name="purchaseSum" placeholder="Purchase Sum"/>
      <input type="input" name="visitsCount" placeholder="Visits Count"/>
      <input type="input" name="roles" placeholder="ROLE_NAME1;ROLE_NAME2..."/>
      <input type="hidden" name="action" value="update"/>
    </form>
  </div>
</div>
</body>
</html>