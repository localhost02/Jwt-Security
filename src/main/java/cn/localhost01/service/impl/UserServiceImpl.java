package cn.localhost01.service.impl;

import cn.localhost01.domain.JwtTokenDbFactory;
import cn.localhost01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description: 服务层接口
 * @Author Ran.chunlin
 * @Date: Created in 15:13 2019-01-13
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Value("${jwt.security.redis.keepAlive}")
    private long redisKeepAlive;

    @Autowired
    private JwtTokenDbFactory jwtTokenDbFactory;

    @Override
    public void logout4JwtDb(String token) {
        jwtTokenDbFactory.getInstance().del(token);
    }

    @Override
    public void login4JwtDb(String token, Object value) {
        jwtTokenDbFactory.getInstance().put(token, value, redisKeepAlive);
    }

    @Override
    public boolean ifContainsAndReset4JwtDb(String token, Object value) {
        boolean isContains = jwtTokenDbFactory.getInstance().containsKey(token);
        if (isContains) {
            jwtTokenDbFactory.getInstance().put(token, value, redisKeepAlive);
        }
        return isContains;
    }

}
