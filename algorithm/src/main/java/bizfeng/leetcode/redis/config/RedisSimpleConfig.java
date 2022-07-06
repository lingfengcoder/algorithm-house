package bizfeng.leetcode.redis.config;


import bizfeng.leetcode.redis.RedisTypeEnum;

/**
 * @author: wz
 * @date: 2021/2/3 16:53
 * @description:
 */
public class RedisSimpleConfig extends RedisConfig {

    public RedisSimpleConfig() {
        super();
        setType(RedisTypeEnum.SIMPLE);
    }
}
