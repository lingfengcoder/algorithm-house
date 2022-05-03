package bizfeng.leetcode.binarytree;


import bizfeng.leetcode.base.BinaryNode;
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
 * 1.首先要找到前序和中序序列的特点
 * #    比如:  a.前序第一个元素必然是根节点
 * #           b.中序可以根据根节点获取左右子树各自的长度
 * #           c.前序和中序的 序列中左右子树长度一致
 * #           d.每个子树的序列也遵循前序或者中序的规则(中->左->右 OR 左->中->右 )
 * 2.递归处理，找出子树构建的规律
 */
@Slf4j
public class RecoverBinaryTree {

    public static void main(String[] args) {
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        BinaryNode<Integer> tree = buildTree(preorder, inorder);
        log.info("还原出来的树：{}", tree);


        //         0
        //      /     \
        //     3        8
        //   /  \     /   \
        //  1     2  6     7
        //       /
        //      4
        preorder = new int[]{0,3,1,2,4,8,6,7};
        inorder = new int[]{1,3,4,2,0,6,8,7};
        tree = buildTree(preorder, inorder);
        log.info("还原出来的树：{}", tree);

    }

    private static BinaryNode<Integer> buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0) {
            return null;
        }
        if (preorder.length == 1) {
            return new BinaryNode<Integer>().setData(preorder[0]);
        }
        if (inorder.length == 1) {
            return new BinaryNode<Integer>().setData(inorder[0]);
        }
        //1.找到root
        int rootVal = preorder[0];
        //2.找到中序遍历的root的坐标
        int idx = findIdx(inorder, rootVal);
        //3.获取左右子树的长度
        int leftLen = idx + 1;
        BinaryNode<Integer> root = new BinaryNode<>();
        root.setData(rootVal);
        //构建左子树
        int[] preOrderLeft = Arrays.copyOfRange(preorder, 1, leftLen);
        int[] inorderLeft = Arrays.copyOfRange(inorder, 0, idx);
        BinaryNode<Integer> left = buildTree(preOrderLeft, inorderLeft);
        root.setLeft(left);
        //构建右子树
        int[] preOrderRight = Arrays.copyOfRange(preorder, leftLen, preorder.length);
        int[] inorderRight = Arrays.copyOfRange(inorder, idx + 1, inorder.length);
        BinaryNode<Integer> right = buildTree(preOrderRight, inorderRight);
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
