package bizfeng.leetcode.thread;

import cn.hutool.core.thread.threadlocal.NamedInheritableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: wz
 * @Date: 2022/8/26 21:08
 * @Description:
 */
@Slf4j
public class ThreadLocalDemo {

    private final static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private final static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.initialize();
        //第一次设置
        threadLocal.set("main threadLocal set 111");
        inheritableThreadLocal.set("main inheritableThreadLocal set inherit 111");
        new Thread(() -> {
            log.info("threadLocal.get ={}", threadLocal.get());
            while (true) {
                log.info("inheritableThreadLocal.get ={}", inheritableThreadLocal.get());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        TimeUnit.SECONDS.sleep(2);
        //第二次设置
        log.info("第二次设置");
        inheritableThreadLocal.set("main inheritableThreadLocal set inherit 222 ");

    }
}
