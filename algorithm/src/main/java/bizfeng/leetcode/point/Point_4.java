package bizfeng.leetcode.point;

import lombok.extern.slf4j.Slf4j;

/**
 * 【快慢指针】-两数之和
 * <p>
 * 给你一个下标从 1 开始的整数数组  numbers ，该数组已按 非递减顺序排列   ，请你从数组中找出满足相加之和等于目标数  target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
 * <p>
 * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
 * <p>
 * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
 * <p>
 * 你所设计的解决方案必须只使用常量级的额外空间。
 * <p>
 * ##############################################
 * 输入：numbers = [2,7,11,15], target = 9
 * 输出：[1,2]
 * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
 * <p>
 * ##############################################
 * 输入：numbers = [2,3,4], target = 6
 * 输出：[1,3]
 * 解释：2 与 4 之和等于目标数 6 。因此 index1 = 1, index2 = 3 。返回 [1, 3] 。
 * <p>
 * ##############################################
 * 输入：numbers = [-1,0], target = -1
 * 输出：[1,2]
 * 解释：-1 与 0 之和等于目标数 -1 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
 */
@Slf4j
public class Point_4 {

    public static void main(String[] args) {
        int[] nums = new int[]{5,25,75};
        //   int[] nums = new int[]{1, 2,3};
        int[] ints = twoSum(nums, 100);
        log.info("{}", ints);
    }

    private static int[] twoSum(int[] nums, int target) {
        if (nums.length == 0) return new int[0];

        int left = 0, right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum > target) {
                --right;
            } else if (sum < target) {
                ++left;
            }
        }
        return new int[]{left + 1, right + 1};
    }


}
