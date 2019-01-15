package cn.localhost01.db;

import cn.localhost01.annotation.RoleAuthority;
import cn.localhost01.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

/**
 * @Description:模拟数据库 //真实环境这里应是通过配置文件连接到mysql，获取mysql连接
 * @Author Ran.chunlin
 * @Date: Created in 14:28 2019-01-13
 */
public class MySql {
    /**
     * 模拟用户表
     */
    @Getter private static List<User> userTable;

    /**
     * 模拟角色-权限表
     */
    @Getter private static Map<String, String> ruleAuthorityTable;

    static {
        ruleAuthorityTable = new HashMap<>();
        ruleAuthorityTable.put("USER", "account_users_info");
        ruleAuthorityTable.put("ADMIN", "account_users_info,account_users_create");
    }

    /**
     * 模拟jwt token表
     */
    @Getter private static HashSet<Jwt> jwtTable;

    static {
        userTable = new ArrayList<>(4);
        User user = new User();
        user.setUsername("user");
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        List<RoleAuthority> authorities=new ArrayList();
        authorities.add(new RoleAuthority("USER",ruleAuthorityTable.get("USER")));
        user.setAuthorities(authorities);
        userTable.add(user);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(new BCryptPasswordEncoder().encode("123456"));
        authorities=new ArrayList();
        authorities.add(new RoleAuthority("USER",ruleAuthorityTable.get("USER")));
        authorities.add(new RoleAuthority("ADMIN",ruleAuthorityTable.get("ADMIN")));
        admin.setAuthorities(authorities);
        userTable.add(admin);
    }

    @Data public static class Jwt {
        private String token;
        private Long expiryTime;

        public Jwt(String token, Long expiryTime) {
            this.token = token;
            this.expiryTime = expiryTime;
        }
    }

}

