package bizfeng.leetcode.binarytree.recovery;


import bizfeng.leetcode.base.BinaryNode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 根据一棵树的前序序与后序序列构造二叉树
 * <p>
 * 注意：假设没有重复的元素
 * 前序序列： preorder=[3,9,20,15,7]
 * 后序序列： postorder=[9,15,7,20,3]
 * 还原的树：
 * #            3
 * #           / \
 * #          9   20
 * #              /\
 * #            15  7
 * #
 * 函数签名：
 * TreeNode buildTree(int[] preorder, int[] postorder);
 * <p>
 * 解法思路：
 *  note 注意：前序序与后序序列 题目 答案不唯一 原因如下:
 * <p>
 *     3   |   3
 *    /    |    \
 *   2     |     2
 *   /     |      \
 *  1      |       1
 *  前序：3,2,1
 *  后序: 1,2,3
 *  这两棵树的前序和后序的结果是一致的
 *
 * 1.首先要找到前序和中序序列的特点
 * #    比如:  a.后续序最后一个元素必然是根节点
 * #           b.前续序第一个元素必然是根节点
 * #           c.前序和中序的 序列中左右子树长度一致
 * #           d.每个子树的序列也遵循后序或者前序的规则(左->右->中 OR 中->左->右 )
 * <p>
 * 2.递归处理，找出子树构建的规律
 * 3.找到右子树的长度，根据右子树长度找到左子树的根节点
 */
@Slf4j
public class RecoverBinaryTree_index_3 {

    public static void main(String[] args) {
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] postorder = new int[]{9, 15, 7, 20, 3};
        BinaryNode<Integer> tree = buildTree(preorder, postorder);
        log.info("还原出来的树：{}", tree);


        //         0
        //      /     \
        //     3        8
        //   /  \     /   \
        //  1     2  6     7
        //       /
        //      4
        preorder = new int[]{0, 3, 1, 2, 4, 8, 6, 7};
        postorder = new int[]{1, 4, 2, 3, 6, 7, 8, 0};
        tree = buildTree(preorder, postorder);
        log.info("还原出来的树：{}", tree);

    }

    private static BinaryNode<Integer> buildTree(int[] preorder, int[] postorder) {
        //为空判断
        if (postorder == null || preorder == null || postorder.length == 0 || preorder.length == 0) {
            return null;
        }
        //递归到最后节点判断
        return _buildTree(preorder, postorder, 0, preorder.length - 1, 0, postorder.length - 1);
    }

    private static BinaryNode<Integer> _buildTree(int[] preorder, int[] postorder, int preStart, int preEnd, int postStart, int postEnd) {

        // * 前序序列： preorder=[3,9,20,15,7]
        // * 后序序列： postorder=[9,15,7,20,3]
        if (preStart > preEnd) {
            return null;
        }

        BinaryNode<Integer> root = new BinaryNode<Integer>().setData(preorder[preStart]);
        if (preStart == preEnd) {
            return root;
        }
        //左子树的开始 默认下一个节点是左子树
        int preLeftIdx = preStart + 1;
        if (preLeftIdx > preorder.length - 1) {
            return null;
        }
        int leftNode = preorder[preLeftIdx];
        //找到左子树
        int postLeftIdx = findIdx(postorder, leftNode);
        //左子树长度
        int leftSize = postLeftIdx - postStart + 1;
        BinaryNode<Integer> left = _buildTree(preorder, postorder, preLeftIdx, preLeftIdx + leftSize - 1, postStart, postLeftIdx);
        root.setLeft(left);

        BinaryNode<Integer> right = _buildTree(preorder, postorder, preLeftIdx + leftSize, preEnd, postLeftIdx + 1, postEnd - 1);
        root.setRight(right);
        return root;
    }

    //从数组中找到目标的坐标
    private static int findIdx(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }
}
