package bizfeng.leetcode.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 图论 -> 二分图判定
 * 存在一个 无向图 ，图中有 n 个节点。其中每个节点都有一个介于 0 到 n - 1 之间的唯一编号。
 * 给你一个二维数组 graph ，其中 graph[u] 是一个节点数组，由节点 u 的邻接节点组成。
 * 形式上，对于graph[u] 中的每个 v ，都存在一条位于节点 u 和节点 v 之间的无向边。该无向图同时具有以下属性：
 * 1.不存在自环（graph[u] 不包含 u）。
 * 2.不存在平行边（graph[u] 不包含重复值）。
 * ###
 * 如果 v 在 graph[u] 内，那么 u 也应该在 graph[v] 内（该图是无向图）
 * 这个图可能不是连通图，也就是说两个节点 u 和 v 之间可能不存在一条连通彼此的路径。
 * ###
 * 二分图 定义：如果能将一个图的节点集合分割成两个独立的子集 A 和 B ，并使图中的每一条边的两个节点一个来自 A 集合，一个来自 B 集合，就将这个图称为 二分图 。
 * <p>
 * 如果图是二分图，返回 true ；否则，返回 false 。
 * <p>
 * 示例：
 * ###################################################
 * 0 -- 1
 * | \  |
 * |  \ |
 * 3 -- 2
 * <p>
 * 输入：graph = [[1,2,3],[0,2],[0,1,3],[0,2]]
 * 输出：false
 * 解释：不能将节点分割成两个独立的子集，以使每条边都连通一个子集中的一个节点与另一个子集中的一个节点。
 * ###################################################
 * <p>
 * 0 -- 1
 * |    |
 * |    |
 * 3 -- 2
 * 输入：graph = [[1,3],[0,2],[1,3],[0,2]]
 * 输出：true
 * 解释：可以将节点分成两组: {0, 2} 和 {1, 3} 。
 */
@Slf4j
public class Graph_02 {


    public static void main(String[] args) {
        // int[][] array = new int[][]{{1, 2}, {3}, {3}, {}};
        int[][] array = new int[][]{{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}};
        // log.info(" 是否是 二分图 : {} count={}", isBipartite(array), count);
        log.info(" 是否是 二分图 : {} count={}", isBipartite(array), count);
        //

    }

    private static boolean isBipartite(int[][] graph) {
        boolean[] color = new boolean[graph.length];
        boolean[] visited = new boolean[graph.length];
        // isBinaryGraph(graph, 0, color, visited);
        //因为 可能存在 不连接的图，所以要将每个节点都作为起始节点进行访问，
        //同时因为visited数组 存在，就不会重复访问已经访问过的节点
        for (int i = 0; i < graph.length; i++) {
            // flag = isBinaryGraph2(graph, i, color, visited);
            flag = isBinaryGraph_BFS(graph, i);
            if (!flag) return false;
        }
        return true;
    }

    //全局判定标识位
    private static boolean flag = true;
    //只是用来记录递归次数，无核心作用
    private static int count = 0;

    //染色法
    private static void isBinaryGraph(int[][] graph, int cur, boolean[] color, boolean[] visited) {
        ++count;
        //根据flag提前结束递归(已经判定不是二分图，直接返回即可)
        if (!flag) return;
        int[] arr = graph[cur];
        for (int i : arr) {
            //如果接下来的元素没有被访问过
            if (!visited[i]) {
                visited[i] = true;
                //和邻居节点染上不一样的颜色
                color[i] = !color[cur];
                isBinaryGraph(graph, i, color, visited);
            } else {
                //如果元素已经被访问过
                if (color[i] == color[cur]) {
                    //如果两个相邻的节点颜色一样
                    flag = false;
                    return;
                }
            }
        }
    }


    private static boolean isBinaryGraph2(int[][] graph, int cur, boolean[] color, boolean[] visited) {
        ++count;
        int[] arr = graph[cur];
        for (int i : arr) {
            //如果接下来的元素没有被访问过
            if (!visited[i]) {
                visited[i] = true;
                //和邻居节点染上不一样的颜色
                color[i] = !color[cur];
                if (!isBinaryGraph2(graph, i, color, visited)) {
                    return false;
                }
            } else {
                //如果元素已经被访问过
                if (color[i] == color[cur]) {
                    //如果两个相邻的节点颜色一样
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isBinaryGraph_BFS(int[][] graph, int start) {
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] color = new boolean[graph.length];
        boolean[] visited = new boolean[graph.length];
        queue.add(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Integer cur = queue.poll();
                //遍历cur的邻居节点
                for (int n : graph[cur]) {
                    //看邻居节点是否 访问过
                    if (!visited[n]) {
                        //没访问设置访问 标识
                        visited[n] = true;
                        //设置相反的颜色
                        color[n] = !color[cur];
                        queue.add(n);
                    } else {
                        //如果已经访问过
                        if (color[cur] == color[n]) {
                            return false;
                        }
                    }
                }
                ++count;
            }
        }
        return true;
    }

    ;


}
