package bizfeng.leetcode.dynamic;

import lombok.extern.slf4j.Slf4j;


/**
 * @Auther: wz
 * @Date: 2022/8/2 21:18
 * 0-1背包问题
 * 给一个可以装载容量为 W 的背包和 N个物品，每个物品有重量属性。
 * 其中第 i 个物品的重量为 wt[i] ,现在问能否选出数个物品放入背包，使背包正好填满(背包内物品的重量和等于背包的容量W)
 */
@Slf4j
public class Dynamic_5 {


    public static void main(String[] args) {
        int[] val = new int[]{1, 5, 11, 5};
        log.info("能否正好装满：{}", dynamicSplit(val, 4));
    }

    private static boolean dynamicSplit(int[] wt, int cap) {
        //定义dp数组
        boolean[][] dp = new boolean[wt.length + 1][cap + 1];
        //base case 当cap容量为0时 任意的重量都可以正好填满
        for (int i = 0; i <= wt.length; i++) {
            dp[i][0] = true;
        }
        int n = wt.length;
        for (int i = 1; i <= n; i++) {
            for (int w = cap; w > 0; w--) {
                //背包如果放不下
                if (w < wt[i - 1]) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    //选择放入或者不放入
                    dp[i][w] = dp[i - 1][w] || dp[i - 1][w - wt[i - 1]];
                    //dp[i - 1][w - wt[i - 1]] 的解读
                    //如果 在i-1个物品且 重量为w-wt[i-1]时 重量正好填满
                    //那么 在放入重量为wt[i-1]的物品i时，应该也可以正好填满，
                    //所以，当放入第i个物品时，是否可以正好填满背包，取决于第i-1个物品在背包重量为 w-wt[i-1]时的情况
                    // ||的情况，如果i-1不满足，那么就不放入，如果不放入也不满足，那结果就是不满足的
                }
            }
        }
        return dp[n][cap];
    }


}
