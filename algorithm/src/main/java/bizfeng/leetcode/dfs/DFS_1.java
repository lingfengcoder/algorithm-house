package bizfeng.leetcode.dfs;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 注意：DFS递归函数一般都是void没有返回参数。
 * #
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 * 示例 1：
 * #
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * 示例 2：
 * #
 * 输入：nums = [0,1]
 * 输出：[[0,1],[1,0]]
 * 示例 3：
 * #
 * 输入：nums = [1]
 * 输出：[[1]]
 * 提示：
 * #
 * 1 <= nums.length <= 6
 * -10 <= nums[i] <= 10
 * nums 中的所有整数 互不相同
 */
@Slf4j
public class DFS_1 {

    public static void main(String[] args) {

        List<List<Integer>> result = new ArrayList<>();
        algorithm(new int[]{1, 2, 3, 4}, result);
        log.info("size={}, {}", result.size(), result);
    }

    //返回数组的全排列
    private static int[][] fullSort(int[] array) {

        return null;
    }

    // [1,2,3] => [1,2,3] [1,3,2] [2,1,3] [2,3,1] [3,1,2] [3,2,1]
    private static List<Integer> algorithm(int[] array, List<List<Integer>> result) {
        LinkedList<Integer> routeLink = new LinkedList<>();

        // Integer[] tmp = Arrays.stream(array).boxed().toArray(Integer[]::new);
        // backtrack2(tmp, routeLink, result);

        int[] used = new int[array.length];
        backtrack3(array, routeLink, result, used);
        return null;
    }

    private static void backtrack(Integer[] array, LinkedList<Integer> track, List<List<Integer>> result) {
        //结束条件
        if (track.size() == array.length) {
            result.add(new ArrayList<>(track));
            return;
        }
        for (Integer item : array) {
            //排除不合法的数据
            if (track.contains(item)) {
                continue;
            }
            //做选择
            track.add(item);
            //进入下一层决策树
            backtrack(array, track, result);
            //取消选择;
            track.removeLast();
        }
    }


    //将本层的数据设置成null 这样就不必在结果集中搜索，直接跳过null，最后再将数据还原
    private static void backtrack2(Integer[] array, LinkedList<Integer> track, List<List<Integer>> result) {
        //结束条件
        if (track.size() == array.length) {
            result.add(new ArrayList<>(track));
            return;
        }
        for (int i = 0; i < array.length; i++) {
            Integer item = array[i];
            //排除不合法的数据
            if (item == null) {
                continue;
            }
            array[i] = null;
            //做选择
            track.add(item);
            //进入下一层决策树
            backtrack2(array, track, result);
            array[i] = item;
            //取消选择;
            track.removeLast();
        }
    }

    //used数组 和track 都是一种回溯的体现，

    private static void backtrack3(int[] array, LinkedList<Integer> track, List<List<Integer>> result, int[] used) {
        //结束条件
        if (track.size() == array.length) {
            result.add(new ArrayList<>(track));
            return;
        }
        for (int i = 0; i < array.length; i++) {
            Integer item = array[i];
            //排除不合法的数据
            if (used[i] == 1) {
                continue;
            }
            used[i] = 1;
            //做选择
            track.add(item);
            //进入下一层决策树
            backtrack3(array, track, result, used);
            used[i] = 0;
            //取消选择;
            track.removeLast();
        }
    }
}
