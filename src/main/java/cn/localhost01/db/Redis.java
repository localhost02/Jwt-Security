package cn.localhost01.db;

import lombok.Getter;

import java.util.HashMap;

/**
 * @Description:模拟redis //真实环境这里应是通过配置文件连接到redis，获取jedis实例
 * @Author Ran.chunlin
 * @Date: Created in 1:10 2019-01-14
 */
public class Redis {
    /**
     * 模拟redis
     */
    @Getter private static HashMap<String, Long> redis = new HashMap<>();

    /**
     * 是否包含key
     *
     * @param key
     *
     * @return
     */
    public static boolean containsKey(Object key) {
        return !isExpired(key) && redis.containsKey(key);
    }

    /**
     * 写入key value keepAlive
     *
     * @param key 键
     * @param value 值
     * @param keepAlive 存活时间 毫秒
     *
     * @return
     */
    public static Long put(String key, Object value, Long keepAlive) {
        return redis.put(key, System.currentTimeMillis() + keepAlive);
    }

    /**
     * 获取值
     *
     * @param key 键
     *
     * @return
     */
    public static Long get(Object key) {
        if (key == null) {
            return null;
        }
        if (isExpired(key)) {
            return null;
        }
        return redis.get(key);
    }

    /**
     * 删除
     *
     * @param key 键
     *
     * @return
     */
    public static void del(Object key) {
        if (key == null) {
            return;
        }
        redis.remove(key);
    }

    /*
     * 检查是否过期
     * @param key   键
     * @return
     */
    private static boolean isExpired(Object key) {

        if (!redis.containsKey(key)) {
            return Boolean.FALSE;
        }
        long expiryTime = redis.get(key);

        boolean isExpired = System.currentTimeMillis() > expiryTime;
        if (isExpired) {
            redis.remove(key);
        }
        return isExpired;
    }
}
