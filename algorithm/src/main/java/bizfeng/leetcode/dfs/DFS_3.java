package bizfeng.leetcode.dfs;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * ********************** 组合问题  回溯算法 【元素不重复，不可重复选择，子集不可重复】************************************
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * List<List<Integer>> combine(int n, int k)
 * <p>
 * #####################################
 * 例子：
 * 比如 combine(3, 2) 的返回值应该是：
 * [ [1,2],[1,3],[2,3] ]
 * <p>
 * #
 */
@Slf4j
public class DFS_3 {

    public static void main(String[] args) {
        List<List<Integer>> combine = combine(4, 3);
        log.info("result len:{} ,result:{} ", combine.size(), combine);
    }

    private static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtracking(n, result, null, k, 0);
        return result;
    }

    private static void backtracking(int arrayLen, List<List<Integer>> result, LinkedList<Integer> item, int maxCount, int start) {
        if (item == null) {
            item = new LinkedList<>();
        }
        if (item.size() == maxCount) {
            //结果添加进去
            result.add(new ArrayList<>(item));
            return;
        }
        for (int i = start; i < arrayLen; i++) {
            item.addLast(i+1);
            backtracking(arrayLen, result, item, maxCount, i + 1);
            item.removeLast();
        }
    }

}
