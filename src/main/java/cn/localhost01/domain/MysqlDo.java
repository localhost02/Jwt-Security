package cn.localhost01.domain;

import cn.localhost01.db.MySql;
import cn.localhost01.model.User;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @Description:mysql的领域模型
 * @Author Ran.chunlin
 * @Date: Created in 1:10 2019-01-14
 */
@Component public class MysqlDo implements JwtTokenDbProvider {

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     *
     * @return 实体
     */
    public User getUserByName(String username) {
        //1.模拟全表扫描 ๑乛◡乛๑
        Iterator<User> iterator = MySql.getUserTable().iterator();
        User user;
        while (iterator.hasNext()) {
            user = iterator.next();
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    @Override public void put(String key, Object value, long keepAlive) {
        //TODO mysql来存储jwt token的核心也就是，设置一个过期时间，每次程序检测是否过期，过期则删除（相比redis的过期主动删除，mysql可直接借由程序来实现被动删除）
        MySql.getJwtTable().add(new MySql.Jwt(key, System.currentTimeMillis() + keepAlive));
    }

    @Override public boolean containsKey(Object key) {
        //1.模拟全表扫描 ๑乛◡乛๑
        Iterator<MySql.Jwt> iterator = MySql.getJwtTable().iterator();
        MySql.Jwt jwt;
        while (iterator.hasNext()) {
            jwt = iterator.next();
            if (key.equals(jwt.getToken())) {
                boolean isExpired = System.currentTimeMillis() > jwt.getExpiryTime();
                if (isExpired) {
                    iterator.remove();
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override public void del(Object key) {
        //1.模拟全表扫描 ๑乛◡乛๑
        Iterator<MySql.Jwt> iterator = MySql.getJwtTable().iterator();
        MySql.Jwt jwt;
        while (iterator.hasNext()) {
            jwt = iterator.next();
            if (key.equals(jwt.getToken())) {
                iterator.remove();
                break;
            }
        }
    }
}
