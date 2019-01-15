package cn.localhost01.security;

import cn.localhost01.exception.ForbiddenException;
import cn.localhost01.exception.UnauthorizedException;
import cn.localhost01.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description: security适配器
 * @Author Ran.chunlin
 * @Date: Created in 16:18 2019-01-13
 */
@EnableWebSecurity public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("#{'${jwt.security.permit-urls}'.split(',')}") private String[] permitUrls;

    @Autowired private UserDetailsService userDetailsService;

    @Autowired private UnauthorizedException unauthorizedException;

    @Autowired private ForbiddenException forbiddenException;

    @Autowired private JwtLoginSuccessHandler jwtLoginSuccessHandler;

    @Autowired private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    /**
     * 配置验证的规则
     *
     * @param http
     *
     * @throws Exception
     */
    @Override protected void configure(HttpSecurity http) throws Exception {
        if (!SpringUtil.isEnableJwtSecurity()){
            http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
        } else {
            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers(permitUrls).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    //注意：自定义formLogin后，继承自UsernamePasswordAuthenticationFilter的过滤器将无法使用，说白了只有默认formLogin时（/login），
                    //UsernamePasswordAuthenticationFilter的实现类才会被调用，否则会直接调用UsernamePasswordAuthenticationFilter，而跳过其实现类
                    .formLogin().loginPage("/auth/login").loginProcessingUrl("/auth/login").successHandler(jwtLoginSuccessHandler)
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedException)
                    .accessDeniedHandler(forbiddenException)
                    .and()
                    .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                    //logoutSuccessUrl在声明了logoutSuccessHandler后会失效，因为logoutSuccessUrl其实底层也是在默认的ForwardLogoutSuccessHandler执行了url跳转，这里我们声明了自己的LogoutSuccessHandler，则在自己的Handler中来实现跳转逻辑
                    .logout().deleteCookies("Authorization").logoutSuccessHandler(jwtLogoutSuccessHandler);
            }
    }

    /**
     * 登录时调用
     *
     * @param auth
     *
     * @throws Exception
     */
    @Override public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义验证
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
