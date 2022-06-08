# algorithm-house
一些算法和思考

1.二叉树

2.数组算法

3.缓存算法 LRU LFU DubboLFU DubboLRU  TinyLFU  W-TinyLFU 

4.哈希一致性算法 DscHashConsistencyVirtualNode

5.堆算法

6.GC测试

7.搜索算法

8.排序算法

9.流量控制算法 滑动窗口 漏桶算法

10.高性能线程安全算法

11.洗牌算法 Shuffle  

[](exportToHTML/bizfeng/leetcode/array/PrefixSum.java.html)



# CompletableFuture 实践总结

## 1.1 线程阻塞问题

### 1.1.1 代码执行在哪个线程上？

要合理治理线程资源，最基本的前提条件就是要在写代码时，清楚地知道每一行代码都将执行在哪个线程上。下面我们看一下CompletableFuture的执行线程情况。

CompletableFuture实现了CompletionStage接口，通过丰富的回调方法，支持各种组合操作，每种组合场景都有同步和异步两种方法。

同步方法（即不带Async后缀的方法）有两种情况。

- 如果注册时被依赖的操作已经执行完成，则直接由当前线程执行。
- 如果注册时被依赖的操作还未执行完，则由回调线程执行。
  异步方法（即带Async后缀的方法）：可以选择是否传递线程池参数Executor运行在指定线程池中；
  当不传递Executor时，会使用ForkJoinPool中的共用线程池CommonPool（CommonPool的大小是CPU核数-1，如果是IO密集的应用，线程数可能成为瓶颈）。

```java
class demo {
    ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
    CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
        System.out.println("supplyAsync 执行线程：" + Thread.currentThread().getName());
        //业务操作
        return "";
    }, threadPool1);
//此时，如果future1中的业务操作已经执行完毕并返回，则该thenApply直接由当前main线程执行；否则，将会由执行以上业务操作的threadPool1中的线程执行。
        future1.thenApply(value->

    {
        System.out.println("thenApply 执行线程：" + Thread.currentThread().getName());
        return value + "1";
    });
//使用ForkJoinPool中的共用线程池CommonPool
        future1.thenApplyAsync(value->

    {
//do something
        return value + "1";
    });
//使用指定线程池
        future1.thenApplyAsync(value->

    {
//do something
        return value + "1";
    },threadPool1);
}

```

## 1.2 线程池须知

### 1.2.1 异步回调要传线程池

前面提到，异步回调方法可以选择是否传递线程池参数Executor，这里我们建议强制传线程池，且根据实际情况做线程池隔离。

当不传递线程池时，会使用ForkJoinPool中的公共线程池CommonPool，这里所有调用将共用该线程池，核心线程数=处理器数量-1（单核核心线程数为1），所有异步回调都会共用该CommonPool，核心与非核心业务都竞争同一个池中的线程，很容易成为系统瓶颈。手动传递线程池参数可以更方便的调节参数，并且可以给不同的业务分配不同的线程池，以求资源隔离，减少不同业务之间的相互干扰。

### 1.2.2 线程池循环引用会导致死锁

```java
class demo {
    public Object doGet() {
        ExecutorService threadPool1 = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        CompletableFuture cf1 = CompletableFuture.supplyAsync(() -> {
            //do sth
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("child");
                return "child";
            }, threadPool1).join();//子任务
        }, threadPool1);
        return cf1.join();
    }
}
```

如上代码块所示，doGet方法第三行通过supplyAsync向threadPool1请求线程，并且内部子任务又向threadPool1请求线程。threadPool1大小为10，当同一时刻有10个请求到达，则threadPool1被打满，子任务请求线程时进入阻塞队列排队，但是父任务的完成又依赖于子任务，这时由于子任务得不到线程，父任务无法完成。主线程执行cf1.join()
进入阻塞状态，并且永远无法恢复。

