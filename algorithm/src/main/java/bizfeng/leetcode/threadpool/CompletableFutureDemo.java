package bizfeng.leetcode.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: wz
 * @Date: 2022/6/7 15:14
 * @Description:
 */
@Slf4j
public class CompletableFutureDemo {

    static {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        CompletableFuture<String> t1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("t1 do action");
            return "result1";
        }, executor);

        CompletableFuture<String> t2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("t2 do action");
            return "result2";
        }, executor);

        CompletableFuture<String> t3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("t3 do action");
            return "result3";
        }, executor);

        t1.thenCombine(t2, (res1, res2) -> {
            log.info("step3: get result={} {}", res1, res2);
            return "res3";
        });

        CompletableFuture<Void> t4 = CompletableFuture.allOf(t1, t2, t3);
        CompletableFuture<String> t4_result = t4.thenApply(v -> {
//            String t1Result = t1.join();
//            String t2Result = t2.join();
//            String t3Result = t3.join();
//            log.info("t1 result={}", t1Result);
//            log.info("t2 result={}", t2Result);
//            log.info("t3 result={}", t3Result);

            log.info("V={}", v);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("t4 do action");
            return "result4";
        });

        t4_result.whenComplete((v, e) -> {
            if (e != null) {
                log.error(e.getMessage());
            } else {
                log.error("final result ={}", v.toLowerCase());
            }
        });


    }

    public static void main(String[] args) {

    }
}
