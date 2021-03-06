package bizfeng.leetcode.threadpool.dubbo.queue.plan2;

import bizfeng.leetcode.threadpool.dubbo.EagerExecutorService;
import bizfeng.leetcode.threadpool.dubbo.TaskQueue;

import java.util.Collection;

/**
 * @Auther: wz
 * @Date: 2022/6/6 15:49
 * @Description:
 */
/**
 * MemorySafeTaskQueue in the {@link org.apache.shenyu.common.concurrent.ShenyuThreadPoolExecutor}.
 * It offer a task if the executor's submittedTaskCount less than currentPoolThreadSize
 * or the currentPoolThreadSize more than executor's maximumPoolSize.
 * That can make the executor create new worker
 * when the task num is bigger than corePoolSize but less than maximumPoolSize.
 */
public class MemorySafeTaskQueue<R extends Runnable> extends MemorySafeLinkedBlockingQueue<Runnable> implements TaskQueue<Runnable> {

    private static final long serialVersionUID = -1998413481091670338L;

    private EagerExecutorService executor;

    public MemorySafeTaskQueue(final int maxFreeMemory) {
        super(maxFreeMemory);
    }

    public MemorySafeTaskQueue(final Collection<? extends Runnable> c, final int maxFreeMemory) {
        super(c, maxFreeMemory);
    }

    @Override
    public EagerExecutorService getExecutor() {
        return this.executor;
    }

    @Override
    public void setExecutor(final EagerExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public boolean doOffer(final Runnable runnable) {
        return super.offer(runnable);
    }

}
