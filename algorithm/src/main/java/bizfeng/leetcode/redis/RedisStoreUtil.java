package bizfeng.leetcode.redis;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.commands.JedisCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Author: wz
 * @Date: 2020/11/9 16:00
 * @Description: redis持久化存储工具
 */

public class RedisStoreUtil {

    private static final Log log = LogFactory.getLog(RedisStoreUtil.class);


    private static void log(Exception e) {
        log.error(e, e.fillInStackTrace());
    }

    private static void info(String s) {
        log.info("【REDIS STORE】 " + s);
    }

    /**
     * redis 持久化 分页
     */
    private static class Page {
        int start = 0;
        int end = 0;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }
    }

    private static Page transPage(Integer pageIndex, Integer pageSize) {
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;
        if (pageSize <= 0) {
            pageSize = 10;
        }
        Page p = new Page();
        p.setStart((pageIndex - 1) * pageSize);
        p.setEnd(pageIndex * pageSize);
        return p;
    }

    public static boolean checkEnable() {
        try {
            JedisCommands cmd = RedisStoreCmd.getCmd();
            return cmd != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Long ttl(String s) {
        try {
            JedisCommands cmd = RedisStoreCmd.getCmd();
            return cmd.ttl(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static void expire(String key, Integer time) {
        try {
            JedisCommands cmd = RedisStoreCmd.getCmd();
            cmd.expire(key, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Long incr(String key) {
        try {
            JedisCommands cmd = RedisStoreCmd.getCmd();
            return cmd.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static <T> void set(String key, T t) {
         //todo jedis 4.0 需要更新代码
        throw new RuntimeException("//todo jedis 4.0 需要更新代码");
       // put(key, JSONObject.toJSONString(t));
    }

    public static void set(String key, String data) {
        put(key, data);
    }

    public static Long del(String key) {
        try {
            JedisCommands cmd = RedisStoreCmd.getCmd();
            return cmd.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private static void put(String key, String data) {
        JedisCommands cmd = null;
        try {
            info("put " + key + data);
            cmd = RedisStoreCmd.getCmd();
            cmd.set(key, data);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
    }

    public static String get(String key) {
        String result = null;
        JedisCommands cmd = null;
        try {
            info("get " + key);
            cmd = RedisStoreCmd.getCmd();
            result = cmd.get(key);
            log.info("get Redis key " + key + "," + result);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return result;
    }

    public static void sadd(String key, String data) {
        JedisCommands cmd = null;
        try {
            info("sadd " + key + " " + data);
            cmd = RedisStoreCmd.getCmd();
            cmd.sadd(key, data);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
    }

    public static void hset(String key, String hkey, String hvalue) {
        JedisCommands cmd = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            cmd.hset(key, hkey, hvalue);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
    }

    public static Long hdel(String key, String hkey) {
        try {
            JedisCommands cmd = RedisStoreCmd.getCmd();
            return cmd.hdel(key, hkey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static Map<String, String> hgetAll(String key) {
        Map<String, String> map = null;
        JedisCommands cmd = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            map = cmd.hgetAll(key);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return map;
    }

    public static List<String> hmget(String key, String... fields) {
        List<String> list = null;
        JedisCommands cmd = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            list = cmd.hmget(key, fields);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return list;
    }

    public static List<String> smembers(String key) {

        List<String> list = null;
        JedisCommands cmd = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            list = new ArrayList<>(cmd.smembers(key));
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return list;
    }


    public static void srem(String key, String data) {
        JedisCommands cmd = null;
        try {
            info("srem " + key + " " + data);

            cmd = RedisStoreCmd.getCmd();
            cmd.srem(key, data);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
    }

    /**
     * @param keys 指定的多个key
     * @return java.util.List<java.lang.String>
     * @description: 通过管道批量获取
     * @author:wz
     * @date:2021/2/4 11:14
     */
    public static List<String> mget(String... keys) {
        List<String> list = null;
        JedisCommands cmd = null;
        try {
            info("mget " + keys);
            cmd = RedisStoreCmd.getCmd();
            list = mget(cmd, keys);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return list;
    }

    /**
     * @description redis cluster集群模式下 通过管道批量删除
     * @author wz
     * @date 2021/2/22 13:58
     */
    //todo jedis 4.0 需要更新代码
//    public static <T> List<T> redisClusterPipelineZRem(Map<String, String> map) throws Exception {
//        JedisCluster jedisCluster = getJedisCluster();
//        if (jedisCluster == null) {
//            throw new Exception("pipelineGet 仅支持redis cluster模式下进行!");
//        }
//        JedisClusterPipeline pipeline = JedisClusterPipeline.pipelined(jedisCluster);
//        try {
//            pipeline.refreshCluster();
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                pipeline.zrem(entry.getKey(), entry.getValue());
//            }
//            return pipeline.syncAndReturnAll();
//        } catch (Exception e) {
//            log(e);
//        }
//        return null;
//    }

    /**
     * @description redis cluster集群模式下 通过管道批量删除
     * @author wz
     * @date 2021/2/22 13:58
     */
    //todo jedis 4.0 需要更新代码
//    public static <T> List<T> redisClusterPipelineZadd(String key, Map<String, Double> map) throws Exception {
//        JedisCluster jedisCluster = getJedisCluster();
//        if (jedisCluster == null) {
//            throw new Exception("pipelineGet 仅支持redis cluster模式下进行!");
//        }
//        JedisClusterPipeline pipeline = JedisClusterPipeline.pipelined(jedisCluster);
//        try {
//            pipeline.refreshCluster();
//            for (Map.Entry<String, Double> entry : map.entrySet()) {
//                pipeline.zadd(key, entry.getValue(), entry.getKey());
//            }
//            return pipeline.syncAndReturnAll();
//        } catch (Exception e) {
//            log(e);
//        }
//        return null;
//    }

    /**
     * @param keys
     * @return java.util.List<java.lang.Object>
     * @description redis cluster集群模式下 通过管道批量获取
     * @author wz
     * @date 2021/2/22 13:58
     */
    //todo jedis 4.0 需要更新代码
//    public static <T> List<T> redisClusterPipelineGet(String... keys) throws Exception {
//        info("redisClusterPipelineGet " + keys);
//        JedisCluster jedisCluster = getJedisCluster();
//        if (jedisCluster == null) {
//            throw new Exception("pipelineGet 仅支持redis cluster模式下进行!");
//        }
//        JedisClusterPipeline pipeline = JedisClusterPipeline.pipelined(jedisCluster);
//        try {
//            pipeline.refreshCluster();
//            for (String k : keys) {
//                pipeline.get(k);
//            }
//            return pipeline.syncAndReturnAll();
//        } catch (Exception e) {
//            log(e);
//        }
//        return null;
//    }
    //todo jedis 4.0 需要更新代码
//    public static <T> void redisClusterPipelineSet(Map<String, T> data) throws Exception {
//        info("redisClusterPipelineSet " + data);
//        JedisCluster jedisCluster = getJedisCluster();
//        if (jedisCluster == null) {
//            throw new Exception("pipelineSet 仅支持redis cluster模式下进行!");
//        }
//        JedisClusterPipeline pipeline = JedisClusterPipeline.pipelined(jedisCluster);
//        try {
//            pipeline.refreshCluster();
//            for (Map.Entry<String, T> item : data.entrySet()) {
//                pipeline.set(item.getKey(), item.getKey());
//            }
//        } catch (Exception e) {
//            log(e);
//        } finally {
//            pipeline.sync();
//        }
//
//    }

    /**
     * @param key   指定key
     * @param value 指定value
     * @param score value对应分数
     * @description: 指定value的分数
     */
    public static void zadd(String key, String value, Double score) {
        JedisCommands cmd = null;
        try {
            info("zadd " + key + " " + value);
            cmd = RedisStoreCmd.getCmd();
            cmd.zadd(key, score, value);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
    }

    /**
     * @param key   key
     * @param value 要删除的集合中元素
     * @return: void
     * @description: 删除指定zset中value
     * @author:wz
     * @date: 2021/2/4 11:32
     */

    public static void zrem(String key, String value) {
        JedisCommands cmd = null;
        try {
            info("zrem " + key + " " + value);

            cmd = RedisStoreCmd.getCmd();
            cmd.zrem(key, value);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
    }

    public static List<String> zrange(String key, long start, long end) {
        JedisCommands cmd = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            return cmd.zrange(key, start, end);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return null;
    }

    public static Long zcard(String key) {
        JedisCommands cmd = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            return cmd.zcard(key);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return 0L;
    }

    /**
     * @param key   zset key
     * @param start 开始的位置
     * @param end   结束的位置
     * @param min   分数最小值
     * @param max   分数最大值
     * @return java.util.List<java.lang.String>
     * @description: 获取zset
     * @author:wz
     * @date 2021/2/4 11:13
     */
    public static List<String> zrangeByScore(String key, Integer start, Integer end, Double min, Double max) {
        JedisCommands cmd = null;
        List<String> set = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            set = cmd.zrangeByScore(key, min, max, start, end);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return set;
    }

    /**
     * [ key: 栏目ID，value：内容ID：排序：Score]
     *
     * @param key       指定的key
     * @param pageIndex 页码
     * @param pageSize  每页大小
     * @param min       获取数据的最小范围值
     * @param max       获取数据的最大范围值
     * @param isMin2Max 是否是从小到大
     * @description: 获取指定key 在min->max范围内 进行 isMin2Max(小->大或反之) 排序 并进行 pageIndex&pageSize分页
     */
    public static List<String> getPageDataByScore(String key, Integer pageIndex, Integer pageSize, Double min, Double max, Boolean isMin2Max) {
        JedisCommands cmd = null;
        List<String> set = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            Page page = transPage(pageIndex, pageSize);
            set = isMin2Max ? cmd.zrangeByScore(key, min, max, page.start, page.end) :
                    cmd.zrangeByScore(key, max, min, page.start, page.end);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return set;
    }

    /**
     * [ key: 栏目ID，value：内容ID：排序：Score]
     * 获取栏目下所有的内容详情
     *
     * @param key       指定的key
     * @param pageIndex 页码
     * @param pageSize  每页大小
     * @param min       获取数据的最小范围值
     * @param max       获取数据的最大范围值
     * @param isMin2Max 是否是从小到大
     * @description: 获取指定key 在min->max范围内 进行 isMin2Max(小->大或反之) 排序 并进行 pageIndex&pageSize分页
     */
    public static List<String> getPageDetailDataByScore(String key, Integer pageIndex, Integer pageSize, Double min, Double max, Boolean isMin2Max) {
        JedisCommands cmd = null;
        List<String> set = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            set = getPageDataByScore(key, pageIndex, pageSize, min, max, isMin2Max);
            return mget(cmd, set);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return null;
    }

    /**
     * [ key: 栏目ID，value：内容ID：排序：Score]
     *
     * @param key       指定key
     * @param pageIndex 页码
     * @param pageSize  每页大小
     * @param isMin2Max 是否从小到大
     * @description: 获取分页数据
     */
    public static List<String> getPageData(String key, Integer pageIndex, Integer pageSize, Boolean isMin2Max) {
        JedisCommands cmd = null;
        List<String> set = null;
        try {
            info("getPageData " + key);

            cmd = RedisStoreCmd.getCmd();
            Page page = transPage(pageIndex, pageSize);
            set = isMin2Max ? cmd.zrange(key, page.start, page.end) :
                    cmd.zrange(key, page.start, page.end);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return set;
    }

    /**
     * [ key: 栏目ID，value：内容ID：排序：Score]
     * 获取栏目下所有的内容详情
     *
     * @param key       指定的key
     * @param pageIndex 页码
     * @param pageSize  每页大小
     * @param isMin2Max 是否是从小到大
     * @description: 获取指定key 所有的value(对应score) 进行 isMin2Max(小->大或反之) 排序 并进行 pageIndex&pageSize分页
     */
    public static List<String> getPageDetailData(String key, Integer pageIndex, Integer pageSize, Boolean isMin2Max) {
        JedisCommands cmd = null;
        List<String> set = null;
        try {
            cmd = RedisStoreCmd.getCmd();
            set = getPageData(key, pageIndex, pageSize, isMin2Max);
            return mget(cmd, set);
        } catch (Exception e) {
            log(e);
        } finally {
            RedisStoreCmd.close(cmd);
        }
        return null;
    }

    private static List<String> mget(JedisCommands cmd, List<String> set) {
        String[] keysArr = new String[set.size()];
        return mget(cmd, set.toArray(keysArr));
    }

    private static List<String> mget(JedisCommands cmd, String[] keysArr) {
        //pipeline 的二进制数据传输模式
        //todo
//        if (cmd instanceof BinaryJedisClusterCommands) {
//            try {
//                return redisClusterPipelineGet(keysArr);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        if (cmd instanceof Jedis) {
            Jedis jcmd = (Jedis) cmd;
            return jcmd.mget(keysArr);
        }
        if (cmd instanceof Pipeline) {
            Pipeline jcmd = (Pipeline) cmd;
            return jcmd.mget(keysArr).get();
        }
        return null;
    }

    private static <T> void mset(JedisCommands cmd, Map<String, T> data) {
        if (cmd instanceof JedisCluster) {
            try {
                //todo jedis 4.0 需要更新代码
                //redisClusterPipelineSet(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cmd instanceof Jedis) {
            Jedis jcmd = (Jedis) cmd;
            jcmd.mset(transData2Cmd(data));
        }
        if (cmd instanceof Pipeline) {
            Pipeline jcmd = (Pipeline) cmd;
            jcmd.mset(transData2Cmd(data));
        }
    }

    private static <T> String[] transData2Cmd(Map<String, T> data) {
        if (data != null && data.size() > 0) {
            int len = data.size();
            String[] keys = data.keySet().toArray(new String[0]);
            String[] result = new String[len * 2];
            for (int i = 0; i < len; i++) {
                result[2 * i] = keys[i];
                T t = data.get(keys[i]);
                result[2 * i + 1] = null == t ? null : (String) t;
            }
            return result;
        }
        return null;
    }

    public static <T> void mset(Map<String, T> data) {

    }

    public static boolean exists(String key) {
        try {
            JedisCommands cmd = RedisStoreCmd.getCmd();
            return cmd.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


//    public static <T> void msetWithPipeline(Map<String, T> data) {
//        Pipeline pipeline = null;
//        try {
//            pipeline = RedisStoreCmd.getPipeLine();
//            for (Map.Entry<String, T> item : data.entrySet()) {
//                pipeline.set(item.getKey(), item.getKey());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (pipeline != null) {
//                pipeline.sync();
//            }
//            RedisStoreCmd.close(pipeline);
//        }
//    }


//    public static List<String> mgetWithPipeline(String... keys) {
//        Pipeline pipeline = null;
//        List<String> result = new ArrayList<>(keys.length);
//        try {
//            pipeline = RedisStoreCmd.getPipeLine();
//            for (String key : keys) {
//                result.add(pipeline.get(key).get());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (pipeline != null) {
//                pipeline.sync();
//            }
//            RedisStoreCmd.close(pipeline);
//        }
//        return result;
//    }
}
