package bizfeng.leetcode.dfs;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * ********************** 组合问题  回溯算法 【子集/组合（元素可重不可复选）】************************************
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 * <p>
 * 示例：
 * ################
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 * [1,2,1],
 * [2,1,1]]
 * <p>
 * ###############
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * <p>
 * #
 */
@Slf4j
public class DFS_6 {

    public static void main(String[] args) {
        int[] array = new int[]{1, 1, 2};
        List<List<Integer>> combine = combine(array);
        log.info("result len:{} ,result:{} ", combine.size(), combine);
    }

    private static List<List<Integer>> combine(int[] array) {
        List<List<Integer>> result = new ArrayList<>();
        //关键步骤，先将array排序，将相同的元素挨在一起
        Arrays.sort(array);
        backtracking(array, result, null, null);
        return result;
    }


    private static void backtracking(int[] array, List<List<Integer>> result, LinkedList<Integer> list, int[] used) {
        if (list == null) {
            list = new LinkedList<>();
        }
        if (used == null) {
            used = new int[array.length];
        }
        if (list.size() == array.length) {
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < array.length; i++) {
            if (used[i] == 1) {
                continue;
            }
            //在前后两个元素相同的时候，前一个没用过，说明开始进行回溯了，就会产生重复
            if (i > 0 && array[i] == array[i - 1] && used[i - 1] == 0) {
                continue;
            }
            list.add(array[i]);
            used[i] = 1;
            backtracking(array, result, list, used);
            list.removeLast();
            used[i] = 0;
        }
    }

}
