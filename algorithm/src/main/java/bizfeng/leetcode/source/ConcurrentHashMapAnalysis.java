package bizfeng.leetcode.source;

import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: wz
 * @Date: 2022/5/5 15:31
 * @Description:
 */
@Slf4j
public class ConcurrentHashMapAnalysis {

    public static void main(String[] args) throws InterruptedException {


        syncSimpleObj();
        Thread.currentThread().join();

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();//1856ms
        ConcurrentSkipListMap<String, String> skipListMap = new ConcurrentSkipListMap<>();//3054ms
        Map<String, String> syncHashMap = Collections.synchronizedMap(new HashMap<>());//2609ms  2504ms 2582ms
        HashMap<String, String> hashMap = new HashMap<>();
        int x = 100000;
        testPut(concurrentHashMap, x, 20);
        log.info("DONE");
    }


    @Data
    @AllArgsConstructor
    static class Demo {
        private int id;
    }

    //对象锁的验证 结论：synchronized 可以针对单个对象加锁，只有多线程访问同一个对象时才会发同步阻塞
    private static void syncSimpleObj() throws InterruptedException {
        int x = 10;
        Map<Integer, Demo> map = new HashMap<>();
        for (int i = 0; i < x; i++) {
            Demo demo = new Demo(i);
            map.put(i, demo);
        }

        new Thread(() -> {
            Thread thread = Thread.currentThread();
            int v = 9;
            while (v > 0) {
                Demo demo = get(map, v);
                log.info("{}获取到数据{}", thread.getName(), demo.id);
                v--;
            }
        }, "biz--1").start();
        new Thread(() -> {
            Thread thread = Thread.currentThread();
            int v = 0;
            while (v < 10) {
                Demo demo = get(map, v);
                log.info("{}获取到数据{}", thread.getName(), demo.id);
                v++;
            }
        }, "biz++2").start();
    }

    private static Demo get(Map<Integer, Demo> map, int id) {
        Demo demo = map.get(id);
        synchronized (demo) {
            Thread thread = Thread.currentThread();
            log.info("{} get sync lock id={}", thread.getName(), id);
            long time = 50;
            try {
                if (id == 7) {
                    time = 5000;
                    thread.sleep(time);
                    log.info("{} sleep  {} done", thread.getName(), time);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return demo;
    }


    private static long testPut(Map<String, String> map, int count, int threadCount) {
        log.info("");
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 1; i < threadCount + 1; i++) {
            int finalI = i;
            new Thread(() -> {
                for (int c = 0; c < count; c++) {
                    map.put(finalI + "" + c + "" + RandomUtil.randomString(4), finalI + "" + c + "");
                }
                Thread thread = Thread.currentThread();
                log.info("{}", thread.getName() + "finished ");
                latch.countDown();
            }, "biz-" + finalI).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        log.info("[testPut] 耗时：{}", (end - start));
        log.info("map size: {}", map.size());
        return cost;
    }
}
