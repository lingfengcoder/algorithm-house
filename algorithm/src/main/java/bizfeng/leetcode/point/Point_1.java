package bizfeng.leetcode.point;

import lombok.extern.slf4j.Slf4j;

/**
 * [数组快慢指针] 删除有序数组中重复的项
 * <p>
 * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。
 * <p>
 * 由于在某些语言中不能改变数组的长度，所以必须将结果放在数组nums的第一部分。更规范地说，如果在删除重复项之后有 k 个元素，那么 nums 的前 k 个元素应该保存最终结果。
 * <p>
 * 将最终结果插入 nums 的前 k 个位置后返回 k 。
 * <p>
 * 不要使用额外的空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 * <p>
 * 函数签名 int removeDuplicates(int[] nums);
 * <p>
 * #############################################################################
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2,_]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 * <p>
 * ##############################################################################
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
 */
@Slf4j
public class Point_1 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7};
        //   int[] nums = new int[]{1, 2,3};
        int i = removeDuplicates(nums);
        log.info("{}", i);
    }


    private static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[slow] != nums[fast]) {
                ++slow;
                nums[slow] = nums[fast];
            }
            ++fast;
        }
        log.info("{}", nums);
        //数组长度为索引+1
        return slow + 1;
    }
}
