package bizfeng.leetcode.binarytree;

import bizfeng.leetcode.base.TreeNode;
import bizfeng.leetcode.binarytree.recovery.RecoverBinaryTree_index_1;
import lombok.extern.slf4j.Slf4j;


/**
 * ####################【经典单方法+单参数+内部数组方式解决递归问题】#################################
 * 力扣 1373 题
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
public class BinaryTreeSubTreeMaxSum_bak {

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

        int max = findMaxSumBST(tree);
        log.info("maxSumBST={}", max);
    }

    //step1: 左树任意节点都比右树和跟节点 小 右子树任意一个节点都比跟节点 大
    //step2: 如果子树不是BST，那么父树也不是BST
    //step3: 叶子节点也算是BST
    private static int MAX;

    private static int findMaxSumBST(TreeNode<Integer> node) {
        maxSumBST(node);
        return MAX;
    }

    // * #         1
    // * #       /   \
    // * #      4      3
    // * #     /  \   /  \
    // * #    6    5 2    8
    // * #               / \
    // * #              7   9
    private static int[] maxSumBST(TreeNode<Integer> node) {
        //如果是叶子节点
        if (node == null) {
            return new int[]{1, Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        }
        int[] leftTmp = maxSumBST(node.left);// {1,2,2,2} MAX=2
        int[] rightTmp = maxSumBST(node.right);//{1,7,9,24} MAX=24
        //result 定义 int[4]={是否是BST,最小值,最大值,BST和}
        int[] result = new int[4];
        //1.如果左树和右树都是BST 2.左数比根小，右树比根大
        //两个条件都符合，说明本树也是BST
        if (leftTmp[0] == 1 && rightTmp[0] == 1
                //左数的最大值小于根节点 右树的最小值大于根节点
                && leftTmp[2] < node.val && rightTmp[1] > node.val) {
            result[0] = 1;
            result[1] = Math.min(leftTmp[1], node.val);
            result[2] = Math.max(rightTmp[2], node.val);
            //BST求和
            result[3] = leftTmp[3] + rightTmp[3] + node.val;
            MAX = Math.max(MAX, result[3]);
        } else {
            result[0] = 0;
        }
        return result;
    }


}
