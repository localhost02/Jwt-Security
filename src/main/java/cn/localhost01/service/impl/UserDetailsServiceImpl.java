package cn.localhost01.service.impl;

import cn.localhost01.domain.MysqlDo;
import cn.localhost01.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description: 服务层实现
 * @Author Ran.chunlin
 * @Date: Created in 15:13 2019-01-13
 */
@Service("userDetailsService") public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private MysqlDo mysqlDo;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = mysqlDo.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
