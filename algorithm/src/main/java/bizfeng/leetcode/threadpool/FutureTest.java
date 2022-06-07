package bizfeng.leetcode.threadpool;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Auther: wz
 * @Date: 2022/6/7 14:14
 * @Description:
 */
@Slf4j
public class FutureTest {


    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4,
            4, 20, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        threadPool.execute(new TTask());
        threadPool.execute(new RTask());
        Future<?> future = threadPool.submit(new RTask());
        log.info("RTask result = {}", future.get());

        Future<String> cFuture = threadPool.submit(new CTask());
        log.info("cFuture result={}", cFuture.get());

        FTask fTask = new FTask(new CTask());
        Future<?> fFuture = threadPool.submit(fTask);

        log.info("cFuture result={}", fFuture.get());
        TimeUnit.SECONDS.sleep(2);
        fFuture.cancel(true);
    }

    static class FTask extends FutureTask<String> {

        public FTask(Runnable runnable, String result) {
            super(runnable, result);
        }

        public FTask(Callable<String> callable) {
            super(callable);
        }
    }

    static class CTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread thread = Thread.currentThread();
            TimeUnit.SECONDS.sleep(4);
            log.info(" thread info={} CTask do call() ", thread);
            return "CTask:" + RandomUtil.randomString(5);
        }
    }

    static class RTask implements Runnable {

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            log.info(" thread info={} RTask do run() ", thread);
        }
    }

    static class TTask extends Thread {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            log.info(" thread info={} TTask do run() ", thread);
        }
    }

}
