package bizfeng.leetcode.dynamic;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Auther: wz
 * @Date: 2022/8/2 21:18
 * @Description: 零钱兑换
 * 题目：给定不同面值的硬币 coins 和一个总金额，
 * 编写一个函数来计算可以凑成总金额所需要的最少硬币个数，如果没有任何一种金币组合能组成总金额，返回-1。
 * ##############################
 * 输入: coins=[1,2,5]，amout=11
 * 输出：3
 * 解析： 11=5+5+1
 * ##############################
 * 输入：coins=[2], amount=3
 * 输出：-1
 */
@Slf4j
public class Dynamic_2 {


    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        // log.info("findCoins1 11 {}", findCoins1(coins, 50));

        log.info("findCoins2 14 {}", findCoins2(coins, 2));
        log.info("findCoins2 50 {}", findCoins2(coins, 3));
        log.info("=========================================================");
        log.info("findCoins3 14 {}", findCoins3(coins, 2));
        log.info("findCoins3 50 {}", findCoins3(coins, 3));
        log.info("=========================================================");

        log.info("findCoins4 14 {}", findCoins4(coins, 2));
        log.info("findCoins4 50 {}", findCoins4(coins, 3));
    }


    /**
     * @Description：暴力穷举法
     * 时间复杂度：O(K)
     * @auther: wz
     * @date: 2022/8/4 18:19
     */
    private static int findCoins1(int coins[], int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subRes = findCoins1(coins, amount - coin);
            // 子问题 无解就跳过：当amount < coin时，不选择当前的coin，继续选择其他的coin
            if (subRes < 0) continue;
            //存储一下父问题当前的最小值
            res = Math.min(subRes + 1, res);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }


    /**
     * @Description 备忘录法
     * 时间复杂度：O(n)*O(K)=O(KN) ,k=可选择个数(coins个数) n=amount
     * @auther: wz
     * @date: 2022/8/3 22:59
     */
    private static int findCoins2(int coins[], int amount) {
        int[] book = new int[amount + 1];
        Arrays.fill(book, -999);
        return findCoins2(coins, amount, book);
    }


    private static int findCoins2(int coins[], int amount, int[] book) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        int res = Integer.MAX_VALUE;
        int subRes = book[amount];
        if (subRes != -999) {
            //从备忘录中找到答案了
            return subRes;
        }
        for (int coin : coins) {
            int subQues = amount - coin;
            //寻找子问题的解
            subRes = findCoins2(coins, subQues, book);
            //子问题有解，存储子问题
            if (subRes < 0) continue;
            res = Math.min(subRes + 1, res);
        }
        //存储 备忘录
        book[amount] = res;
        return res == Integer.MAX_VALUE ? -1 : res;
    }


    /**
     * @Description:自底向上
     * 先从0 1，2开始计算，将底层的子问题都填充，【1，2，5】来说 0元无解 F(0)=0 ；因为有一元的存在，所以其他数字都可以凑出
     * 时间复杂度：有K个金币，凑到金额为N，O(N)*O(K)=O(KN)
     * 空间复杂度：O(N+1)=O(N)
     * @auther: wz
     * @date: 2022/8/3 23:01
     */
    private static int findCoins3(int[] coins, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        int out_max=amount+1;
        int[] dp = new int[out_max];
        //dp数组的定义 凑出amount 至少需要 dp[amount]枚硬币
        //凑出amount 最多需要 amount个 1元硬币 所以不可能的值是 amount+1
        Arrays.fill(dp,out_max);
        dp[0] = 0;
        //初始化 book
        int minCoins,tmp;
        for (int i = 1; i <= amount; i++) {
            //找dp i的答案
            // dp[i]=findCoins3(coins,amount-i);
            //每个 i的答案 需要初始化，用于后续的比较
            minCoins = out_max;
            //找出 金额为i 的子问题 的最小值  +1就是 i 的答案
            for (int coin : coins) {
                //如果子问题 是负数，说明没有结果
                if(i-coin<0) continue;
                tmp = dp[i - coin];
                // 如果子问题的答案是负数或者是初始化的MAX，说明子问题无解，那么 i 也无解
                if (tmp < 0 || tmp ==out_max) continue;
                minCoins = Math.min(minCoins, tmp + 1);
            }
            //如果没有找到 i的答案，那么就填入-1
            dp[i] = minCoins==out_max?-1:minCoins;
        }
        return dp[amount] > 0 ? dp[amount] : -1;
    }

    // findCoins3的改进版本
    private static int findCoins4(int[] coins,int amount){
        if(amount<0)return -1;
        if(amount==0)return 0;
        //定义dp数组，dp[i] 表示 凑出金额i需要的最小硬币个数
        int [] dp=new int[amount+1];
        Arrays.fill(dp,amount+1);
        //自底向上 将每个 子问题的结果计算出来，最终父问题也就有了答案
        dp[0]=0;
        for (int i = 1; i <= amount; i++) {
            //在可能的结果中找出子问题的最小答案
            for (int coin : coins) {
                //小于0的都是无解的，不用处理
                if(i-coin<0)continue;
                //因为上面已经将dp数组填充了 amount+1这个不可能达到的最大值，
                //dp[i-coin]表示 凑出i-coin 需要的最少金币个数，即子问题的最小值，+1表示 在子问题i-coin的基础上 在放一个coin 就是父问题的答案，
                // 所以如果子问题有答案，那么再+1 就是父问题的答案
                dp[i]=Math.min(dp[i],dp[i-coin]+1);
            }
        }
        //如果dp[amount] 还是填充值,则认为没有答案，那就返回-1
        return( dp[amount]==amount+1)?-1:dp[amount];
    }
}
