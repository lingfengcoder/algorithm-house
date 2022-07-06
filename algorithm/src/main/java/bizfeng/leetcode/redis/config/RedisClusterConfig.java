package bizfeng.leetcode.redis.config;


import bizfeng.leetcode.redis.RedisTypeEnum;

/**
 * @author: wz
 * @date: 2021/2/3 17:44
 * @description:
 */
public class RedisClusterConfig extends RedisConfig {
    private String hostLink;

    public RedisClusterConfig() {
        super();
        setType(RedisTypeEnum.CLUSTER);
    }

    public String getHostLink() {
        return hostLink;
    }

    public void setHostLink(String hostLink) {
        this.hostLink = hostLink;
    }

}
