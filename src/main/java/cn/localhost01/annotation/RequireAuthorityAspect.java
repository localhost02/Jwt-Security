package cn.localhost01.annotation;

import cn.localhost01.util.SpringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect @Component public class RequireAuthorityAspect {

    @Pointcut("@annotation(cn.localhost01.annotation.RequireAuthority)") public void annotationPointCut() {
    }

    @Before("annotationPointCut()") public void before(JoinPoint joinPoint) throws ClassNotFoundException {
        if (!SpringUtil.isEnableJwtSecurity()) {
            return;
        }

        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();

        RequireAuthority annotation = method.getAnnotation(RequireAuthority.class);
        if (annotation == null) {
            return;
        }

        List<RoleAuthority> authorities = (List<RoleAuthority>) SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities();
        List list;

        //1.验证是否具有权限
        for (RoleAuthority authority : authorities) {
            list = Arrays.asList(authority.getAuthorities().split(","));
            if (list.contains(annotation.value())) {
                return;
            }
        }

        //2.没有权限，跳出
        throw new AccessDeniedException("");
    }
}
