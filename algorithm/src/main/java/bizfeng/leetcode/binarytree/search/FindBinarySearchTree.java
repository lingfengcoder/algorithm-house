package bizfeng.leetcode.binarytree.search;

import bizfeng.leetcode.base.TreeNode;

/**
 * 找出 二叉搜索树
 * <p>
 * 二叉搜索树的定义如下：
 * <p>
 * 任意节点的左子树中的键值都 小于 此节点的键值。
 * 任意节点的右子树中的键值都 大于 此节点的键值。
 * 任意节点的左子树和右子树都是二叉搜索树。
 * * #===============================
 * * #         1
 * * #       /   \
 * * #      4      3
 * * #     /  \   /  \
 * * #    6    5 2    8
 * * #               / \
 * * #              7   9
 * * # 以3为根的子树就是BST
 * * ================================
 * * #        10
 * * #         \
 * * #          5
 * * #         / \
 * * #       3    4
 * * # 叶子节点也可以认为是一个BST 3 、4 分别都是BST
 * <p>
 * #         5
 * #       /    \
 * #      4      8
 * #     /      / \
 * #    3      6   2
 * #  4-3 也是一个BST
 */
public class FindBinarySearchTree {

    public static void main(String[] args) {

    }

    int MAX_SUM = 0;

    //tmp flag=[0]  min=tmp[1]   max=tmp[2]  maxSum=tmp[3]
    //判断是否是二叉搜索树
    private int[] findSearchTreeMaxSum(TreeNode<Integer> root) {
        if (root == null) {
            //赋予默认值 符合左边的最大值 也小于根，右边的最小值也大于根 这样就能处理 单链树 的情况
            //叶子节点认为是BST
            return new int[]{1, Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        }
        int[] left = findSearchTreeMaxSum(root.left);
        int[] right = findSearchTreeMaxSum(root.right);

        int[] tmp = new int[4];
        //左边的最大值 也小于根，
        if (left[0] == 1 && left[2] < root.val
                && right[0] == 1 && right[1] > root.val) {
            tmp[0] = 1;//设置BST标识
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
