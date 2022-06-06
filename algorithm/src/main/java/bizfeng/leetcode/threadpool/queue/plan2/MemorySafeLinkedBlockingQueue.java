package bizfeng.leetcode.threadpool.queue.plan2;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wz
 * @Date: 2022/6/6 15:48
 * @Description:
 */
public class MemorySafeLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

    private static final long serialVersionUID = 8032578371749960142L;

    private int maxFreeMemory;

    public MemorySafeLinkedBlockingQueue(final int maxFreeMemory) {
        super(Integer.MAX_VALUE);
        this.maxFreeMemory = maxFreeMemory;
    }

    public MemorySafeLinkedBlockingQueue(final Collection<? extends E> c,
                                         final int maxFreeMemory) {
        super(c);
        this.maxFreeMemory = maxFreeMemory;
    }

    /**
     * set the max free memory.
     *
     * @param maxFreeMemory the max free memory
     */
    public void setMaxFreeMemory(final int maxFreeMemory) {
        this.maxFreeMemory = maxFreeMemory;
    }

    /**
     * get the max free memory.
     *
     * @return the max free memory limit
     */
    public int getMaxFreeMemory() {
        return maxFreeMemory;
    }

    /**
     * determine if there is any remaining free memory.
     *
     * @return true if has free memory
     */
    public boolean hasRemainedMemory() {
        return MemoryLimitCalculator.maxAvailable() > maxFreeMemory;
    }

    @Override
    public void put(final E e) throws InterruptedException {
        if (hasRemainedMemory()) {
            super.put(e);
        }
    }

    @Override
    public boolean offer(final E e, final long timeout, final TimeUnit unit) throws InterruptedException {
        return hasRemainedMemory() && super.offer(e, timeout, unit);
    }

    @Override
    public boolean offer(final E e) {
        return hasRemainedMemory() && super.offer(e);
    }
}
