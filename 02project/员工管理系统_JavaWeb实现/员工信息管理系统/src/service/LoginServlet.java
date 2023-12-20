package service;

import entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获得用户名和密码
        int employee_id =Integer.parseInt(request.getParameter("employee_id"));
        String password = request.getParameter("password");
        // 检查用户名和密码
        if (001 == employee_id && "root".equals(password)) {
            // 管理员登录成功
            // 将用户状态 User 对象存入 Session 域
            User user = new User();
            user.setEmployee_id(employee_id);
            user.setPassword(password);
            request.getSession().setAttribute("user",user);
            // 发送自动登录的Cookie
            String autoLogin = request.getParameter("autologin");
            if (autoLogin != null) {
                // 注意Cookie 中的密码要加密
                Cookie cookie = new Cookie("autoLogin", employee_id + "-" + password);
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
