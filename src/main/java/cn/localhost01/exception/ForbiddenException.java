package cn.localhost01.exception;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:权限不足异常
 * @Author Ran.chunlin
 * @Date: Created in 16:02 2019-01-13
 */
@Component public class ForbiddenException implements AccessDeniedHandler {

    @Override public void handle(HttpServletRequest request, HttpServletResponse response,
            org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        response.setHeader("Content-Type", "text/html;charset=utf-8");
        response.getWriter().write("<h1>403，您的权限不足，无法访问该资源！</h1>");
        response.getWriter().flush();
    }
}
