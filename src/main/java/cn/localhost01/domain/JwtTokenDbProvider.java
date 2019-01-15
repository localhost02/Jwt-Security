package cn.localhost01.domain;

/**
 * @Description: jwt token存储引擎提供器接口，用于屏蔽具体的存储引擎实现，对以后新增存储引擎提供扩展性
 * @Author Ran.chunlin
 * @Date: Created in 1:10 2019-01-14
 */
public interface JwtTokenDbProvider {

    /**
     * 设值
     *
     * @param key        键
     * @param value      值
     * @param keepAlive 存活时间
     */
    void put(String key, Object value, long keepAlive);

    /**
     * 是否存在key
     *
     * @param key 键
     * @return
     */
    boolean containsKey(Object key);

    /**
     * 删除key
     *
     * @param key 键
     * @return
     */
    void del(Object key);
}
