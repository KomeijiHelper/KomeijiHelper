package komeiji.back.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.PrintWriter;


public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登陆成功之后应该有用户的session
        Object session = request.getSession().getAttribute("LoginUser");

        if (session == null) {  //没有登陆
            //重置response
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            //设置编码格式
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            response.addHeader("Access-Control-Max-Age", "3600");
            String origin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            //要返回前端的数据
            PrintWriter printWriter = response.getWriter();
            printWriter.write("{\"status\":403}");
            printWriter.flush();
            printWriter.close();
//            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        } else {
            return true;
        }
    }
}
