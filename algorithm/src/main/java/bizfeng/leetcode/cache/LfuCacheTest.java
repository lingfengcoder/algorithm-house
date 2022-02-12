package bizfeng.leetcode.cache;

public class LfuCacheTest {
    public static void main(String[] args) {

        String[] data = new String[]{"1", "1", "1", "2", "2","2", "3", "4","4", "4", "5", "5"};

        LfuCache<String, Integer> cache = new LfuCache<>(3);


        for (String item : data) {
            cache.put(item, 0);
            if (item.equals("5")) {
                //cache.get("5");
            }
        }

        System.out.println(cache.print());
    }
}
