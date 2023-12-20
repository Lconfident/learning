### 目标：模拟用户登录

当用户访问某个网站时，首先判断用户是否登录，若已登录，则显示登录信息；否则进入用户登录页面，输入登录信息，接着判断用户名或密码是否正确，若错误，则提示用户名或密码错误；否则显示登录用户的信息，最后单击退出，退出到登录界面。

### 创建User类

包含`username`和`password`属性，分别代表用户名和密码

`Getter,Setter`方法

```java
public class User {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

### 创建Servlet

#### 显示网站的首页

`IndexServlet`类

创建或获取用户信息的`session`对象

`User user = (User) session.getAttribute("user");`在`session`对象中获取`user`属性值

判断`user`是否存在（即用户是否登录）

```java
@WebServlet(name = "IndexServlet", urlPatterns = "/IndexServlet")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //创建或获取用户信息的Session对象
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.getWriter().print("您还没有登录，请<a href='/chapter05/login.html'>登录</a>");
        } else {
            response.getWriter().print("您已登录，欢迎你，" + user.getName() + "！");
            response.getWriter().println("<a href='/chapter05/LogoutServlet'>退出</a>");
            Cookie cookie = new Cookie("JESSIONID", session.getId());
            cookie.setMaxAge(30 * 60);
            // 对chapter05目录及其子目录有效
            cookie.setPath("/chapter05");
            // 服务器发送Cookie，所以是响应response添加Cookie
            response.addCookie(cookie);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```

#### 用户登录页面

`LoginServlet`类

获取表单元素`username`和`password`

验证用户名和密码是否正确

正确 -> 登录成功，重定向至`IndexServlet`

错误 -> 提示登录失败

```java
@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();
        // 假设用户名是itcast，密码是123
        if (username.equals("itcast") && password.equals("123")) {
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/chapter05/IndexServlet");
        } else {
            out.write("用户名或密码错误，登录失败！");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```

#### 实现注销

`LogoutServlett`类

将`session`对象中的`User`对象删除

重定向至`IndexServlet`

```java
@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 将session对象中的User对象删除
        request.getSession().removeAttribute("user");
        response.sendRedirect("/chapter05/IndexServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```

### 创建登录页面

```html
<body>
<form action="http://localhost:8080/chapter05/LogoutServlet" method="post">
    用户名：<input name="username" type="text"><br/>
    密码：<input name="password" type="password"><br/>
    <input type="submit" value="提交" id="bt">
</form>
</body>
```

