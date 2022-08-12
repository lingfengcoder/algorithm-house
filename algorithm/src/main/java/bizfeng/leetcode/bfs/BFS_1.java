package bizfeng.leetcode.bfs;

import bizfeng.leetcode.base.BinaryNode;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Auther: wz
 * @Date: 2022/8/10 20:27
 * @Description: 二叉树的最小深度
 * <p>
 * 给定一个二叉树，找出其最小深度。
 * <p>
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * <p>
 * 说明：叶子节点是指没有子节点的节点。
 * #####################################################
 * 示例：
 * <p>
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：2
 * <p>
 * #######################################################
 * 输入：root = [2,null,3,null,4,null,5,null,6]
 * 输出：5
 */
public class BFS_1 {

    public static void main(String[] args) {

    }

    //               3
    //              / \
    //             9   20
    //                /  \
    //               15   7

    private static int findMinHeight(BinaryNode<Integer> root, BinaryNode<Integer> end) {
        if (root == null) {
            return 0;
        }
        Queue<BinaryNode<Integer>> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;
        while (!queue.isEmpty()) {
            //size要独立出来
            for (int i = 0; i < queue.size(); i++) {
                BinaryNode<Integer> item = queue.poll();
                if (item.getLeft() == null && item.getRight() == null) {
                    return depth;
                }
                if (item.getLeft() != null) {
                    queue.offer(item.getLeft());
                }
                if (item.getRight() != null) {
                    queue.offer(item.getRight());
                }
            }
            depth++;
        }
        return depth;
    }

}
