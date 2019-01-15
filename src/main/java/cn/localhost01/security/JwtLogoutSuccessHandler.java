package cn.localhost01.security;

import cn.localhost01.service.UserService;
import cn.localhost01.util.CookiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:登录验证过滤器
 * @Author Ran.chunlin
 * @Date: Created in 15:30 2019-01-13
 */
@Component public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired private UserService userService;

    @Override public void onLogoutSuccess(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, Authentication authentication)
            throws IOException, ServletException {
        String token = CookiesUtil.getCookie(httpServletRequest, "Authorization");

        //1.验证token
        if (token != null && JwtAuthenticationFilter.getAuthentication(token) != null) {

            //2.注销JwtDb中的token
            userService.logout4JwtDb(token);
        }

        httpServletRequest.getRequestDispatcher("/auth/login").forward(httpServletRequest, httpServletResponse);
    }
}
