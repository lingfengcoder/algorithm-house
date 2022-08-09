package bizfeng.leetcode.dynamic;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Auther: wz
 * @Date: 2022/8/2 21:18
 * @Description: 给两个单词 word1和word2，请计算出将word1转换成word2所使用的最少操作数，
 * 可以对一个单词进行如下三次操作：
 * 1.插入一个字符
 * 2.删除一个字符
 * 3.替换一个字符
 * #######################
 * 示例：
 * 输入：word1=horse; word2=ros
 * 输出：3
 * 解释：
 * horse =>rorse (h->r)
 * rorse => rose (del r)
 * rose => ros (del e)
 * <p>
 * <p>
 * 思路：
 * 0.求最值、有重叠子问题
 * 1.每步只能操作一个字符
 * <p>
 * 1.遇到两个字符串替换的问题，首先想到 用两个指针 i,j 进行游走对比。
 * 2.
 */
@Slf4j
public class Dynamic_3 {


    public static void main(String[] args) {
        log.info("findCoins1 11 {}", minStep1(new char[]{'a', 'b', 'c'}, new char[]{'k', 'a', 'f', 'a', 'c'}));
    }

    private static int minStep1(char[] s1, char[] s2) {
        return minStep1(s1.length - 1, s2.length - 1, s1, s2);
    }

    private static int minStep1(int i, int j, char[] s1, char[] s2) {
        if (i == -1) return j + 1;
        if (j == -1) return i + 1;
        //如果两个字符相同则继续向前迭代
        if (s1[i] == s2[j]) {
            return minStep1(i - 1, j - 1, s1, s2);
        } else return min(
                minStep1(i - 1, j - 1, s1, s2) + 1, //修改
                minStep1(i, j - 1, s1, s2) + 1,//新增
                minStep1(i - 1, j, s1, s2) + 1//删除

        );
    }

    //字符串始终没有变化，变化的只有指针的位置 i,j
    //dp[0][0]=0
    private static int minStep(int i, int j, char[][] dp, char[] s1, char[] s2) {

        if (i == -1) {
            //如果S1遍历完毕，但是S2还没有遍历完毕，说明还需要新增 j++个字符，也就意味着还要j++步操作
            return j + 1;
        }
        if (j == -1) {
            //如果S2遍历完毕，但是S1还没有遍历完毕，说明还需要将S1剩余的 i++个字符删除，也就需要i++步操作
            return i + 1;
        }


        //dp 数组的定义，从s[i] 转换到s[j]所需要的最小步数,将每一个字母变换的最小步数相加就是总的的最小方案
        //dp 要记录S1每个字母做 所有转换[cud]的所有步骤，形成一个多叉树,找到多叉树最短的那条，就是最少步骤
        //比如 word1=horse; word2=ros
        // 给e做替换[hors e => ros] horss => hor s => horos => h ros => ros ==> 最少5步
        //实际上每一步都会有其他【c u d选择的】分支，其他步骤更为复杂
        // 给e做删除[hors e => ros] hors => horss
        if (dp[i][j] > 0) {
            return dp[i][j];
        }
        int minStep = Integer.MAX_VALUE;
        for (int k = i; k >= 0; k--) {
            for (int m = j; m >= 0; m--) {
                //从 S1[i]变换到 S2[j]需要多少步骤
//                dp[k][m] = min(
//                       // dp[k][m - 1],//新增
//                        //删除
//                        //替换
//                );
            }
        }
        return dp[i][j];
    }

    //从三个数中选出最小数
    private static int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }


}
