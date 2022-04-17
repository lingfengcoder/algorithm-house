package bizfeng.leetcode;


import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 洗牌算法 在很多搜索和排序算法里面为了
 * 降低时间复杂度尽可能让算法 达到平均时间复杂度（较为理想）的状态
 * 需要使用洗牌算法先将原始数据的顺序打乱
 * 或者说是为了防止 最坏 的情况发生
 */
public class Shuffle {
    //todo 蒙特卡罗方法验证正确性
    //蒙特卡罗方法：同通过正方形和内切元的点数计算圆周率,当打的点足够多的时候，点的数量就可以近似代表图形的面积。通过面积公式，由正方形和圆的面积比值是可以很容易推出圆周率的。
    // 当然打的点越多，算出的圆周率越准确，充分体现了大力出奇迹的真理
    //arr 数组中全都是 0，只有一个 1。对 arr 进行 100 万次打乱，记录每个索引位置出现 1 的次数，
    // 如果每个索引出现 1 的次数差不多，也可以说明每种打乱结果的概率是相等的。
    // 结果 概率是 [2501034, 2499668, 2501069, 2498229] 基本每个位置为1的概率是近似相同的
    // todo 可以说明 上述打乱的方法是有效的
    private static void testRandom() {
        int x = 100000000;
        Integer[] array = new Integer[]{0, 0, 0, 1};
        int[] count = new int[array.length];
        for (int i = 0; i < x; i++) {
            random(array);
            for (int j = 0; j < array.length; j++) {
                if (array[j] == 1) {
                    count[j]++;
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(count));
    }

    public static void main(String[] args) {
        int x = 9999;
        Integer[] data = new Integer[x];
        for (int i = 0; i < x; i++) {
            data[i] = i;
        }
        random(data);
        System.out.println(Arrays.toString(data));
    }

    public static <T> void random(T[] array) {
        for (int i = 0; i < array.length; i++) {
            //注意randomRange方法是左闭右开，所以r=[0,length) => r=[0,length-1]
            int r = randomRange(i, array.length);
            swap(array, i, r);
        }
    }

    private static <T> void swap(T[] array, int x, int y) {
        T tmp = array[x];
        array[x] = array[y];
        array[y] = tmp;
    }

    //[0,10)   random
    private static int randomRange(int min, int max) {
        if (min >= max) return max;
        return ThreadLocalRandom.current().nextInt(min, max);
    }


}
