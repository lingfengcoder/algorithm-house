package bizfeng.leetcode.point;

import lombok.extern.slf4j.Slf4j;

/**
 * 【快慢指针】
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * ###############################################################
 * 输入: nums = [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * <p>
 * ##############################################################
 * 输入: nums = [0]
 * 输出: [0]
 */
@Slf4j
public class Point_3 {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 0, 3, 12};
        //   int[] nums = new int[]{1, 2,3};
//         moveZeroes(nums, 0);
        moveZeroes_2(nums, 0);
        log.info("{}", nums);
    }


    //[0,1,2,2,3,0,4,2], val = 2
    private static int moveZeroes(int[] nums, int target) {
        if (nums.length == 0) return 0;
        int slow = 0, fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != target) {
                if (nums[slow] == target) {
                    nums[slow] = nums[fast];
                    nums[fast] = target;
                }
                ++slow;
            }
            ++fast;
        }
        log.info("{}", nums);
        return slow + 1;
    }

    private static void moveZeroes_2(int[] nums, int target) {
        //将前面的target都剔除，后面的都设置成target即可
        int index = removeDuplicates(nums, target);
        for (int i = index; i < nums.length; i++) {
            nums[i] = target;
        }
    }

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
