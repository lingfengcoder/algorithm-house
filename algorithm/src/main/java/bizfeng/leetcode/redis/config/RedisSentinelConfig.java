package bizfeng.leetcode.redis.config;


import bizfeng.leetcode.redis.RedisTypeEnum;

/**
 * @author: wz
 * @date: 2021/2/3 17:36
 * @description:
 */
public class RedisSentinelConfig extends RedisConfig {
    public RedisSentinelConfig() {
        super();
        setType(RedisTypeEnum.SENTINEL);
    }

    private String master;
    //
    private String hostLink;

    public String getHostLink() {
        return hostLink;
    }

    public void setHostLink(String hostLink) {
        this.hostLink = hostLink;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
