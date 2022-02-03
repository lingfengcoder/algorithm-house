package bizfeng.leetcode.array;

/**
 * 数组前缀技巧用于快速、频繁的计算一个索引区间内元素之和，时间复杂度可 O(n) ->O(1)
 */
public class PrefixSum {

    public static void main(String[] args) {
        int[] data = {-2, 0, 3, -5, 2, -1};
        PrefixSumOne.numArray(data);
        System.out.println(PrefixSumOne.sumRange(0, 2));
        System.out.println(PrefixSumOne.sumRange(2, 5));
        System.out.println(PrefixSumOne.sumRange(0, 5));
    }


    /**
     * ⼒扣第 303 题「区域和检索 - 数组不可变」，让你计算数组区间内元素的和，这是⼀道标准 的前缀和问题
     * <p>
     * 题目如下：
     * *************************************************
     * 303. 区域和检索 - 数组不可变
     * 给定一个整数数组  nums，处理以下类型的多个查询:
     * <p>
     * 计算索引 left 和 right （包含 left 和 right）之间的 nums 元素的 和 ，其中 left <= right
     * 实现 NumArray 类：
     * <p>
     * NumArray(int[] nums) 使用数组 nums 初始化对象
     * int sumRange(int i, int j) 返回数组 nums 中索引 left 和 right 之间的元素的 总和 ，包含 left 和 right 两点（也就是 nums[left] + nums[left + 1] + ... + nums[right] )
     * **************************************************
     */

    //一维数组
    static class PrefixSumOne {
        private static int[] idx = null;

        public static int[] numArray(int[] nums) {
            if (nums == null || nums.length == 0) return nums;
            idx = new int[nums.length + 1];
            for (int i = 1; i < idx.length; i++) {
                //计算到N的和并存放到idx中 用于获取
                idx[i] = idx[i - 1] + nums[i - 1];
            }
            return nums;
        }

        public static int sumRange(int l, int r) {
            if (r > l) {
                return idx[r + 1] - idx[l];
            } else if (l == r && l < idx.length - 1) {
                return idx[l];
            }
            return -1;
        }
    }

}
