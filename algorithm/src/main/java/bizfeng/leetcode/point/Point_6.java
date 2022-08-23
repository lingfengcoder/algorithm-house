package bizfeng.leetcode.point;

import lombok.extern.slf4j.Slf4j;

/**
 * 【快慢指针】-最长回文子串
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * <p>
 * #####################################
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * #####################################
 * 输入：s = "cbbd"
 * 输出："bb"
 */
@Slf4j
public class Point_6 {

    public static void main(String[] args) {
        char[] nums = new char[]{'h', 'e', 'l', 'l', 'o'};
        //   int[] nums = new int[]{1, 2,3};
        String str = "aba";
        // log.info("{}", chargePalindrome(str));
        log.info("{}", palindrome(str, 1, 1));
        // log.info("{}", longestPalindrome(str));

    }

    //如果l=r那就是找奇数序列的回文
    //如果l =r-1 那就是找偶数序列的回文
    private static String palindrome(String str, int l, int r) {
        while (l >= 0 && r < str.length() && str.charAt(l) == str.charAt(r)) {
            //双指针向两边开来
            --l;
            ++r;
        }
        // --l 是不满足的，所以要+1 ;sub 左闭右开，截取 l+1 到 r-1 的长度
        //abvba => bvb
        if (l + 1 > r) {
            return "";
        }
        return str.substring(l + 1, r);
    }

    //如果
    public static String longestPalindrome(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            String s1 = palindrome(str, i, i);
            String s2 = palindrome(str, i, i + 1);
            result = result.length() > s1.length() ? result : s1;
            result = result.length() > s2.length() ? result : s2;
        }
        return result;
    }

    //回文
    // abba
    //abbac
    //abvba

    private static boolean chargePalindrome(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        int left = 0, right = str.length() - 1;
        //str为奇数时 最后left=right 也可判定是回文
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            ++left;
            --right;
        }
        return true;
    }


}
