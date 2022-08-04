package bizfeng.leetcode.redis.sentinel;

import bizfeng.leetcode.redis.RedisStoreConnection;
import bizfeng.leetcode.redis.RedisStoreUtil;
import bizfeng.leetcode.redis.config.RedisSentinelConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * @Auther: wz
 * @Date: 2022/7/6 16:48
 * @Description: jedis sentinel 模式测试
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        test1();
        while (true){
            TimeUnit.SECONDS.sleep(2);
        }
    }

    static {
        RedisSentinelConfig sentinelConfig = new RedisSentinelConfig();
        sentinelConfig.setHostLink("192.168.14.84:27379,192.168.14.84:37379,192.168.14.84:17379");
        sentinelConfig.setAuth("123456");
        sentinelConfig.setMaster("mymaster");
        //创建连接
        RedisStoreConnection.connect(sentinelConfig);

    }

    private static void test1(){
        String ggggg = RedisStoreUtil.get("ggggg");
        log.info("test1 get ggggg={}", ggggg);
    }
    private static void test(){
        String wztest = RedisStoreUtil.get("wztest");
        log.info("get wztest={}", wztest);
        String ggggg = RedisStoreUtil.get("ggggg");
        log.info("get ggggg={}", ggggg);
        try {
            String zset_test = RedisStoreUtil.get("zset_test");
            log.info("get zset_test={}", zset_test);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        Map<String, String> zset_test = RedisStoreUtil.hgetAll("zset_test");
        log.info("zset_test hget={}", zset_test);
    }
}
