package bizfeng.leetcode.point;

import lombok.extern.slf4j.Slf4j;

/**
 * 【快慢指针】-反转字符串
 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
 * <p>
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 * ###############################################
 * 输入：s = ["h","e","l","l","o"]
 * 输出：["o","l","l","e","h"]
 * ###############################################
 * 输入：s = ["H","a","n","n","a","h"]
 * 输出：["h","a","n","n","a","H"]
 */
@Slf4j
public class Point_5 {

    public static void main(String[] args) {
        char[] nums = new char[]{'h', 'e', 'l', 'l', 'o'};
        //   int[] nums = new int[]{1, 2,3};
        char[] c = reverse(nums);
        log.info("{}", c);
    }

    private static char[] reverse(char[] nums) {
        if (nums.length == 0) return nums;
        int left = 0, right = nums.length - 1;
        char tmp;
        while (left < right) {
            tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            ++left;
            --right;
        }
        return nums;
    }


}