**为了修复该问题，需要将父任务与子任务做线程池隔离，两个任务请求不同的线程池，避免循环依赖导致的阻塞**。

### 1.2.3 异步RPC调用注意不要阻塞IO线程池

服务异步化后很多步骤都会依赖于异步RPC调用的结果，这时需要特别注意一点，如果是使用基于NIO（比如Netty）的异步RPC，则返回结果是由IO线程负责设置的，即回调方法由IO线程触发，CompletableFuture同步回调（如thenApply、thenAccept等无Async后缀的方法）如果依赖的异步RPC调用的返回结果，那么这些同步回调将运行在IO线程上，而整个服务只有一个IO线程池，这时需要保证同步回调中不能有阻塞等耗时过长的逻辑，否则在这些逻辑执行完成前，IO线程将一直被占用，影响整个服务的响应。

## 1.3 其他

### 1.3.1 异常处理

由于异步执行的任务在其他线程上执行，而异常信息存储在线程栈中，因此当前线程除非阻塞等待返回结果，否则无法通过try\catch捕获异常。CompletableFuture提供了异常捕获回调exceptionally，相当于同步调用中的try\catch。使用方法如下所示：

```java
class demo {
    @Autowired
    private WmOrderAdditionInfoThriftService wmOrderAdditionInfoThriftService;//内部接口

    public CompletableFuture<Integer> getCancelTypeAsync(long orderId) {
        CompletableFuture<WmOrderOpRemarkResult> remarkResultFuture = wmOrderAdditionInfoThriftService.findOrderCancelledRemarkByOrderIdAsync(orderId);//业务方法，内部会发起异步rpc调用
        return remarkResultFuture
                .exceptionally(err -> {//通过exceptionally 捕获异常，打印日志并返回默认值
                    log.error("WmOrderRemarkService.getCancelTypeAsync Exception orderId={}", orderId, err);
                    return 0;
                });
    }
}
```

有一点需要注意，CompletableFuture在回调方法中对异常进行了包装。大部分异常会封装成CompletionException后抛出，真正的异常存储在cause属性中，因此如果调用链中经过了回调方法处理那么就需要用Throwable.getCause()
方法提取真正的异常。但是，有些情况下会直接返回真正的异常（Stack Overflow的讨论），最好使用工具类提取异常，如下代码所示：

```java
class demo {
    @Autowired
    private WmOrderAdditionInfoThriftService wmOrderAdditionInfoThriftService;//内部接口

    public CompletableFuture<Integer> getCancelTypeAsync(long orderId) {
        CompletableFuture<WmOrderOpRemarkResult> remarkResultFuture = wmOrderAdditionInfoThriftService.findOrderCancelledRemarkByOrderIdAsync(orderId);//业务方法，内部会发起异步rpc调用
        return remarkResultFuture
                .thenApply(result -> {//这里增加了一个回调方法thenApply，如果发生异常thenApply内部会通过new CompletionException(throwable) 对异常进行包装
                    //这里是一些业务操作
                })
                .exceptionally(err -> {//通过exceptionally 捕获异常，这里的err已经被thenApply包装过，因此需要通过Throwable.getCause()提取异常
                    log.error("WmOrderRemarkService.getCancelTypeAsync Exception orderId={}", orderId, ExceptionUtils.extractRealException(err));
                    return 0;
                });
    }
}
```

上面代码中用到了一个自定义的工具类ExceptionUtils，用于CompletableFuture的异常提取，在使用CompletableFuture做异步编程时，可以直接使用该工具类处理异常。实现代码如下：

```java
public class ExceptionUtils {
    public static Throwable extractRealException(Throwable throwable) {
        //这里判断异常类型是否为CompletionException、ExecutionException，如果是则进行提取，否则直接返回。
        if (throwable instanceof CompletionException || throwable instanceof ExecutionException) {
            if (throwable.getCause() != null) {
                return throwable.getCause();
            }
        }
        return throwable;
    }
}
```
