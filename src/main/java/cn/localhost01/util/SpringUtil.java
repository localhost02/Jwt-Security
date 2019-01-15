package cn.localhost01.util;

import cn.localhost01.annotation.EnableJwtSecurity;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: 普通类获取bean工具
 * @Author Ran.chunlin
 * @Date: Created in 15:13 2019-01-13
 */
@Component public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public SpringUtil() {
    }

    /**
     * 启动类全限定名
     */
    private static String mainClass;

    /**
     * 是否开启了@EnableJwtSecurity注解
     *
     * @return
     *
     * @throws ClassNotFoundException
     */
    public static boolean isEnableJwtSecurity() throws ClassNotFoundException {
        mainClass = applicationContext.getEnvironment().getProperty("sun.java.command");
        return Class.forName(mainClass).isAnnotationPresent(EnableJwtSecurity.class);
    }

    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        if (applicationContext == null) {
            applicationContext = arg0;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
