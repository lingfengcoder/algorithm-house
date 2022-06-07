package bizfeng.leetcode.threadpool.dubbo.queue.plan2;

/**
 * {@link javax.management.MXBean} technology is used to calculate the memory
 * limit by using the percentage of the current maximum available memory,
 * which can be used with {@link org.apache.shenyu.common.concurrent.MemoryLimiter}.
 *
 * @see org.apache.shenyu.common.concurrent.MemoryLimiter
 * @see <a href="https://github.com/apache/incubator-shenyu/blob/master/shenyu-common/src/main/java/org/apache/shenyu/common/concurrent/MemoryLimitCalculator.java">MemoryLimitCalculator</a>
 */

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MemoryLimitCalculator {


    private static final MemoryMXBean MX_BEAN = ManagementFactory.getMemoryMXBean();

    private static volatile long maxAvailable;

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();

    static {
        // immediately refresh when this class is loaded to prevent maxAvailable from being 0
        refresh();
        // check every 50 ms to improve performance
        SCHEDULER.scheduleWithFixedDelay(MemoryLimitCalculator::refresh, 50, 50, TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(SCHEDULER::shutdown));
    }

    private static void refresh() {
        final MemoryUsage usage = MX_BEAN.getHeapMemoryUsage();
        maxAvailable = usage.getCommitted();
        //note 建议修改成  maxAvailable =usage.getMax()- usage.getUsed()
    }

    /**
     * Get the maximum available memory of the current JVM.
     *
     * @return maximum available memory
     */
    public static long maxAvailable() {
        return maxAvailable;
    }

    /**
     * Take the current JVM's maximum available memory
     * as a percentage of the result as the limit.
     *
     * @param percentage percentage
     * @return available memory
     */
    public static long calculate(final float percentage) {
        if (percentage <= 0 || percentage > 1) {
            throw new IllegalArgumentException();
        }
        return (long) (maxAvailable() * percentage);
    }

    /**
     * By default, it takes 80% of the maximum available memory of the current JVM.
     *
     * @return available memory
     */
    public static long defaultLimit() {
        return (long) (maxAvailable() * 0.8);
    }


    private static boolean isFull() {

        MemoryUsage usage = MX_BEAN.getHeapMemoryUsage();
        long available = usage.getMax() - usage.getUsed();
        log.info(" memory committed = {}", usage.getCommitted());
        log.info("available mem={}", available);
        long limit = (long) (usage.getMax() * 0.3);
        log.info("limit mem={}", limit);
        return available < limit;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println();
        List<Byte[]> list = new ArrayList<>();
        boolean t = true;
        while (t) {
            TimeUnit.MILLISECONDS.sleep(1);
            if (isFull()) {
                log.error("full!!!!");
                log.error("full!!!!");
                log.error("full!!!!");
                list.clear();

                System.gc();
                TimeUnit.MILLISECONDS.sleep(4000);
                continue;
            }
            Byte[] data = new Byte[1024 * 10];
            list.add(data);
            Arrays.fill(data, (byte) 1);
            System.out.println("add");
            log.info("list.size={}", list.size());
        }
    }
}
