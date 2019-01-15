package cn.localhost01.security;

import cn.localhost01.service.UserService;
import cn.localhost01.util.CookiesUtil;
import cn.localhost01.util.SpringUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:jwt认证过滤器
 * @Author Ran.chunlin
 * @Date: Created in 16:02 2019-01-13
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        //1.获取传入的jwt
        String token = CookiesUtil.getCookie(request, "Authorization");
        if (token == null || !token.startsWith("demo-jwt-prefix:")) {
            chain.doFilter(request, response);
            return;
        }

        //2.进行jwt验证，得到一个security token
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        //TODO 正式环境不需要这个东西，而是前端获取Authorization来判定是否登录
        if (authentication == null) {
            request.getSession().removeAttribute("isAdmin");
        }

        //3.通过，则保存授权
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /**
     * Jwt+时效性验证
     *
     * @param token 待验证token
     *
     * @return
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        UserService userService = SpringUtil.getBean("userService", UserService.class);
        UserDetailsService userDetailsService = SpringUtil.getBean("userDetailsService", UserDetailsService.class);

        //1.解析验证
        String user = Jwts.parser().setSigningKey("i am key").parseClaimsJws(token.replace("demo-jwt-prefix:", ""))
                .getBody().getSubject();

        //2.jwt验证成功
        if (user == null) {
            return null;
        }

        //3.JwtDb时效性验证（验证是否过期）
        if (userService.ifContainsAndReset4JwtDb(token, null)) {
            return new UsernamePasswordAuthenticationToken(user, null,
                    userDetailsService.loadUserByUsername(user).getAuthorities());
        }

        return null;
    }

}
