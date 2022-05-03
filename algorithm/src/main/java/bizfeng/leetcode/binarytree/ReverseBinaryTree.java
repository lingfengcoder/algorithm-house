package bizfeng.leetcode.binarytree;


import bizfeng.leetcode.base.BinaryNode;
import lombok.extern.slf4j.Slf4j;

/**
 * 反转二叉树
 */
@Slf4j
public class ReverseBinaryTree {


    public static void main(String[] args) {
        //树长这样

        //         0
        //      /     \
        //     3        8
        //   /  \     /   \
        //  1     2  6     7
        //       /
        //      4
        // 前序遍历结果： 0-3-1-2-4-8-6-7
        // 中序遍历结果: 1-3-4-2-0-6-8-7
        // 后序遍历结果：1-4-2-3-6-7-8-0
        BinaryNode<Integer> tree = BinaryTreeBuilder.build();

        //反转之后的结果为：
        //         0
        //       /   \
        //      8      3
        //     / \    / \
        //    7   6  2   1
        //            \
        //             4

        BinaryNode<Integer> newTree = reverseBinaryTree(tree);

        log.info("反转之后的树: {}", newTree);
    }

    /**
     * 反转 二叉树
     *
     * @param tree
     * @return
     */
    private static BinaryNode<Integer> reverseBinaryTree(BinaryNode<Integer> tree) {
        if (tree == null) {
            return null;
        }

        //左右节点交换
        reverseLR(tree);
        //左树非叶子节点，那就继续对左边树进行递归交换
        if (tree.getLeft() != null) {
            reverseBinaryTree(tree.getLeft());
        }
        //右树非叶子节点，继续对右树进行左右递归交换
        if (tree.getRight() != null) {
            reverseBinaryTree(tree.getRight());
        }
        return tree;
    }

    /**
     * 交换左右节点
     *
     * @param node
     */
    private static void reverseLR(BinaryNode<Integer> node) {
        if (node == null) {
            return;
        }
        BinaryNode<Integer> tmp = null;
        tmp = node.getLeft();
        node.setLeft(node.getRight());
        node.setRight(tmp);
    }
}
