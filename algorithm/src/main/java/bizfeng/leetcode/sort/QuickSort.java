package bizfeng.leetcode.sort;

import bizfeng.leetcode.Shuffle;

import java.util.Arrays;

/**
 * 快速排序
 * 1.随机找一个基数
 * 2.两个指针 一前一后 分别与基数比较 小的放前面 大的放后面
 * 3.全部过完之后，只能确认基数的正确位置
 * 4.随后对前面部分和后面部分别进行递归操作
 * 5.最终前后集合都找到正确的位置
 */
public class QuickSort implements Sort<Integer> {
    private final static String NAME = "快速排序";
    public static QuickSort me = new QuickSort();

    public static void main(String[] args) {
        Integer[] data = new Integer[]{9, 3, 10, 41, 16, 7, 21, 6, 4, 32, 2, 1, 19, 78};
        me.sort(data);
        System.out.println(Arrays.toString(data));
    }

    public String name() {
        return NAME;
    }

    public void sort(Integer[] array) {
        //使用随机洗牌算法打乱数据
        Shuffle.random(array);
        sort(array, 0, array.length - 1);
    }

    //从小到大排列
    private static void sort(Integer[] array, int left, int right) {
        if (left >= right) return;
        //找到交换元素的分界点索引P
        int p = partition(array, left, right);
        sort(array, left, p - 1);
        sort(array, p + 1, right);
    }

    private static int partition(Integer[] array, int left, int right) {
        if (left == right) {
            return left;
        }
        //找一个基准值
        int pivot = array[left];
        //两个指针
        int i = left;
        int j = right + 1;
        while (true) {
            //从左往右找比基准值大的坐标
            while (array[++i] < pivot) {
                if (i == right) break;
            }
            //从右往左找比基准值小的坐标
            while (array[--j] > pivot) {
                if (j == left) break;
            }
            //如果两个指针碰撞，说明遍历完毕，直接跳出循环
            if (i >= j) break;
            //否则 将基准值左右的数据进行交换 这样左边的数据就比基数小 右边的就比基数大
            swap(array, i, j);
        }
        //用中间位置的j和基准值 交换位置
        swap(array, j, left);
        return j;
    }

    //交换
    private static void swap(Integer[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

}
