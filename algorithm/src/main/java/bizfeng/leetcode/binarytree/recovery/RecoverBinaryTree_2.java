package bizfeng.leetcode.binarytree.recovery;


import bizfeng.leetcode.base.BinaryNode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 根据一棵树的中序与后序序列构造二叉树
 * <p>
 * 注意：假设没有重复的元素
 * 中序序列： inorder=[9,3,15,20,7]
 * 后序序列： postorder=[9,15,7,20,3]
 * 还原的树：
 * #            3
 * #           / \
 * #          9   20
 * #              /\
 * #            15  7
 * #
 * 函数签名：
 * TreeNode buildTree(int[] postorder, int[] inorder);
 * <p>
 * 解法思路：
 * note 这类题目都可以 总结化简为最小二叉树的思想，从最简单的二叉树找规律
 * 1.首先要找到前序和中序序列的特点
 * #    比如:  a.后续序最后一个元素必然是根节点
 * #           b.中序可以根据根节点获取左右子树各自的长度
 * #           c.后序和中序的 序列中左右子树长度一致
 * #           d.每个子树的序列也遵循后序或者中序的规则(左->右->中 OR 左->中->右 )
 * #           e.中序遍历的根节点坐标-1=后续遍历左子树结束
 * 2.递归处理，找出子树构建的规律
 * 3.找到右子树的长度，根据右子树长度找到左子树的根节点
 */
@Slf4j
public class RecoverBinaryTree_2 {

    public static void main(String[] args) {
        int[] postorder = new int[]{9, 15, 7, 20, 3};
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        BinaryNode<Integer> tree = buildTree(postorder, inorder);
        log.info("还原出来的树：{}", tree);


        //         0
        //      /     \
        //     3        8
        //   /  \     /   \
        //  1     2  6     7
        //       /
        //      4
        postorder = new int[]{1, 4, 2, 3, 6, 7, 8, 0};
        inorder = new int[]{1, 3, 4, 2, 0, 6, 8, 7};
        tree = buildTree(postorder, inorder);
        log.info("还原出来的树：{}", tree);

    }

    private static BinaryNode<Integer> buildTree(int[] postorder, int[] inorder) {
        //为空判断
        if (postorder == null || inorder == null || postorder.length == 0 || inorder.length == 0) {
            return null;
        }
        //递归到最后节点判断
        if (postorder.length == 1) {
            return new BinaryNode<Integer>().setData(postorder[0]);
        }
        if (inorder.length == 1) {
            return new BinaryNode<Integer>().setData(inorder[0]);
        }
        //找到根节点
        int rootIdx = postorder.length - 1;
        int rootData = postorder[rootIdx];
        BinaryNode<Integer> root = new BinaryNode<Integer>().setData(rootData);
        //根据根节点分别找到左右子树长度
        int inorderRootIdx = findIdx(inorder, rootData);

        //左子树处理
        //找出后序遍历中 左子树的开始坐标
        int postorderLeftIdx = inorderRootIdx - 1;// inorder.length-1 - rightLen - 1;
        //后序遍历的左子树
        int[] postorderLeftTree = Arrays.copyOfRange(postorder, 0, postorderLeftIdx + 1);
        //中序遍历的左子树
        int[] inorderLeftTree = Arrays.copyOfRange(inorder, 0, inorderRootIdx);
        BinaryNode<Integer> left = buildTree(postorderLeftTree, inorderLeftTree);
        root.setLeft(left);

        //右子树处理
        //后序遍历的右子树
        int[] postorderRightTree = Arrays.copyOfRange(postorder, postorderLeftIdx + 1, postorder.length - 1);
        //中序遍历的右子树
        int[] inorderRightTree = Arrays.copyOfRange(inorder, inorderRootIdx + 1, inorder.length);
        BinaryNode<Integer> right = buildTree(postorderRightTree, inorderRightTree);
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
