package bizfeng.leetcode.binarytree;

import bizfeng.leetcode.base.TreeNode;
import bizfeng.leetcode.binarytree.recovery.RecoverBinaryTree_index_1;
import lombok.extern.slf4j.Slf4j;


/**
 *
 * ####################【经典单方法+单参数+内部数组方式解决递归问题】#################################
 * 力扣 1373 题(难)
 * ⼆叉搜索⼦树的最⼤键值和
 * ⼆叉搜索树（简写作 BST）：简单说就是「左⼩右⼤」，对于每个节点，整棵左⼦ 树都⽐该节点的值⼩，整棵右⼦树都⽐该节点的值⼤。
 * <p>
 * 二叉搜索树的定义如下：
 * 任意节点的左子树中的键值都 小于 此节点的键值。
 * 任意节点的右子树中的键值都 大于 此节点的键值。
 * 任意节点的左子树和右子树都是二叉搜索树。
 * <p>
 * 函数签名：int maxSumBST(TreeNode root);
 * 要求：
 * 给你输⼊⼀棵⼆叉树，这棵⼆叉树的⼦树中 可能 包含⼆叉搜索树，请你找到节点之和最⼤的那棵 ⼆叉搜索树，返回它的节点值之和。
 * #===============================
 * #         1
 * #       /   \
 * #      4      3
 * #     /  \   /  \
 * #    6    5 2    8
 * #               / \
 * #              7   9
 * # 以3为根的子树就是BST
 * ================================
 * #        10
 * #         \
 * #          5
 * #         / \
 * #       3    4
 * # 叶子节点也可以认为是一个BST 3 、4 都是BST
 * #
 */
@Slf4j
public class BinaryTreeSubTreeMaxSum {

    public static void main(String[] args) {

        // * #         5
        // * #       /    \
        // * #      4      8
        // * #     /      / \
        // * #    3      6   2
        // * #
        // * #
        // [5,4,8,3,null,6,3]
        // TreeNode<Integer> tree = RecoverBinaryTree_index_1.buildTree(new int[]{1, 4, 6, 5, 3, 2, 8, 7, 9}, new int[]{6, 4, 5, 1, 2, 3, 7, 8, 9});
        TreeNode<Integer> tree = RecoverBinaryTree_index_1.buildTree(new int[]{5, 4, 3, 8, 6, 2}, new int[]{3, 4, 5, 6, 8, 2});

        // TreeNode<Integer> tree = RecoverBinaryTree_index_1.buildTree(new int[]{1, 4, 3, 40, 5, 2, 8, 7, 9}, new int[]{3, 4, 40, 1, 2, 5, 7, 8, 9});
        System.out.println(tree);

        int max = maxSumBST(tree);
        log.info("maxSumBST={}", max);
    }


    private static int maxSumBST(TreeNode<Integer> root) {
        findMaxBST(root);
        return MAX_SUM;
    }

    static int MAX_SUM = 0;

    //1.如果子树不是BST，那么父树也一定不是BST
    //2.左数任意节点都比 根节点和右树小
    private static int[] findMaxBST(TreeNode<Integer> root) {
        if (root == null) {
            //赋予默认值 符合左边的最大值 也小于根，右边的最小值也大于根 这样就能处理 单链树 的情况
            //叶子节点认为是BST
            return new int[]{1, Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        }
        int[] left = findMaxBST(root.left);
        int[] right = findMaxBST(root.right);

        int[] tmp = new int[4];
        //左边的最大值 也小于根，
        if (left[0] == 1 && left[2] < root.val
                && right[0] == 1 && right[1] > root.val) {
            tmp[0] = 1;//设置BST标识
            //处理叶子节点
            tmp[1] = Math.min(left[1], root.val);//设置最小值
            tmp[2] = Math.max(right[2], root.val);//设置最大值

            tmp[3] = left[3] + right[3] + root.val;//BST求和
            MAX_SUM = Math.max(MAX_SUM, tmp[3]);
        } else {
            tmp[0] = 0;//不是BST
        }
        return tmp;
    }


}
