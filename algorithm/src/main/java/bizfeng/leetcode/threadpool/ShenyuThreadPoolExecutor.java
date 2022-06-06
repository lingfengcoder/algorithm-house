package bizfeng.leetcode.threadpool;

import java.util.concurrent.*;

/**
 * @Auther: wz
 * @Date: 2022/6/6 15:51
 * @Description:
 */
public class ShenyuThreadPoolExecutor extends ThreadPoolExecutor implements EagerExecutorService {

    public ShenyuThreadPoolExecutor(final int corePoolSize,
                                    final int maximumPoolSize,
                                    final long keepAliveTime,
                                    final TimeUnit unit,
                                    final TaskQueue<Runnable> workQueue,
                                    final ThreadFactory threadFactory,
                                    final RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        workQueue.setExecutor(this);
    }

    @Override
    public void execute(final Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }

        try {
            super.execute(command);
        } catch (RejectedExecutionException e) {
            // retry to offer the task into queue.
            final TaskQueue<Runnable> queue = (TaskQueue<Runnable>) super.getQueue();
            try {
                if (!queue.retryOffer(command, 0, TimeUnit.MILLISECONDS)) {
                    throw new RejectedExecutionException("Queue capacity is full.", e);
                }
            } catch (InterruptedException t) {
                throw new RejectedExecutionException(t);
            }
        }
    }
}
