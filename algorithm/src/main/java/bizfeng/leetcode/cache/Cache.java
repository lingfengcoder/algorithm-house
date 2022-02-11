package bizfeng.leetcode.cache;


public interface Cache<K, V> {
    //放入缓存
    boolean put(K k, V v);

    //从缓存中获取
    V get(K k);

    boolean del(K k);

    int size();
}
