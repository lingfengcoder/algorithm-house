package bizfeng.leetcode.redis.jedis.sentinel;

import redis.clients.jedis.*;

/**
 * @Auther: wz
 * @Date: 2022/7/6 15:01
 * @Description: 基于jedis的sentinel哨兵实践
 *
 */
public class SentinelInstance {

    private static String host="";
    private static int port=7369;

    static {
        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
       // HostAndPortMapper hostAndPortMapper = new HostAndPortMapper();
      //  HostAndPort hostAndPort = new HostAndPort();

       // String masterName, Set<String> sentinels,
       // final GenericObjectPoolConfig<Jedis> poolConfig, int timeout, final String user,
        //final String password, final int database
       // JedisSentinelPool sentinelPool=new JedisSentinelPool();
    }
}
