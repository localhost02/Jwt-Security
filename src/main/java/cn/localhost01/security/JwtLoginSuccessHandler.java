package cn.localhost01.security;

import cn.localhost01.annotation.RoleAuthority;
import cn.localhost01.service.UserService;
import cn.localhost01.util.CookiesUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Description:登录验证过滤器
 * @Author Ran.chunlin
 * @Date: Created in 15:30 2019-01-13
 */
@Component public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired private UserService userService;

    @Override public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        //TODO 注意：过期时间应由redis来保证，这里暂设为一周；正式情况下，该时间也应交由配置中心来统一配置
        int expire = 7 * 60 * 60 * 24;

        //1.生成token，TODO 正式情况下，加密的密钥也应妥善保管，而不是写死在这儿
        String token = "demo-jwt-prefix:" + Jwts.builder().setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + expire * 1000))
                .signWith(SignatureAlgorithm.HS512, "i am key").compact();

        //2.转发
        CookiesUtil.setCookie(response, "Authorization", token, expire);

        //记录是否登录，TODO  正常情况下，个人中心和登录应由前端获取Authorization来判定！
        if (authentication.getAuthorities().contains(new RoleAuthority("ADMIN","account_user_info,account_user_create"))) {
            request.getSession().setAttribute("isAdmin", 1);
        } else {
            request.getSession().setAttribute("isAdmin", -1);
        }

        //3.保存jwttoken到JwtDb
        userService.login4JwtDb(token, null);

        request.getRequestDispatcher("/").forward(request, response);
    }
}
