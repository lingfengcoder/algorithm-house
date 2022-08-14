package bizfeng.leetcode.dfs;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * ********************** 组合问题  回溯算法 【元素可重复，不可重复选，子集不可重复】************************************
 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的子集（幂集）。
 * <p>
 * 解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
 *
 * <p>
 * #####################################
 * 输入：nums = [1,2,2]
 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
 * <p>
 * #########################################
 * 输入：nums = [0]
 * 输出：[[],[0]]
 * <p>
 * #
 */
@Slf4j
public class DFS_4 {

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 2,3};
        List<List<Integer>> combine = combine(array);
        log.info("result len:{} ,result:{} ", combine.size(), combine);
    }

    private static List<List<Integer>> combine(int[] array) {
        List<List<Integer>> result = new ArrayList<>();
        //关键步骤，先将array排序，将相同的元素挨在一起
        Arrays.sort(array);
        backtracking(array, result, null, null, 0);
        return result;
    }


    private static void backtracking(int[] array, List<List<Integer>> result, int[] usedIndex, LinkedList<Integer> itemArray, int start) {
        if (itemArray == null) {
            itemArray = new LinkedList<>();
        }
        result.add(new ArrayList<>(itemArray));
        for (int i = start; i < array.length; i++) {
            //如果当前元素和前一个元素相同，则不再参与,
            //i>start  表示，array[i]元素可以参与，但是与 大于i的不行，
            //这样就保证了相同元素至少参与一次
            if (i > start && array[i] == array[i - 1]) {
                continue;
            }
            itemArray.addLast(array[i]);
            backtracking(array, result, usedIndex, itemArray, i + 1);
            itemArray.removeLast();
        }
    }

}
