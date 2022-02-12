package bizfeng.leetcode.cache;

import java.util.*;

/**
 * LFU 缓存策略 淘汰访问(put get )频率最小的元素
 * 注意点：min-req的设置 和 触发淘汰策略的条件
 *
 * @param <K>
 * @param <V>
 */
public class LfuCache<K, V> implements Cache<K, V> {
    @Override
    public boolean put(K k, V v) {
        this.putKV(k, v);
        return true;
    }

    @Override
    public V get(K k) {
        return this.getV(k);
    }

    @Override
    public boolean del(K k) {
        delKV(k);
        return true;
    }

    @Override
    public int size() {
        return getSize();
    }

    //容量
    private int capacity = 0;
    //最小访问频率
    private int minFreq = 0;
    //数据存储
    private HashMap<K, V> cache;
    //key-freq map
    private HashMap<K, Integer> k2Freq;
    //freq - key 带访问时间的存储形式
    private HashMap<Integer, LinkedHashSet<K>> freq2k;

    public LfuCache(int cap) {
        this.capacity = cap;
        cache = new HashMap<>(cap);
        k2Freq = new HashMap<>(cap);
        freq2k = new HashMap<>(cap);
        minFreq = 1;
    }

    /**
     * 存入 频率+1
     */
    private void putKV(K k, V v) {
        //如果key原本就存在
        if (cache.size() >= capacity && !cache.containsKey(k)) {
            //满了要执行淘汰策略
            removeMinFreqNode();
        }
        //存入数据
        cache.put(k, v);
        coreAction(k);
    }

    /**
     * 获取 频率+1
     */
    private V getV(K k) {
        if (cache.containsKey(k)) {
            coreAction(k);
            return cache.get(k);
        }
        return null;
    }

    /**
     * 删除元素K-V
     * 如果删除的是最小频率的，并且最小频率组为空，那么要删除最小频率组，重新再集合中找到最小频率
     */
    private void delKV(K k) {
        //数据删除
        cache.remove(k);
        //获取旧频率
        int oldFreq = getOldFreq(k);
        //key-freq删除key
        k2Freq.remove(k);
        //找到频率组并删除key
        LinkedHashSet<K> ks = freq2k.get(oldFreq);
        ks.remove(k);
        //如果组空了，需要剔除该频率组
        if (ks.size() == 0) {
            freq2k.remove(oldFreq);
            //如果最小频率对应的ks为空，则删除ks并要找到最小频率
            if (minFreq == oldFreq) {
                reloadMinFreq();
            }
        }
    }

    /**
     * 重新再集合中找到最小频率
     */
    private void reloadMinFreq() {
        Iterator<Map.Entry<Integer, LinkedHashSet<K>>> iterator = freq2k.entrySet().iterator();
        if (iterator.hasNext()) {
            minFreq = iterator.next().getKey();
        }
        while (iterator.hasNext()) {
            minFreq = Math.min(minFreq, iterator.next().getKey());
        }
    }

    /**
     * 获取缓存元素数量
     */
    private int getSize() {
        return cache.size();
    }

    /**
     * 核心动作 将k的频率+1 并对k进行重新分组(移动到频率+1的组里)
     */
    private void coreAction(K k) {
        //访问频率+1
        int oldFreq = getOldFreq(k);
        //更新频率
        updateKFreq(k);
        //换组
        joinNewGroup(k, oldFreq);
    }

    private int getOldFreq(K k) {
        Integer f = k2Freq.get(k);
        return f == null ? 0 : f;
    }

    /**
     * 1.找到key的上一次访问频率
     * 2.更新key的访问频率+1
     * 3.更新最小访问频率
     */
    private void updateKFreq(K k) {
        //访问频率+1
        int newFreq = getOldFreq(k) + 1;
        k2Freq.put(k, newFreq);
        //更新最小频率
        minFreq = Math.min(minFreq, newFreq);
    }

    /**
     * 1.找到访问频率最小的组
     * 2.找到时间最早加入该组的key(头部元素)
     * 3.从三个集合中都删除key
     */
    private void removeMinFreqNode() {
        //找到访问频率最小的组
        LinkedHashSet<K> ks = freq2k.get(minFreq);
        //找到时间最早加入该组的元素(头部元素)
        K key = ks.iterator().next();
        //删除key
        delKV(key);
    }

    /**
     * 将k移动到频率+1的组
     * 在原组中删除key，如果原本组为空则直接删除组，
     * 如果该组还是最小频率组，则需要更新最小频率
     */
    private void joinNewGroup(K k, int oldFreq) {
        //新频率
        int newFreq = oldFreq + 1;
        //找到原本的key
        LinkedHashSet<K> ks = freq2k.get(oldFreq);
        if (ks != null) {
            //从原本频率组中删除
            ks.remove(k);
            //如果组内没有任何元素了
            if (ks.size() == 0) {
                //删除组
                freq2k.remove(oldFreq);
                //如果最小频率的对应组没有了 则更新最小频率
                if (minFreq == oldFreq) {
                    minFreq = newFreq;
                }
            }
        }
        //找到新的频率组
        LinkedHashSet<K> ks1 = freq2k.computeIfAbsent(newFreq, k1 -> new LinkedHashSet<>());
        //加入新的频率组
        ks1.add(k);
    }

    //打印
    public String print() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<K, Integer> item : k2Freq.entrySet()) {
            builder.append(" [ ")
                    .append("key=")
                    .append(item.getKey())
                    .append(" freq=")
                    .append(item.getValue())
                    .append(" ] ");
        }
        return builder.toString();
    }

}
