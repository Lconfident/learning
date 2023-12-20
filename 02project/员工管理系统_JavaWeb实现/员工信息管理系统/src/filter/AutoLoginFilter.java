package filter;

import entity.User;

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
            int employee_id = Integer.parseInt(parts[0]);
            String password = parts[1];
            // 检查用户名和密码
            if(001 == employee_id && "root".equals(password)){
                //登录成功，将用户状态 user 对象存入Session域
                User user = new User();
                user.setEmployee_id(employee_id);
                user.setPassword(password);
                request.getSession().setAttribute("user",user);
            }
        }
        // 放行
        chain.doFilter(request, response);
    }
}
