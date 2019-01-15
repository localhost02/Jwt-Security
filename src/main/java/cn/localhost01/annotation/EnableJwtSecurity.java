package cn.localhost01.annotation;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.annotation.*;

/**
 * @Description: 开启jwt安全认证注解
 * @Author Ran.chunlin
 * @Date: Created in 15:42 2019-01-13
 */
@EnableAspectJAutoProxy @Target({
        ElementType.TYPE }) @Retention(RetentionPolicy.RUNTIME) @Documented public @interface EnableJwtSecurity {
}
