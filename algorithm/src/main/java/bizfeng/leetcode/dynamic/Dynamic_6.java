package bizfeng.leetcode.dynamic;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: wz
 * @Date: 2022/8/2 21:18
 * 给一个只包含正整数的非空数组，判断是否可以将这个数组分割成两个子数组，且两个子数组之和相等。
 *
     * 示例：
 *
     * 输⼊：nums = [1,5,11,5]
     * 输出：true
     * 解释：数组可以分割成 [1, 5, 5] 和 [11]
 * <p>
 * 思路:
 * <p>
 * 将两个这个问题转换为1-0背包问题
 * 既然是两个和相等的数组。n1=n2; n1+n2=n;可以推导出 n1=n2=n/2;
 * 所以可以先求出原数组的和SUM，将背包承重设置为sum/2,每个元素代表物品的重量，
 * 问：能否找出M个元素正好放入背包，（当然可能有好几种方案），然后此时在看剩余的元素之和能否等于sum/2，即可获得题目的答案
 */
@Slf4j
public class Dynamic_6 {


    public static void main(String[] args) {
        int[] val = new int[]{1, 5, 11, 5};
        log.info("最大价值：{}", dynamicSplit(val));
    }

    private static boolean dynamicSplit(int[] data) {
        int sum = 0;
        for (int i : data) {
            sum += i;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int n = data.length;
        int cap = sum / 2;
        //数组dp的定义dp[i][j]代表 在物品个数为i，背包容量为j时，是否能证号装下 subArray.sum == sum
        boolean dp[][] = new boolean[data.length + 1][cap + 1];
        //状态：放入了N个物品
        //base case
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        //选择：放入或者不放入
        for (int i = 1; i <= data.length; i++) {
            //当容量为W时，物品分别放入（绘制二维表格）
            for (int w = 1; w <= cap; w++) {
                //如果放不下
                if (w < data[i - 1]) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    //装入背包 或者不装
                    dp[i][w] = dp[i - 1][w - data[i - 1]] || dp[i - 1][w];
                }
            }
        }
        return dp[data.length][cap];
    }

    //把 数组 按照
    //N位 全组合 回溯算法
    //
    private static int[] put(int[] data, int[] res, int resEnd, int begin, int sum) {
        //数组装满了
        if (resEnd == res.length) {
            int arraySum = sumArray(res);
            //如果找到的组合,数组之和等于sum
            if (arraySum == sum) {
                return res;
            }
        }

        for (int i = begin; i < data.length; i++) {
            res[resEnd] = data[i];
            resEnd++;

            int[] tmpArray = put(data, res, resEnd, begin++, sum);
            if (tmpArray != null) {
                return tmpArray;
            }
        }
        return null;
    }

    private static int sumArray(int[] array) {
        int sum = 0;
        for (int i : array) {
            sum += i;
        }
        return sum;
    }

}
