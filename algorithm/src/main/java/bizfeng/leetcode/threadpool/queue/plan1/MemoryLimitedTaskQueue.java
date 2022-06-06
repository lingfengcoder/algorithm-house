package bizfeng.leetcode.threadpool.queue.plan1;

import bizfeng.leetcode.threadpool.EagerExecutorService;
import bizfeng.leetcode.threadpool.TaskQueue;

import java.lang.instrument.Instrumentation;

/**
 * @Auther: wz
 * @Date: 2022/6/6 15:45
 * @Description:
 */
public class MemoryLimitedTaskQueue<R extends Runnable> extends MemoryLimitedLinkedBlockingQueue<Runnable> implements TaskQueue<Runnable> {

    private static final long serialVersionUID = -2635853580887179627L;

    private EagerExecutorService executor;

    public MemoryLimitedTaskQueue(final Instrumentation inst) {
        super(inst);
    }

    public MemoryLimitedTaskQueue(final long memoryLimit, final Instrumentation inst) {
        super(memoryLimit, inst);
    }

    @Override
    public EagerExecutorService getExecutor() {
        return executor;
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
