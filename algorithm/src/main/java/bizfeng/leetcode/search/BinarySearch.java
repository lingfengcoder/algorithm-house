package bizfeng.leetcode.search;

import bizfeng.leetcode.Shuffle;

/**
 * 二分查找
 */
public class BinarySearch implements Search<Integer> {
    private final static String NAME = "二分查找";
    public final static BinarySearch me = new BinarySearch();

    public static void main(String[] args) {
        int count = 33333;
        Integer[] array = new Integer[count];
        for (int i = 0; i < count; i++) {
            array[i] = i;
        }
        for (int i = 0; i < count; i++) {
            int x = find(array, i);
            System.out.println("i=" + i + "在索引位置" + x);
        }

    }

    public int search(Integer[] array, Integer target) {
        //使用随机洗牌算法打乱原始数组
        Shuffle.random(array);
        return find(array, target);
    }

    //假设array是从小到大
    //找不到返回-1
    //1,2,3,4,5,6,7
    private static int find(Integer[] array, Integer target) {
        int left = 0;
        int right = array.length - 1;
        int middle = 0;
        while (left <= right) {
            middle = (left + right) / 2;
            if (target.equals(array[middle])) {
                return middle;
            } else if (array[middle] > target) {
                right = middle - 1;
            } else if (array[middle] < target) {
                left = middle + 1;
            }
        }
        return -1;
    }


    public String name() {
        return NAME;
    }
}
