package bizfeng.leetcode.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 图论 -> 二分图判定
 * 给定一组n人（编号为1, 2, ..., n），我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
 * <p>
 * 给定整数 n和数组 dislikes，其中dislikes[i] = [ai, bi]，表示不允许将编号为 ai和bi的人归入同一组。
 * 当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
 *
 * <p>
 * 示例：
 * ###################################################
 * 输入：n = 4, dislikes = [[1,2],[1,3],[2,4]]
 * 输出：true
 * 解释：group1 [1,4], group2 [2,3]
 * ###################################################
 * 输入：n = 3, dislikes = [[1,2],[1,3],[2,3]]
 * 输出：false
 * ###################################################
 * 输入：n = 5, dislikes = [[1,2],[2,3],[3,4],[4,5],[1,5]]
 * 输出：false
 */
@Slf4j
public class Graph_03 {


    public static void main(String[] args) {
        // int[][] array = new int[][]{{1, 2}, {3}, {3}, {}};
//        int[][] array = new int[][]{{1, 2}, {1, 3}, {2, 4}};int n=4;
        int[][] array = new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}};
        int n = 5;
        // log.info(" 是否是 二分图 : {} count={}", isBipartite(array), count);
        log.info(" 是否是 可以将人群分为两组: {}  ", possibleBipartition(n, array));
        //

    }

    private static boolean possibleBipartition(int n, int[][] dislikes) {
        List<Integer>[] lists = tranToGraph(n, dislikes);
        for (int i = 0; i < lists.length; i++) {
            if (!isBinaryGraph_BFS(lists, i)) return false;
        }
        return true;
    }

    private static List<Integer>[] tranToGraph(int n, int[][] arr) {
        // int[][]graph=new int[][];
        List<Integer>[] result = new ArrayList[n + 1];
        //初始化
        for (int i = 0; i < n + 1; i++) {
            result[i] = new ArrayList<>(0);
        }
        //[[1,2],[1,3],[2,4]]
        for (int[] tmp : arr) {
            int i = tmp[0];
            int j = tmp[1];
            result[i].add(j);
            result[j].add(i);
        }
        return result;
    }

    private static boolean isBinaryGraph_BFS(List<Integer>[] graph, int start) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.length];
        boolean[] color = new boolean[graph.length];
        queue.add(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            for (int i : graph[cur]) {
                if (!visited[i]) {
                    visited[i] = true;
                    color[i] = !color[cur];
                    queue.add(i);
                } else {
                    if (color[i] == color[cur]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
