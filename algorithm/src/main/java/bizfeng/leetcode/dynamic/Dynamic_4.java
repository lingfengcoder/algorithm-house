package bizfeng.leetcode.dynamic;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: wz
 * @Date: 2022/8/2 21:18
 * 0-1背包问题
 * 给一个可以装载容量为 W 的背包和 N个物品，每个物品有重量和价值两个属性。
 * 其中第 i 个物品的重量为 wt[i]，价值为 val[i] ，现在使用这个背包装物品，最多能装的价值是多少？
 * int knapsck(int w, int n,int[] wt,int[]val);
 * #########################################################################
 * 例子：
 * N=3,W=4;
 * wt=[2,1,3]
 * val=[4,2,3]
 * 结果为 6  wt=2,1  val=4,2 重量=2+1=3 < W=4  价值=4+2=6
 * ########################################################################
 * 分析如下
 * <p>
 * 首先明确状态和选择
 * <p>
 * 状态：
 * 1.背包的空余容量会变化
 * 2.可选的剩余物品会变化
 * <p>
 * 选择：
 * 1.将物品放入背包
 * 2.物品不放入背包
 * <p>
 * dp[i][w] 因为是有两个变量 价值和重量，所以dp数组就是一个二维数组
 * <p>
 * 其实dp[][]存放了所有可能 每个容量下 可以存放的最大价值
 * <p>
 * 容量 6 5 4 3
 * 个数
 * 1    = = = =
 * 2    = = = =
 * 3    = = = =
 * <p>
 * = 代表价值
 */
@Slf4j
public class Dynamic_4 {


    public static void main(String[] args) {
        int wcap = 4, num = 3;
        int[] wt = new int[]{2, 1, 3};
        int[] val = new int[]{4, 2, 3};
        log.info("最大价值：{}", knapsck(wcap, num, wt, val));
    }

    //自底向上的
    private static int knapsck(int wcap, int n, int[] wt, int[] val) {
        //有n个物品时 且 背包容量为 wcap 时 的最大价值
        int[][] dp = new int[n + 1][wcap + 1];
        //因为i=0 和 w=0在数组dp初始化时已经置零了
        //从第一个物品开始，i=1 wcap=1 代表，当有一个具体 重量wt[1] 价值为val[1] 的物品时，容量为w=1的容器，可以存放的最大价值
        //当 i=2代表，当有一个具体 重量为wt[2] 价值为val[2]的物品时，容量为w=1的容器 ，可以存放的最大价值
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= wcap; w++) {
                //如果当前的容量不足以放下当前的物品时 ，当前的容量可以存放的最大价值就是 上一个物品放入时的价值
                if(w<wt[i-1]){
                    //上一个物品 在w的重量下的最大价值，因为当前物品没有放入，所以背包容量没变化，故此，
                     dp[i][w]=dp[i-1][w];
                    log.info("dp[{}][{}]={}",i,w,dp[i][w]);
                 }else{
                    //选择放入或者不放入
                     dp[i][w]=Math.max(
                             dp[i-1][w],//不放入
                             val[i-1]+dp[i-1][w-wt[i-1]]//放入背包，
                            //val[i-1] 代表 物品i的价值 wt[i-1]代表物品i的重量，
                             // 因为 w>wt[i-1] 所以 w-wt[i-1]>0; dp[i-1][w-wt[i-1]] 代表在 不放入i物品 前 重量为 w-wt[i-1] 的最大价值
                     );
                     log.info("dp[{}][{}]={}",i,w,dp[i][w]);
                 }
            }
        }
        return dp[n][wcap];
    }
}
