package bizfeng.leetcode.dynamic;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Auther: wz
 * @Date: 2022/8/2 21:18
 * @Description: 斐波那契数列
 */
@Slf4j
public class Dynamic_1 {


    public static void main(String[] args) {
        log.info("F4 ({}) = {}", 1, dp4(1));
        log.info("F4 ({}) = {}", 2, dp4(2));
        log.info("F4 ({}) = {}", 3, dp4(3));
        log.info("F4 ({}) = {}", 4, dp4(4));
        log.info("F4 ({}) = {}", 10, dp3(10));
        //F(100)十分耗时
        log.info("F4 ({}) = {}", 45, dp4(45));


        log.info("F2 ({}) = {}", 3, dp2(3));
        log.info("F2 ({}) = {}", 4, dp2(4));
        log.info("F2 ({}) = {}", 10, dp2(10));
        //F(100)十分耗时
        log.info("F2 ({}) = {}", 45, dp2(45));
    }

    /**
     * @Description: 完全按照斐波那契数列的数学定义，利用程序栈的递归，进行计算
     * 缺点：当N比较大时，由于依赖栈的递归，存在甚多深度递归，效率很低
     * 时间复杂度：dp执行的次数*每次执行的时间=> 递归函数类似一颗二叉树=> 2^n *1 = O(2^n)
     * @date: 2022/8/3 20:52
     */
    //F(45)十分耗时
    private static int dp(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return dp(n - 1) + dp(n - 2);
    }

    /**
     * @Description: 备忘录法
     * 时间复杂度=>由二叉树变为链表,N个节点计算N次=> O(N)*O(1)=O(N)
     * 空间复杂度：O(N)
     * @auther: wz
     * @date: 2022/8/3 20:58
     */
    private static int dp2(int n){
        if (n == 0) return 0;
        if (n == 1) return 1;
        int[] book = new int[n + 1];
        return dp2(n,book);
    }
    private static int dp2(int n ,int[] book ) {
        if(n==0)return 0;
        if(n==1)return 1;
        int tmp = book[n];
        if (tmp == 0) {
            //存入备忘录
            book[n] = tmp = dp2(n - 2,book) + dp2(n - 1,book);
        }
        //在备忘录中找到了
        return tmp;
    }


    /**
     * @Description :自低向上的 统计，利用DP数组进行存储每个N的值
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     * @auther: wz
     * @date: 2022/8/3 21:16
     */
    private static int dp3(int n){
        if(n==0)return 0;
        int dp[]=new int[n+1];
        dp[0]=0;
        dp[1]=1;
        for (int i=2;i<=n;i++){
           dp[i]=dp[i-1]+dp[i-2];
        }
        return dp[n];
    }

    /**
     * @Description:进阶版，通过一个长度为3的数组，不停的交换前两数的位置来达到求和的目的
     * 时间复杂度:O(n)
     * 空间复杂度:O(1)
     * @auther: wz
     * @date: 2022/8/3 21:25
     */
    private static int dp4(int n){
        if(n==0)return 0;
        if(n==1)return 1;
        int dp[]=new int[3];
        dp[0]=0;
        dp[1]=1;
        for (int i=0;i<=n-2;i++){
            dp[2]=dp[0]+dp[1];
            dp[0]=dp[1];
            dp[1]=dp[2];
        }
        return dp[2];
    }
}
