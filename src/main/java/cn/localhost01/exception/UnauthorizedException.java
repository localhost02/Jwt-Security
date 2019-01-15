package cn.localhost01.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:未认证异常
 * @Author Ran.chunlin
 * @Date: Created in 16:02 2019-01-13
 */
@Component public class UnauthorizedException implements AuthenticationEntryPoint {

    @Override public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        response.getWriter().write("<h1>401，您还未登录认证！</h1>");
        response.getWriter().flush();
    }

}
