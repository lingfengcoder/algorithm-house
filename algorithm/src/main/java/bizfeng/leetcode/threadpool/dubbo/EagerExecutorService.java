package bizfeng.leetcode.threadpool.dubbo;

import java.util.concurrent.ExecutorService;

/**
 * @Auther: wz
 * @Date: 2022/6/6 15:47
 * @Description:
 */
public interface EagerExecutorService extends ExecutorService {

    /**
     * Returns the current number of threads in the pool.
     *
     * @return the number of threads
     */
    int getPoolSize();

    /**
     * Returns the approximate number of threads that are actively
     * executing tasks.
     *
     * @return the number of threads
     */
    int getActiveCount();

    /**
     * Returns the maximum allowed number of threads.
     *
     * @return the maximum allowed number of threads
     */
    int getMaximumPoolSize();
}
