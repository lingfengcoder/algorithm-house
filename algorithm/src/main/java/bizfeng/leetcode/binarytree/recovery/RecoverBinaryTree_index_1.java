package bizfeng.leetcode.binarytree.recovery;


import bizfeng.leetcode.base.TreeNode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 根据一棵树的前序与中序序列构造二叉树
 * <p>
 * 注意：假设没有重复的元素
 * 前序序列： preorder=[3,9,20,15,7]
 * 中序序列： inorder=[9,3,15,20,7]
 * 还原的树：
 * #            3
 * #           / \
 * #          9   20
 * #              /\
 * #            15  7
 * #
 * 函数签名：
 * TreeNode buildTree(int[] preorder, int[] inorder);
 * <p>
 * 解法思路：
 * note 按照规律找到每一个子树的范围和特点进行迭代处理即可，注意边界问题
 * 1.首先要找到前序和中序序列的特点
 * #    比如:  a.前序第一个元素必然是根节点
 * #           b.中序可以根据根节点获取左右子树各自的长度
 * #           c.前序和中序的 序列中左右子树长度一致
 * #           d.每个子树的序列也遵循前序或者中序的规则(中->左->右 OR 左->中->右 )
 * 2.递归处理，找出子树构建的规律
 */
@Slf4j
public class RecoverBinaryTree_index_1 {

    public static void main(String[] args) {
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        TreeNode<Integer> tree = buildTree(preorder, inorder);
        log.info("还原出来的树：{}", tree);


        //         0
        //      /     \
        //     3        8
        //   /  \     /   \
        //  1     2  6     7
        //       /
        //      4
        preorder = new int[]{0, 3, 1, 2, 4, 8, 6, 7};
        inorder = new int[]{1, 3, 4, 2, 0, 6, 8, 7};
        tree = buildTree(preorder, inorder);
        log.info("还原出来的树：{}", tree);

    }

    public static TreeNode<Integer> buildTree(int[] preorder, int[] inorder) {
        //特殊情况处理
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0) {
            return null;
        }
        if (preorder.length == 1 || inorder.length == 1) {
            TreeNode<Integer> node = new TreeNode<>();
            node.val = (preorder[0]);
            return node;
        }
        //对于整个二叉树来说，前序遍历和后续遍历的起始点都是 0 -> len-1
        return _buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    private static TreeNode<Integer> _buildTree(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        //边界条件
        if (preStart > preEnd) {
            //如果preStart>preEnd 说明没有子节点了
            return null;
        }
        int rootData = preorder[preStart];
        //跳出点
        TreeNode<Integer> root = new TreeNode<>();
        root.val = (rootData);
        int rootIdx = findIdx(inorder, rootData);
        //左子树长度
        int leftSize = rootIdx - inStart;
        // * 前序序列： preorder=[3,9,20,15,7]
        // * 中序序列： inorder=[9,3,15,20,7]
        root.left = _buildTree(preorder, inorder, preStart + 1, preStart + leftSize, inStart, rootIdx - 1);
        root.right = _buildTree(preorder, inorder, preStart + leftSize + 1, preEnd, rootIdx + 1, inEnd);
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
