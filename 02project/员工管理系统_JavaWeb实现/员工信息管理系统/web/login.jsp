<%--
  Created by IntelliJ IDEA.
  User: www
  Date: 2023/6/18
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
</head>
<body style="text-align: center;">
<form action="${pageContext.request.contextPath }/LoginServlet" method="post">
    <table border="1" width="600px" cellspacing="0" cellpadding="0" align="center">
        <tr>
            <td height="30" align="center">用户名：</td>
            <td>&nbsp;&nbsp;
                <input type="text" name="employee_id"/>${errorMsg}</td>
        </tr>
        <tr>
            <td height="30" align="center">密&nbsp;码：</td>
            <td>&nbsp;&nbsp;
                <input type="password" name="password">
            </td>
        </tr>
        <tr>
            <td height="35" align="center">自动登录时间</td>
            <td>
                <input type="radio" name="autologin" value="${60*60*24*31}">一个月
                <input type="radio" name="autologin" value="${60*60*24*31*3}">三个月
                <input type="radio" name="autologin" value="${60*60*24*31*6}">半年
                <input type="radio" name="autologin" value="${60*60*24*31*12}">一年
            </td>
        </tr>
        <tr>
            <td height="30" colspan="2" align="center">
                <input type="submit" value="登录"/>
                &nbsp;&nbsp;
                <input type="reset" value="重置"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
