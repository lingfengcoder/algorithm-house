package bizfeng.leetcode.threadpool.future;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

/**
 * @Auther: wz
 * @Date: 2022/6/7 17:56
 * @Description:
 */
public class ExceptionUtils {
    /**
     * 提取真正的异常
     */
    public static Throwable extractRealException(Throwable throwable) {
        if (throwable instanceof CompletionException || throwable instanceof ExecutionException) {
            if (throwable.getCause() != null) {
                return throwable.getCause();
            }
        }
        return throwable;
    }
}
