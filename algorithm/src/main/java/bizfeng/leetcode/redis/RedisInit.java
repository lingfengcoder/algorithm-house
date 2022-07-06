package bizfeng.leetcode.redis;



import bizfeng.leetcode.redis.config.RedisClusterConfig;
import bizfeng.leetcode.redis.config.RedisConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @author: wz
 * @date: 2021/2/3 20:51
 * @description:
 */
//@Component
public class RedisInit  {
    private static final Log log = LogFactory.getLog(RedisInit.class);

    private static boolean TEST_ON_BORROW = true;
    private static int DB_INDEX = 0;
    private static boolean isRuning = false;
    public static boolean isEnabled = true;

    private static RedisConfig preparedConfig() {
        String HOST = "192.168.14.84";
        String AUTH = "7379";
        int MAX_ACTIVE =  1024;
        int MAX_IDLE =  8;
        int MAX_WAIT = 10000;
        int TIMEOUT =  10000;
        //设置redis连接模式
        RedisClusterConfig config = new RedisClusterConfig();
//        RedisSimpleConfig config = new RedisSimpleConfig();
        config.setHostLink(HOST);
        config.setAuth(AUTH);
        config.setMaxActive(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWait(MAX_WAIT);
        config.setTimeout(TIMEOUT);
        return config;
    }

    public static void redisInit() {
        //初始化redis连接
        RedisStoreConnection.connect(preparedConfig());
        log.info("redis store redis持久化连接池 初始化成功！");
    }

    //在spring容器启动直接连接redis

    public void onApplication(ApplicationContext ctx) {
        redisInit();
        //testPipelineGet();
    }
}
