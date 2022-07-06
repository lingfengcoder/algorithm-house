package bizfeng.leetcode.redis.pipeline;



import bizfeng.leetcode.redis.RedisStoreUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: wz
 * @date: 2021/2/5 10:27
 * @description:
 */
public class TestDemo {


    public static void testPipelineGet() {
        String key = "wz66609";
        for (int x = 0; x < 10; x++) {
            RedisStoreUtil.zadd(key, "val" + x, x + 0.0);
            Set<String> pageData = RedisStoreUtil.getPageData(key, 0, 10, false);
            System.out.println(pageData);
        }
        RedisStoreUtil.zadd("ccc", "1", 1.0);
        RedisStoreUtil.zadd("ccc", "2", 22.0);
        RedisStoreUtil.zadd("ccc", "3", 333.0);
        RedisStoreUtil.zadd("ccc", "4", 444.0);

        Set<String> ccc = RedisStoreUtil.getPageData("ccc", 0, 10, false);
        System.out.println(ccc);
        List<String> wz666;
        try {
            wz666 = RedisStoreUtil.mget("123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> data = new HashMap<>();
        for (int x = 0; x < 10; x++) {
            data.put("bbq" + x, "bbq_" + x);
        }
        // RedisStoreUtil.msetWithPipeline(data);
        wz666 = RedisStoreUtil.mget("bbq0", "bbq1", "bbq2", "bbq3", "bbq4", "bbq5");
        System.out.println(wz666);


    }



}
