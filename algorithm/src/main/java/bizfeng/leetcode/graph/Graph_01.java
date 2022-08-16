package bizfeng.leetcode.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 图论
 * 给你一个有  n  个节点的 有向无环图（DAG），请你找出所有从节点 0  到节点 n-1  的路径并输出（不要求按特定顺序）
 * <p>
 * graph[i]  是一个从节点 i 可以访问的所有节点的列表（即从节点 i 到节点  graph[i][j]存在一条有向边）。
 * <p>
 * #####################################
 * 例子：
 * <p>
 * 输入：graph = [[1,2],[3],[3],[]]
 * 输出：[[0,1,3],[0,2,3]]
 * 解释：有两条路径 0 -> 1 -> 3 和 0 -> 2 -> 3
 * <p>
 * ######################################
 * #    0 -> 1
 * #    |    |
 * ##  \/   \/
 * #    2 -> 3
 */
@Slf4j
public class Graph_01 {


    public static void main(String[] args) {
        int[][] array = new int[][]{{1, 2}, {3}, {3}, {}};
        log.info("result {} ", allPathsSourceTarget(array));
    }

    public static List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result = new ArrayList<>();
        allPathsSourceTarget(graph, 0, result, new LinkedList<>());
        return result;
    }

    public static void allPathsSourceTarget(int[][] graph, int cur, List<List<Integer>> result, LinkedList<Integer> path) {
        path.add(cur);
        if (cur == graph.length - 1) {
            result.add(new LinkedList<>(path));
            //最后一个节点要 回归退出删除
            path.removeLast();
            return;
        }
        for (int i : graph[cur]) {
            allPathsSourceTarget(graph, i, result, path);
        }
        //每层递归的 回归退出删除
        path.removeLast();
    }

}
