package cn.localhost01.domain;

import cn.localhost01.db.Redis;
import org.springframework.stereotype.Component;

/**
 * @Description:redis的领域模型
 * @Author Ran.chunlin
 * @Date: Created in 1:10 2019-01-14
 */
@Component
public class RedisDo implements JwtTokenDbProvider {

    @Override
    public void put(String key, Object value, long keepAlive) {
        Redis.put(key, value, keepAlive);
    }

    @Override
    public boolean containsKey(Object key) {
        return Redis.get(key) != null;
    }

    @Override
    public void del(Object key) {
        Redis.del(key);
    }
}
