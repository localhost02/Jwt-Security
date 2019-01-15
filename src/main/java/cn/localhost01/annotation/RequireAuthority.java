package cn.localhost01.annotation;

import java.lang.annotation.*;

/**
 * @Description: 开启权限校验注解
 * @Author Ran.chunlin
 * @Date: Created in 15:42 2019-01-13
 */
@Target({ ElementType.METHOD }) @Retention(RetentionPolicy.RUNTIME) @Documented public @interface RequireAuthority {
    String value();
}
