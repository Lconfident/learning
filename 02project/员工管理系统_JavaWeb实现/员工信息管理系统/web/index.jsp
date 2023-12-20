<%--
  Created by IntelliJ IDEA.
  User: www
  Date: 2023/6/18
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>显示登录的用户信息</title>
</head>
<body>
<br/>
<center>欢迎光临</center>
<br/>
<br/>
<c:choose>
  <c:when test="${sessionScope.user==null}">
    <a href="${pageContext.request.contextPath}/login.jsp">用户登录</a>
  </c:when>
  <c:otherwise>
    欢迎你，${sessionScope.user.employee_id}!
    <a href="${pageContext.request.contextPath}/LogoutServlet">注销</a>
  </c:otherwise>
</c:choose>
<hr/>
</body>
</html>
