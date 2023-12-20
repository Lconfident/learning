### 目标：Filter在Cookie自动登录中的应用

使用Cookie可以实现自动登录，担当客户端访问服务器的Servlet时，Servlet需要对所有用户的Cookie信息进行校验，这样会导致在Servlet写大量重复的代码。通过注册过滤器完成对用户Cookie信息的校验，可以减轻服务器压力。一旦请求通过Filter，就相当于用户信息校验通过，Servlet程序根据获取到的用户信息就可以实现自动登录。

### 编写User类

用户封装用户的信息

```java
package cn.itcast.chapter09.entity;

public class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

### 实现登录页面和首页

编写login.jsp，一个用户登录的表单，需要填写用户名和密码，以及用户自动登录的时间

```
<%--
login.jsp
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
                <input type="text" name="username"/>${errorMsg}</td>
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
```

```
<%--
index.jsp
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
        欢迎你，${sessionScope.user}!
        <a href="${pageContext.request.contextPath}/LogoutServlet">注销</a>
    </c:otherwise>
</c:choose>
<hr/>
</body>
</html>
```

### 创建Servlet

#### 编写LoginServlet类

用于处理用户的登录请求，如果输入的用户名和密码正确，则发送一个用户自动登录的Cookie，并跳转到首页；否则提示输入的用户名或密码错误，并跳转到登录页面，让用户重新登录。

```
package cn.itcast.chapter09.filter;

import cn.itcast.chapter09.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 检查用户名和密码
        if ("itcast".equals(username) && "123456".equals(password)) {
            // 登录成功
            // 将用户状态 User 对象存入 Session 域
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            request.getSession().setAttribute("user", user);
            // 发送自动登录的Cookie
            String autoLogin = request.getParameter("autologin");
            if (autoLogin != null) {
                // 注意Cookie 中的密码要加密
                Cookie cookie = new Cookie("autoLogin", username + "-" + password);
                cookie.setMaxAge(Integer.parseInt(autoLogin));
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }
            //跳转至首页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            // 请求转发至登录页面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```

#### 编写LogoutServlet类

用于注销用户登录的信息，首先将Session会话中保存的User对象删除，然后将自动登录的Cookie删除，最后跳转到index.jsp

```
package cn.itcast.chapter09.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 用户注销
        request.getSession().removeAttribute("user");
        // 从客户端删除自动登录的Cookie
        Cookie cookie = new Cookie("autologin","msg");
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
```

### 创建过滤器

#### 编写AuroLoginFilter类

用于拦截用户登录的访问请求，判断请求汇总是否包含用户自动登录的Cooie，如果包含，则获取Cooie的用户名和密码，并验证用户名和密码是否正确，如果正确，则将用户的登录信息封装到User对象存入Session域中，完成用户自动登录

```
package cn.itcast.chapter09.filter;

import cn.itcast.chapter09.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter",urlPatterns="/*")
public class AutoLoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        // 获取一个名为 autologin 的Cookie
        Cookie[] cookies = request.getCookies();
        String autologin = null;
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if("autologin".equals(cookies[i].getName())){
                autologin = cookies[i].getValue();
                break;
            }
        }
        if(autologin != null){
            // 自动登录
            String[] parts = autologin.split("-");
            String username = parts[0];
            String password = parts[1];
            // 检查用户名和密码
            if("itcast".equals(username) && "123456".equals(password)){
                //登录成功，将用户状态 user 对象存入Session域
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                request.getSession().setAttribute("user",user);
            }
        }
        // 放行
        chain.doFilter(request, response);
    }
}
```





