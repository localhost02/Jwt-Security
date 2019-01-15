package cn.localhost01.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: jwt token存储引擎工厂，根据配置文件，提供不同的存储引擎实例
 * @Author Ran.chunlin
 * @Date: Created in 1:10 2019-01-14
 */
@Component
public class JwtTokenDbFactory {

    @Value("${jwt.security.db}")
    private String jwtTokenDbChooser;

    @Autowired
    private MysqlDo mysqlDo;

    @Autowired
    private RedisDo redisDo;

    /**
     * 获取存储引擎实例
     */
    public JwtTokenDbProvider getInstance() {
        if ("redis".equals(jwtTokenDbChooser)) {
            return redisDo;
        } else if ("mysql".equals(jwtTokenDbChooser)) {
            return mysqlDo;
        } else {
            new Exception("Storage Engine Settings Not Supported!").printStackTrace();
        }
        return null;
    }


}
