package bizfeng.leetcode.dfs;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;


/**
 * ********************** 组合问题  回溯算法 【子集/组合（元素可重不可复选）】************************************
 * 给你一个 无重复元素 的整数数组  candidates 和一个目标整数  target ，找出  candidates  中可以使数字和为目标数  target 的 所有  不同组合 ，
 * 并以列表形式返回。你可以按 任意顺序 返回这些组合。
 * <p>
 * candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 * <p>
 * 对于给定的输入，保证和 为  target 的不同组合数 少于 150 个。
 * <p>
 * ###################################################
 * 示例：
 * 输入：candidates = [2,3,6,7], target = 7
 * 输出：[[2,2,3],[7]]
 * 解释：
 * 2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
 * 7 也是一个候选， 7 = 7 。
 * 仅有这两种组合。
 * #################################################
 * 输入: candidates = [2,3,5], target = 8
 * 输出: [[2,2,2,2],[2,3,3],[3,5]]
 * <p>
 * ################################################
 * 输入: candidates = [2], target = 1
 * 输出: []
 * <p>
 * #
 */
@Slf4j
public class DFS_7 {

    public static void main(String[] args) {
        int[] array = new int[]{100, 200, 4, 12};//400
        // int[] array = new int[]{2, 3, 5};
        //{100,200,4,12} target=400
      //   List<List<Integer>> combine = combine02(array, 400);
       List<List<Integer>> combine = combine01(array, 400);
        // List<List<Integer>> combine = combinationSum(array, 400);
        log.info("result len:{} ,result:{} ", combine.size());
    }


    //==========================================================方式一==============================================================================
    private static List<List<Integer>> combine01(int[] array, int target) {

        //关键步骤，先将array排序，将相同的元素挨在一起
        //  Arrays.sort(array);
        List<List<Integer>> result = new ArrayList<>();

        dfs_algorithm01(array, 0, target, new LinkedList<>(), result);
        // result = bfs_algorithm(array, target);
        return result;
    }

    private static void dfs_algorithm01(int[] array, int start, int target, LinkedList<Integer> path, List<List<Integer>> result) {

        if (target < 0) {
            return;
        }
        if (target == 0) {
            result.add(new ArrayList<>(path));
            log.info("result size: {}", result.size());
            log.info("item list: {}", path);
        }
        for (int i = start; i < array.length; i++) {
            if (array[i] <= target) {
                path.add(array[i]);
                dfs_algorithm01(array, i, target - array[i], path, result);
                path.removeLast();
            }
        }
    }


    //===========================================方式二==========================================================================================


    private static List<List<Integer>> combine02(int[] array, int target) {

        //关键步骤，先将array排序，将相同的元素挨在一起
       // Arrays.sort(array);
        List<List<Integer>> result = new ArrayList<>();
        dfs_algorithm02(array, target, result, null, 0);
        // result = bfs_algorithm(array, target);
        return result;
    }


    private static void dfs_algorithm02(int[] array, int target, List<List<Integer>> result, LinkedList<Integer> list, int start) {
        if (list == null) {
            list = new LinkedList<>();
        }
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        if (sum == target) {
            ArrayList<Integer> tmp = new ArrayList<>(list);
            // Collections.sort(tmp);
            result.add(tmp);
            log.info("result size:{}", result.size());
            log.info("item:{}", list);
            return;
        } else if (sum > target) {
            return;
        }
        for (int i = start; i < array.length; i++) {
            list.add(array[i]);
            dfs_algorithm02(array, target, result, list, i);
            list.removeLast();
            //list 排序
        }
    }


    // bfs 的解法 ，但是容易超时  {100,200,4,12} target=400
    private static List<List<Integer>> bfs_algorithm(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Queue<List<Integer>> queue = new LinkedList<>();

        for (int i : array) {
            List<Integer> tmp = new ArrayList<>();
            tmp.add(i);
            queue.add(tmp);
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            log.info("queue size:{}", size);
            for (int i = 0; i < size; i++) {
                List<Integer> poll = queue.poll();
                int sum = poll.stream().mapToInt(Integer::intValue).sum();
                //终止条件
                if (sum == target) {
                    //Collections.sort(poll);
                    if (!result.contains(poll)) {
                        log.info("poll:{}", poll);
                        log.info("result size:{}", result.size());
                        result.add(poll);
                    }
                    continue;
                } else if (sum > target) {
                    poll.clear();
                    poll = null;
                    //剪枝，大于目标的选择 没必要继续做选择了
                    continue;
                }
                //下一层的选择
                for (int j = 0; j < array.length; j++) {
                    //新的选择
                    ArrayList<Integer> tmpItem = new ArrayList<>(poll);
                    tmpItem.add(array[j]);
                    int sum2 = poll.stream().mapToInt(Integer::intValue).sum();
                    if (sum2 > target) {
                        tmpItem.clear();
                        tmpItem = null;
                        //因为array是排好序的，所以如果当前的元素之和已经超出目标了，后面的肯定也超出了，直接停止循环
                        break;
                    }
                    queue.add(tmpItem);
                }
            }
        }
        return result;
    }


}
