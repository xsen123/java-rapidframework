<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPY html>
<html>
<head>
    <title>sample/exists</title>
</head>
<body>
<h2>Hello, Rapid Framework!</h2>
<div>
    ${name}
    <c:if test="${exists==true}">
        存在
    </c:if>
    <c:if test="${exists==false}">
        不存在
    </c:if>

</div>
</body>
</html>
