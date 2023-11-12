<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
  <title>Главная</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <link rel="stylesheet" type="text/css" href="${contextPath}/">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/index.css">
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/background.css">
</head>
<body>
<div>
  <div class="slider-thumb">
      <sec:authorize access="isAuthenticated()">
          <h3>${pageContext.request.userPrincipal.name}</h3>
      </sec:authorize>
      <sec:authorize access="isAnonymous()">
          <h3 style="display: none;"></h3>
      </sec:authorize>
      <sec:authorize access="isAnonymous()">
          <h4><a href="/login" class="button">Войти</a></h4>
          <h4><a href="/registration" class="button">Зарегистрироваться</a></h4>
      </sec:authorize>
      <sec:authorize access="isAuthenticated()">
          <h4><a href="/logout" class="button">Выйти</a></h4>
      </sec:authorize>
      <h4><a href="/admin" class="button">Панель администратора</a></h4>
      <h4><a href="/manager" class="button">Панель менеджера</a></h4>
  </div>
</div>
</body>
</html>