package bizfeng.leetcode.redis.config;


import bizfeng.leetcode.redis.RedisTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz
 * @date: 2021/2/3 16:53
 * @description:
 */
@Setter
@Getter
public class RedisSimpleConfig extends RedisConfig {

    public RedisSimpleConfig() {
        super();
        this.setType(RedisTypeEnum.SIMPLE);
    }
}
