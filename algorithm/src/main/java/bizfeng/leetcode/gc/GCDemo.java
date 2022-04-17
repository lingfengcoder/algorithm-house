package bizfeng.leetcode.gc;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wz
 * @Date: 2022/3/28 14:12
 * @Description: GC分析
 * <p>
 * <p>
 * -Xmx10M
 * -Xms10M
 * -Xmn5M
 * -XX:PermSize128M
 * -XX:MaxPermSize256M
 * -XX:SurvivorRatio=8
 * -XX:MaxTenuringThreshold=5
 * -XX:TargetSurvivorRatio=50
 * -XX:+PrintGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps
 * -XX:+PrintGCDateStamps
 * -XX:+PrintGCApplicationStoppedTime
 * -XX:+PrintHeapAtGC
 * -XX:+PrintReferenceGC
 * -XX:+PrintSafepointStatistics
 * -XX:PrintSafepointStatisticsCount=1
 * -XX:+PrintTenuringDistribution
 * -XX:+UseGCLogFileRotation
 * -Xloggc:./algorithm/log/gc-%t.log
 * -XX:NumberOfGCLogFiles=10
 * -XX:GCLogFileSize=10M
 * -Djava.rmi.server.hostname=127.0.0.1
 * -Dcom.sun.management.jmxremote.port=1000
 * -Dcom.sun.management.jmxremote.ssl=false
 * -Dcom.sun.management.jmxremote.authenticate=false
 */
@Slf4j
public class GCDemo {

    private static final List<Byte[]> cache = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        log.info("用户的主目录:" + System.getProperty("user.home"));
        log.info("用户的当前工作目录:" + System.getProperty("user.dir"));

        while (true) {
            memoryTest();
            TimeUnit.SECONDS.sleep(1);
        }
    }


    static int count = 0;

    private static void memoryTest() {
        int size = 1024 * RandomUtil.randomInt(8, 128);
        byte[] data = new byte[size];
        System.out.println(count++);
    }
}
