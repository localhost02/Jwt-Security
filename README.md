# 一、基本介绍
基于Spring Security实现的JWT认证的SpringBoot Starter包

# 二、基本功能
该项目实现一个简单的**权限认证Demo**，功能包含：
* 注解来是否开启认证`@EnableJwtSecurity`
* 注解来开启某个方法是否需要验证指定权限`@RequireAuthority("account_users_create")`
* jwt token存储引擎可配置（mysq、redis）
* 登录认证
* 注销
* ……

# 三、重要说明
* 程序内使用了`带过期功能的map`和`list`分别模拟的 redis 和 mysql。实际上，可使用spring data、jpa等模块，编码对接 redis 和 mysql！

* token存储方面，提供了抽象接口和简单工厂，便于拓展其他存储引擎

* 注意程序中TODO标记的地方，表示由于是demo级别的原因，故意做的**适配**！

# 四、食用指南
### 打包
* 安装在本地仓库：拉下项目，执行`mvn install`
* 部署到私服：修改pom.xml的`distributionManagement`节点，另外需配置maven的setting.xml，以便连接到私服，执行`mvn deploy`

### 引用
```xml
<dependency>
	<groupId>cn.localhost01</groupId>
	<artifactId>jwt-security-spring-boot-starter</artifactId>
	<version>${jwt.security.version}</version>
</dependency>
```

### 使用示例
1、配置文件
```
jwt.security.permit-urls=/,/auth/login,/home,/**.ico
# 目前支持的存储有：redis、mysql
jwt.security.db=redis
# 单位毫秒，当jwt.security.db=redis才有效
jwt.security.redis.keepAlive=60000
spring.thymeleaf.cache=false
```
* 配置了路由白名单，以上白名单访问不需要认证
* 配置了token存储引擎为 redis
* 配置了token失效时间

2、开启认证、授权示例
```java
@EnableJwtSecurity @SpringBootApplication public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
```java
/**
 * 创建用户
 * @return
 */
@RequireAuthority("account_users_create")
@PostMapping("/create") public String postCreate() {
    return "admin/create_user_success";
}
```

### 附录
nexus私服安装教程（**本人亲自搭建的实战教程，巨详细！**）
* https://localhost01.cn/2018/11/22/Nexus搭建Maven私服全攻略一：认识Nexus与索引/

* https://localhost01.cn/2018/11/22/Nexus搭建Maven私服全攻略二：安装和配置Nexus3/