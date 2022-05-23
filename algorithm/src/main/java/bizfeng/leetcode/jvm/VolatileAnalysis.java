package bizfeng.leetcode.jvm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @Author: wz
 * @Date: 2022/5/23 14:28
 * @Description:
 */
@Slf4j
public class VolatileAnalysis {

    private static volatile int[] array = new int[100];
    //private static volatile int tmpIndex = 0;

    private static void testTmpArrayVolatile() {
        Thread thread = Thread.currentThread();
        int[] tmpArray = array;
        for (int i = 0; i < tmpArray.length; i++) {
            // log.info("tmpIndex={} thread={}", tmpIndex, thread.getName());
            if (tmpArray[i] != i) {
               // throw new RuntimeException("error");
            }
            log.info("i={} data[i]={} thread={}", i, tmpArray[i], thread.getName());
        }
    }

    private static void testVolatileArrayVolatile() {
        Thread thread = Thread.currentThread();
        for (int i = 0; i < array.length; i++) {
            // log.info("tmpIndex={} thread={}", tmpIndex, thread.getName());
            log.info("i={} data[i]={} thread={}", i, array[i], thread.getName());
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Runnable main = () -> {
            int x = array.length;
            for (int i = 0; i < x; i++) {
                // tmpIndex = tmpIndex + 1;
                array[i] = i;
                log.info("modify:{} ", i);
//                log.info("modify:{} tmpIndex={}", i, tmpIndex);
                //System.out.println("modify:" + i + " tmpIndex=" + tmpIndex);
            }
        };
        Supplier<Runnable> observe = () -> VolatileAnalysis::testTmpArrayVolatile;

        CompletableFuture<Void> future = CompletableFuture.runAsync(main);
        Stream.generate(observe).limit(Runtime.getRuntime().availableProcessors() - 1)
                .forEach(CompletableFuture::runAsync);
        Thread.sleep(3000);
    }

}
