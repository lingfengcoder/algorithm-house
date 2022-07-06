package bizfeng.leetcode.redis;


import bizfeng.leetcode.redis.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.Pipeline;

import java.io.IOException;


/**
 * @author: wz
 * @date: 2021/2/3 20:47
 * @description:
 */
@Slf4j
public class RedisStoreCmd {

    protected static JedisCommands getCmd() throws Exception {

        RedisConfig redisConfig = RedisStoreConnection.getRedisConfig();
        switch (redisConfig.getType()) {
            case SIMPLE:
                if (RedisStoreConnection.getJedisPool() != null) {
                    return RedisStoreConnection.getJedisPool().getResource();
                }
                break;
            case SENTINEL:
                if (RedisStoreConnection.getSentinelPool() != null) {
                    return RedisStoreConnection.getSentinelPool().getResource();
                }
                break;
            case CLUSTER:
                if (RedisStoreConnection.getJedisCluster() != null) {
                    return RedisStoreConnection.getJedisCluster();
                }
                break;
            default:
                break;
        }
        throw new Exception("没有可用连接");
    }

    protected static JedisCluster getJedisCluster() {
        return RedisStoreConnection.getJedisCluster();
    }

    protected static Pipeline getPipeLine() throws Exception {
        RedisConfig redisConfig = RedisStoreConnection.getRedisConfig();
        switch (redisConfig.getType()) {
            case SIMPLE:
                if (RedisStoreConnection.getJedisPool() != null) {
                    return RedisStoreConnection.getJedisPool().getResource().pipelined();
                }
                break;
            case SENTINEL:
                if (RedisStoreConnection.getSentinelPool() != null) {
                    return RedisStoreConnection.getSentinelPool().getResource().pipelined();
                }
                break;
            case CLUSTER:
                if (RedisStoreConnection.getJedisCluster() != null) {
                    throw new Exception("暂不支持");
//                    JedisCluster jedisCluster = RedisStoreConnection.getJedisCluster();
//                    JedisClusterPipeline pipeline = new JedisClusterPipeline();
//                    pipeline.setJedisCluster(jedisCluster);
//                    return pipeline.getJedis(key);
                }
                break;
            default:
                break;
        }
        return null;
    }


    protected static void close(JedisCommands cmd) {
        if (cmd != null) {
            if (cmd instanceof Jedis) {
                Jedis jedis = (Jedis) cmd;
                jedis.close();
            }
        }
    }

    protected static void close(Pipeline pipeline) {
        if (pipeline != null) {
            try {
                pipeline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
