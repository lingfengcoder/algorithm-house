package bizfeng.leetcode.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 图论 -> 图环检测
 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * <p>
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，
 * 其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
 * <p>
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 * 示例：
 * <p>
 * ###########################
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：true
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
 * ###########################
 * <p>
 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
 * 输出：false
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成  课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
 * ###########################
 */
@Slf4j
public class Graph_04 {


    public static void main(String[] args) {
        // int[][] array = new int[][]{{1, 2}, {3}, {3}, {}};
//        int[][] array = new int[][]{{1, 2}, {1, 3}, {2, 4}};int n=4;
        //5
        //[[1,4],[2,4],[3,1],[3,2]]
        int[][] array = new int[][]{{1, 4}, {2, 4}, {3, 1}, {3, 2}};
        int n = 5;
        // log.info(" 是否是 二分图 : {} count={}", isBipartite(array), count);
        log.info(" 是否 可以安排上课: {}  ", canFinish(n, array));

    }

    private static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<Integer>[] lists = buildGraph(numCourses, prerequisites);
        boolean[] visited = new boolean[numCourses];
        boolean[] path = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++) {
            if (!graph_dfs(lists, i, visited, path)) {
                return false;
            }
        }
        return true;
    }

    //把依赖关系转换成 邻接表 表示
    private static List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        List<Integer>[] result = new ArrayList[numCourses + 1];

        //初始化 邻接表
        for (int i = 0; i < numCourses; i++) {
            result[i] = new ArrayList<>();
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int[] prerequisite = prerequisites[i];
            int to = prerequisite[0];
            int from = prerequisite[1];
            // [0,1] 0号课程依赖 1号课程 ==> 先学1 才能学0 ==> 1->0
            result[from].add(to);
        }
        return result;
    }

    private static boolean graph_dfs(List<Integer>[] graph, int start, boolean[] visited, boolean[] path) {
        //形成了环
        if (path[start]) {
            return false;
        }
        //把当前节点标注一下，表示当前 正在走的路径
        path[start] = true;
        if (visited[start]) {
            //特别注意这块，当访问到前面么有问题的节点时，不能直接返回，要把当前节点的路径还原，
            // 因为canFinish方法中 要从每个节点出发 进行检测， path 和visited都是 复用的
            path[start] = false;
            return true;
        }
        visited[start] = true;
        for (Integer item : graph[start]) {
            //继续找下个节点
            if (!graph_dfs(graph, item, visited, path)) {
                return false;
            }
        }
        //路径都走完毕后，将路径复原
        path[start] = false;
        return true;
    }


}
