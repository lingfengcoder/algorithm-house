package bizfeng.leetcode.slidingwindow;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * 从滑动窗口中寻找最大值
 *
 * <p>
 * 给你⼀个整数数组 nums，有⼀个⼤⼩为 k 的滑动窗⼝从数组的最左侧移动到数组的最右侧，返回滑动窗⼝ 中的最⼤值。
 * 滑动窗⼝每次只向右移动⼀位，你只可以看到在滑动窗⼝内的 k 个数字。
 * <p>
 * <p>
 * 输⼊：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 滑动窗⼝的位置        最⼤值
 * ---------------        -----
 * [1 3 -1] -3 5 3 6 7    3
 * 1 [3 -1 -3] 5 3 6 7    3
 * 1 3 [-1 -3 5] 3 6 7    5
 * 1 3 -1 [-3 5 3] 6 7    5
 * 1 3 -1 -3 [5 3 6] 7    6
 * 1 3 -1 -3 5 [3 6 7]    7
 * <p>
 * <p>
 * 解题思路：
 * 1.滑动窗口的实现
 * 2.从无序数组中找到最大值 要求时间复杂度最低
 */
@Slf4j
public class FindMaxFromWindow {


    public static void main(String[] args) {
        int x = 20;
        int windowSize = 3;
        Integer[] data = new Integer[20];
        for (int i = 0; i < x; i++) {
            data[i] = i;
        }
        data = new Integer[]{1, 3, -1, -3, 5, 3, 6, 7};

        Integer[] result = slidingWindow(data, windowSize);
        System.out.println(Arrays.toString(result));
    }


    /**
     * 单一变量控制法：使用一个tmpMax临时变量放每次窗口滑动的最大值
     *
     * @param data
     * @param windowSize
     * @return
     */
    private static Integer[] slidingWindow(Integer[] data, int windowSize) {
        LinkedList<Integer> window = new LinkedList<>();
        //结果
        Integer[] result = new Integer[data.length - windowSize + 1];
        int count = 0;
        int tmpMax = data[0];
        for (int x = 0; x < data.length; x++) {
            //窗口扩大一个
            window.offer(data[x]);
            //窗口填充完毕开始 统计结果
            if (x >= windowSize) {
                count++;
            }
            //找出最大值
            if (data[x] > tmpMax) {
                tmpMax = data[x];
            }
            //设置每次窗口滑动的最大值
            result[count] = tmpMax;
            //进行窗口缩小一个
            if (window.size() > windowSize) {
                window.pop();
            }
            log.info(window.toString());
        }
        return result;
    }


}
