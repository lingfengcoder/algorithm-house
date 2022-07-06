package bizfeng.leetcode.redis.config;


import bizfeng.leetcode.redis.RedisTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author: wz
 * @date: 2021/2/3 16:53
 * @description:
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class RedisConfig {

    private RedisTypeEnum type;

    private String host;

    //Redis的端口号
    private int port;

    //访问密码
    private String auth;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private int maxActive = 300;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8。
    private int maxIdle = 8;
    //控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值是0。
    private int minIdle = 0;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private int maxWait = 10000;

    private int timeout = 10000;

    private boolean testOnCreate;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean testWhileIdle;

    private long minEvictableIdleTimeMillis;

    private int numTestsPerEvictionRun;

    private long timeBetweenEvictionRunsMillis;


}
