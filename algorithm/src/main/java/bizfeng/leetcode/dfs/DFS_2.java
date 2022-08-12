package bizfeng.leetcode.dfs;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * ********************** 组合问题  回溯算法 ************************************
 * ##    给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * #
 * ##    解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 * #
 * #
 * 示例 1：
 * #
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 * 示例 2：
 * #
 * 输入：nums = [0]
 * 输出：[[],[0]]
 * #
 * #
 * 提示：
 * #
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 * nums 中的所有元素 互不相同
 * #
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/subsets
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
@Slf4j
public class DFS_2 {

    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 3};
        List<List<Integer>> result = new ArrayList<>();
        domain(data, result);
        log.info("result len:{} ,result:{} ", result.size(), result);
    }

    private static void domain(int[] array, List<List<Integer>> result) {
        backtracking(array, null, result,0);
    }

    private static void backtracking(int[] array, LinkedList<Integer> itemArray, List<List<Integer>> result, int start) {
        if (itemArray == null) {
            itemArray = new LinkedList<>();
        }
        result.add(new ArrayList<>(itemArray));
        for (int i = start; i < array.length; i++) {
            itemArray.addLast(array[i]);
            backtracking(array, itemArray, result, i + 1);
            itemArray.removeLast();
        }
    }

}
