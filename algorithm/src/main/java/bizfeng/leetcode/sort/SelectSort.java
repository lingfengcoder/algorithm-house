package bizfeng.leetcode.sort;

import bizfeng.leetcode.Shuffle;

import java.util.Arrays;

/**
 * 选择排序
 * 1.遍历无序的数组 找到最小值，与左侧第一个数进行交换，此时左侧是最小的数字
 * 2.对剩余数据，循环进行上述的操作直到所有的数据都排序完毕
 */
public class SelectSort implements Sort<Integer> {
    private final static String NAME = "选择排序";
    public static SelectSort me = new SelectSort();

    public static void main(String[] args) {
        Integer[] data = new Integer[]{9, 3, 10, 41, 16, 7, 21, 6, 4, 32, 2, 1, 19, 78};
        me.sort(data);
        System.out.println(Arrays.toString(data));
    }

    public void sort(Integer[] array) {
        //使用随机洗牌算法打乱数据
        Shuffle.random(array);
        sort(array, 0);
    }

    public String name() {
        return NAME;
    }

    private static void sort(Integer[] array, int left) {
        if (left >= array.length) {
            return;
        }
        int pivot = array[left];
        int idx = left;
        //找到最小值
        for (int i = left + 1; i < array.length; i++) {
            if (array[i] < pivot) {
                pivot = array[i];
                idx = i;
            }
        }
        //用区间内最小的数和区间最左侧的数进行交换
        swap(array, left, idx);
        //继续对剩余无序的数据进行选择排序
        sort(array, left + 1);
    }

    private static void swap(Integer[] array, int x, int y) {
        int tmp = array[x];
        array[x] = array[y];
        array[y] = tmp;
    }
}
