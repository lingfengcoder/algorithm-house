package bizfeng.leetcode.threadpool.future;

/**
 * @Auther: wz
 * @Date: 2022/6/7 17:53
 * @Description:
 */
@FunctionalInterface
public interface ThriftAsyncCall {
    void invoke() throws Exception ;
}