package bizfeng.leetcode.redis;

/**
 * @author: wz
 * @date: 2021/2/3 16:56
 * @description: redis 连接类型
 */
public enum RedisTypeEnum {
    SIMPLE,//单例模式
    SENTINEL,//哨兵模式
    CLUSTER//集群分片模式
}
