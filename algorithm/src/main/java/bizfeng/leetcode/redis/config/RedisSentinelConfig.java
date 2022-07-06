package bizfeng.leetcode.redis.config;


import bizfeng.leetcode.redis.RedisTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: wz
 * @date: 2021/2/3 17:36
 * @description:
 */
@Setter
@Getter
public class RedisSentinelConfig extends RedisConfig {
    public RedisSentinelConfig() {
        super();
         this.setType(RedisTypeEnum.SENTINEL);
    }

    private String master;
    //
    private String hostLink;


}
