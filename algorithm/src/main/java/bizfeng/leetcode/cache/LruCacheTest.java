package bizfeng.leetcode.cache;

public class LruCacheTest {
    public static void main(String[] args) {
        LruCache<String, Integer> cache = new LruCache<>(3);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);
        cache.put("4", 4);

        System.out.println(cache.print());

        cache.put("5", 5);
        cache.put("6", 6);
        cache.put("7", 7);
        System.out.println(cache.print());
        System.out.println("size: " + cache.size());

        cache.put("8", 8);
        System.out.println(cache.print());
        System.out.println("size: " + cache.size());

        System.out.println(cache.get("6"));
        System.out.println(cache.get("7"));
        System.out.println(cache.print());
        System.out.println("size: " + cache.size());

        cache.del("7");
        System.out.println(cache.print());
        System.out.println("size: " + cache.size());

    }
}
