<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPY html>
<html>
<head>
    <title>sample/view</title>
</head>
<body>
<h2>Hello, Rapid Framework!</h2>
<div>
    <c:if test="${sample!=null}">
        ${sample.name}
    </c:if>
</div>
</body>
</html>
