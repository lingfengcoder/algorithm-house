package bizfeng.leetcode.redis;



import bizfeng.leetcode.redis.config.RedisClusterConfig;
import bizfeng.leetcode.redis.config.RedisConfig;
import bizfeng.leetcode.redis.config.RedisSentinelConfig;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;
import redis.clients.jedis.util.Pool;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @Author: wz
 * @Date: 2020/11/9 16:00
 * @Description: redis连接工具
 */

public class RedisStoreConnection {

    private static final Log log = LogFactory.getLog(RedisStoreConnection.class);

    private static Pool<?> pool;

    private static JedisPool jedisPool = null;

    private static JedisSentinelPool sentinelPool = null;

    private static JedisCluster jedisCluster = null;

    private static RedisConfig redisConfig = null;

    public static void connect(RedisConfig config) {
        redisConfig = config;
        JedisPoolConfig poolConfig = initPoolConfig(config);
        switch (config.getType()) {
            case SIMPLE:
                jedisPool = initJedisPool(config, poolConfig);
                break;
            case SENTINEL:
                RedisSentinelConfig sentinelConfig = (RedisSentinelConfig) config;
                sentinelPool = initJedisSentinelPool(sentinelConfig, poolConfig);
                break;
            case CLUSTER:
                RedisClusterConfig clusterConfig = (RedisClusterConfig) config;
                jedisCluster = initJedisCluster(clusterConfig, poolConfig);
                break;
            default:
                break;
        }
    }

    private static JedisPoolConfig initPoolConfig(RedisConfig config) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        poolConfig.setTestOnBorrow(defaultVal(config.isTestOnBorrow(), true));
        // 在空闲时检查有效性, 默认false
        poolConfig.setTestWhileIdle(defaultVal(config.isTestWhileIdle(), true));
        //在将连接放回池中前，自动检验连接是否有效
        poolConfig.setTestOnReturn(defaultVal(config.isTestOnReturn(), true));
        // 最大空闲数
        poolConfig.setMaxIdle(defaultVal(config.getMaxActive(), 8));
        // 连接池的最大数据库连接数
        poolConfig.setMaxTotal(defaultVal(config.getMaxIdle(), 16));
        poolConfig.setMinIdle(defaultVal(config.getMinIdle(), 8));
        // 最大建立连接等待时间
        poolConfig.setMaxWaitMillis(defaultVal(config.getTimeout(), 1800000));
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；
        // 这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        poolConfig.setMinEvictableIdleTimeMillis(defaultVal(config.getMinEvictableIdleTimeMillis(), 30000L));
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        //表示idle object evitor每次扫描的最多的对象数
        poolConfig.setNumTestsPerEvictionRun(defaultVal(config.getNumTestsPerEvictionRun(), 10));
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1,
        // 表示idle object evitor两次扫描之间要sleep的毫秒数
        poolConfig.setTimeBetweenEvictionRunsMillis(defaultVal(config.getTimeBetweenEvictionRunsMillis(), 30000L));
        return poolConfig;
    }

    private static JedisPool initJedisPool(RedisConfig config, JedisPoolConfig poolConfig) {
        return new JedisPool(poolConfig, config.getHost(), config.getPort(), config.getTimeout(), config.getAuth());
    }

    private static JedisSentinelPool initJedisSentinelPool(RedisSentinelConfig config, JedisPoolConfig poolConfig) {
        Set<String> sentinels = new HashSet<>();
        if (StringUtils.isNotBlank(config.getHostLink())) {
            String[] hosts = config.getHostLink().split(",");
            if (hosts.length > 0) {
                sentinels.addAll(Arrays.asList(hosts));
            }
            return new JedisSentinelPool(config.getMaster(), sentinels, poolConfig, config.getTimeout(), config.getAuth());
        }
        return null;
    }

    private static JedisCluster initJedisCluster(RedisClusterConfig config, JedisPoolConfig poolConfig) {
        Set<HostAndPort> hostAndPortsSet = new HashSet<>();
        if (StringUtils.isNotBlank(config.getHostLink())) {
            String[] hosts = config.getHostLink().split(",");
            if (hosts.length > 0) {
                for (String s : hosts) {
                    String[] host = s.split(":");
                    hostAndPortsSet.add(new HostAndPort(host[0], Integer.parseInt(host[1])));
                }
            }

            GenericObjectPoolConfig<Connection> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
            int maxIdle = poolConfig.getMaxIdle();
            genericObjectPoolConfig.setMaxIdle(maxIdle);
            //todo jedis 4.0代码需要更新
            return new JedisCluster(hostAndPortsSet, config.getTimeout(), config.getTimeout(), 6, config.getAuth(),genericObjectPoolConfig);
        }
        return null;
    }

    private static void log(Exception e) {
        log.error(e, e.fillInStackTrace());
    }

    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public static void setJedisPool(JedisPool jedisPool) {
        RedisStoreConnection.jedisPool = jedisPool;
    }

    public static JedisSentinelPool getSentinelPool() {
        return sentinelPool;
    }

    public static void setSentinelPool(JedisSentinelPool sentinelPool) {
        RedisStoreConnection.sentinelPool = sentinelPool;
    }

    public static JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public static void setJedisCluster(JedisCluster jedisCluster) {
        RedisStoreConnection.jedisCluster = jedisCluster;
    }

    public static RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public static void setRedisConfig(RedisConfig redisConfig) {
        RedisStoreConnection.redisConfig = redisConfig;
    }

    private static <T> T defaultVal(T t1, T t2) {
        if (t1 == null) {
            return t2;
        }
        if (t1 instanceof String) {
            if (t1.equals("")) {
                return t2;
            }
        }
        return t1;
    }
}
