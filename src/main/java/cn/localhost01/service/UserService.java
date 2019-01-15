package cn.localhost01.service;

/**
 * @Description: 服务层接口
 * @Author Ran.chunlin
 * @Date: Created in 15:13 2019-01-13
 */
public interface UserService {

    /**
     * 注销JwtDb中的jwttoken
     *
     * @param token jwt token
     * @return
     */
    void logout4JwtDb(String token);

    /**
     * jwttoken记录到JwtDb
     *
     * @param token jwt token
     * @param value 附带存储的值
     * @return
     */
    void login4JwtDb(String token, Object value);

    /**
     * JwtDb是否存在jwttoken
     * <p>
     * eq：是则会重置JwtDb中jwttoken的存活时间
     *
     * @param token jwt token
     * @param value 附带存储的值
     * @return
     */
    boolean ifContainsAndReset4JwtDb(String token, Object value);

}
