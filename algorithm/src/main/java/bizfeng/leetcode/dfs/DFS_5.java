package bizfeng.leetcode.dfs;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * ********************** 组合问题  回溯算法 【子集/组合（元素可重不可复选）】************************************
 * 给定一个候选人编号的集合 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * <p>
 * candidates 中的每个数字在每个组合中只能使用 一次 。
 * <p>
 * 注意：解集不能包含重复的组合。
 * ###############################
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 输出:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 * #####################################
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 输出:
 * [
 * [1,2,2],
 * [5]
 * ]
 *
 * <p>
 * #
 */
@Slf4j
public class DFS_5 {

    public static void main(String[] args) {
        int[] array = new int[]{2, 5, 2, 1, 2};
        List<List<Integer>> combine = combine(array, 5);
        log.info("result len:{} ,result:{} ", combine.size(), combine);
    }

    private static List<List<Integer>> combine(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        //关键步骤，先将array排序，将相同的元素挨在一起
        Arrays.sort(array);
        backtracking(array, target, result, null, 0);
        return result;
    }


    private static void backtracking(int[] array, int target, List<List<Integer>> result, LinkedList<Integer> itemArray, int start) {
        if (itemArray == null) {
            itemArray = new LinkedList<>();
        }
        int sum = itemArray.stream().mapToInt(Integer::intValue).sum();
        if (sum == target) {
            result.add(new ArrayList<>(itemArray));
        } else if (sum > target) {
            //超过的就不用再次计算了
            return;
        }
        for (int i = start; i < array.length; i++) {
            if (i > start && array[i] == array[i - 1]) {
                continue;
            }
            itemArray.addLast(array[i]);
            backtracking(array, target, result, itemArray, i + 1);
            itemArray.removeLast();
        }
    }

}
