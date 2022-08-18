package bizfeng.leetcode.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 图论 -> 图环检测
 * 现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。给你一个数组 prerequisites ，
 * 其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
 * <p>
 * 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
 * 返回你为了学完所有课程所安排的学习顺序。
 * 可能会有多个正确的顺序，你只要返回 任意一种 就可以了。如果不可能完成所有课程，返回 一个空数组 。
 * <p>
 * ###########################
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：[0,1]
 * 解释：总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
 * <p>
 * ###########################
 * <p>
 * 输入：numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
 * 输出：[0,2,1,3]
 * 解释：总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
 * 因此，一个正确的课程顺序是  [0,1,2,3] 。另一个正确的排序是  [0,2,1,3] 。
 * ###########################
 * 输入：numCourses = 1, prerequisites = []
 * 输出：[0]
 */
@Slf4j
public class Graph_05 {


    public static void main(String[] args) {
        // int[][] array = new int[][]{{1, 2}, {3}, {3}, {}};
//        int[][] array = new int[][]{{1, 2}, {1, 3}, {2, 4}};int n=4;
        //5
        //[[1,4],[2,4],[3,1],[3,2]]
        //[1,0],[2,0],[3,1],[3,2]
       // int[][] array = new int[][]{{1, 4}, {2, 4}, {3, 1}, {3, 2}};
      int[][] array = new int[][]{{0, 3}, {1, 3}, {2, 0}, {2, 1}};
        int n = 4;
        // log.info(" 是否是 二分图 : {} count={}", isBipartite(array), count);
        log.info("  课程安排: {}  ", findCourseOrder(n, array));

    }


    //课程
    private static LinkedList<Integer> findCourseOrder(int num, int[][] courseGraph) {
        List<Integer>[] lists = buildGraph(num, courseGraph);
        boolean[] visited = new boolean[num ];
        boolean[] path = new boolean[num ];
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            if (!findCourseOrder_dfs(lists, i, visited, path, result)) {
                return null;
            }
        }
        Collections.reverse(result);
        return result;
    }


    private static boolean findCourseOrder_dfs(List<Integer>[] lists, int start, boolean[] visited, boolean[] path, LinkedList<Integer> result) {
        //环检测
        if (path[start]) {
            return false;
        }
        //如果课程已经全部找到
        if (visited[start]) {
            path[start] = false;
            return true;
        }
        visited[start] = true;
        path[start] = true;
        for (Integer k : lists[start]) {
            if (!findCourseOrder_dfs(lists, k, visited, path, result)) {
                return false;
            }
        }
        path[start] = false;
        //后序遍历
        result.add(start);
        return true;
    }


    //把依赖关系转换成 邻接表 表示
    private static List<Integer>[] buildGraph(int numCourses, int[][] prerequisites) {
        List<Integer>[] result = new ArrayList[numCourses + 1];

        //初始化 邻接表
        for (int i = 0; i <= numCourses; i++) {
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


}
