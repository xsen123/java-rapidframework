<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPY html>
<html>
<head>
    <title>index</title>
</head>
<body>
<h2>Hello, Rapid Framework!</h2>
<div>
    <c:if test="${empty login}">
        用户尚未登录&nbsp;&nbsp;<a href="/login.do">立即登录</a>
    </c:if>
    <c:if test="${not empty login}">
        用户已登录
    </c:if>
    <br/><br/>
    <a href="/sample/index.do">用户总数</a><br/><br/>
    <a href="/sample/count.do?name=Jason">根据姓名统计数量</a><br/><br/>
    <a href="/sample/exists.do?name=Jason">姓名是否存在</a><br/><br/>
    <c:if test="${not empty login}">
        <a href="/sample/insert.do">新增用户</a><br/><br/>
        <a href="/sample/view.do?id=2">根据用户ID查看：view</a>&nbsp;&nbsp;<a href="/sample/show.do?id=2">根据用户ID查看：show</a>
    </c:if>
</div>
</body>
</html>
