package bizfeng.leetcode.point;

import lombok.extern.slf4j.Slf4j;

/**
 * 【快慢指针】
 * <p>
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
 * <p>
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 * <p>
 * ######################################################################
 * 输入：nums = [3,2,2,3], val = 3
 * 输出：2, nums = [2,2]
 * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。你不需要考虑数组中超出新长度后面的元素。
 * 例如，函数返回的新长度为 2 ，而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
 * ######################################################################
 * <p>
 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
 * 输出：5, nums = [0,1,4,0,3]
 * 解释：函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
 * 注意这五个元素可为任意顺序。你不需要考虑数组中超出新长度后面的元素。
 */
@Slf4j
public class Point_2 {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        //   int[] nums = new int[]{1, 2,3};
        int i = removeDuplicates(nums, 2);
        log.info("{}", i);
    }


    //[0,1,2,2,3,0,4,2], val = 2
    private static int removeDuplicates(int[] nums, int target) {
        if (nums.length == 0) return 0;
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            //慢指针跳过相同的，不同的进行移动
            if (nums[fast] != target) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        log.info("{}", nums);
        //数组长度为索引+1
        return slow;
    }
}
