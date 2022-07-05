package bizfeng.leetcode.binarytree;

import bizfeng.leetcode.base.BinaryNode;
import bizfeng.leetcode.binarytree.recovery.RecoverBinaryTree_1;


/**
 * 二叉树builder
 */
public class BinaryTreeBuilder {

    //使用前序和中序序列构造树
    public static BinaryNode<Integer> buildTreeWithPreInorder(int[] preorder, int[] inorder) {
        return RecoverBinaryTree_1.buildTree(preorder, inorder);
    }

    //普通二叉树
    public static BinaryNode<Integer> build() {
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

        //种树
        //长出树根
        BinaryNode<Integer> rootNode = new BinaryNode<>();

        BinaryNode<Integer> leftNode = new BinaryNode<>();
        BinaryNode<Integer> rightNode = new BinaryNode<>();

        rootNode
                //长出左边的树枝
                .setLeft(leftNode)
                //长出右边的树枝
                .setRight(rightNode)
                .setData(0);

        leftNode
                //左树叶
                .setLeft(new BinaryNode<Integer>().setData(1))
                //右树枝
                .setRight(new BinaryNode<Integer>().setData(2)
                        //右树枝上的左叶子
                        .setLeft(new BinaryNode<Integer>().setData(4))
                )
                .setData(3);
        //长出右边的树枝

        rightNode
                //左树叶
                .setLeft(new BinaryNode<Integer>().setData(6))
                //右树叶
                .setRight(new BinaryNode<Integer>().setData(7))
                .setData(8);
        return rootNode;
    }


    //完美二叉树
    public static BinaryNode<Integer> buildPerfectTree() {
        //树长这样

        //         15
        //      /      \
        //    13        14
        //   /  \      /   \
        //  9    10   11    12
        // / \   / \ / \   / \
        // 1  2 3  4 5  6  7  8
        //种树
        //长出树根
        BinaryNode<Integer> rootNode = new BinaryNode<>();

        BinaryNode<Integer> leftNode = new BinaryNode<>();
        BinaryNode<Integer> rightNode = new BinaryNode<>();

        rootNode
                //长出左边的树枝
                .setLeft(leftNode)
                //长出右边的树枝
                .setRight(rightNode)
                .setData(15);

        leftNode
                //数据
                .setData(13)
                //左树叶
                .setLeft(new BinaryNode<Integer>().setData(9)
                        .setLeft(new BinaryNode<Integer>().setData(1))
                        .setRight(new BinaryNode<Integer>().setData(2))
                )

                //右树枝
                .setRight(new BinaryNode<Integer>().setData(10)
                        .setLeft(new BinaryNode<Integer>().setData(3))
                        .setRight(new BinaryNode<Integer>().setData(4))
                );

        //长出右边的树枝

        rightNode
                .setData(14)
                //左树叶
                .setLeft(new BinaryNode<Integer>().setData(11)
                        .setLeft(new BinaryNode<Integer>().setData(5))
                        .setRight(new BinaryNode<Integer>().setData(6))
                )
                //右树叶
                .setRight(new BinaryNode<Integer>().setData(12)
                        .setLeft(new BinaryNode<Integer>().setData(7))
                        .setRight(new BinaryNode<Integer>().setData(8))
                );
        return rootNode;
    }

    public static BinaryNode<Integer> buildDuplicateTree() {
        //树长这样

        //         0
        //      /      \
        //    1          2
        //   /  \      /   \
        //  3    10   3    10
        // / \   / \ / \   / \
        //4   2 3  4 4  6  7  4
        //种树
        //长出树根
        BinaryNode<Integer> rootNode = new BinaryNode<>();

        BinaryNode<Integer> leftNode = new BinaryNode<>();
        BinaryNode<Integer> rightNode = new BinaryNode<>();

        rootNode
                //长出左边的树枝
                .setLeft(leftNode)
                //长出右边的树枝
                .setRight(rightNode)
                .setData(0);

        leftNode
                //数据
                .setData(1)
                //左树叶
                .setLeft(new BinaryNode<Integer>().setData(3)
                        .setLeft(new BinaryNode<Integer>().setData(4))
                        .setRight(new BinaryNode<Integer>().setData(2))
                )

                //右树枝
                .setRight(new BinaryNode<Integer>().setData(10)
                        .setLeft(new BinaryNode<Integer>().setData(3))
                        .setRight(new BinaryNode<Integer>().setData(4))
                );

        //长出右边的树枝

        rightNode
                .setData(2)
                //左树叶
                .setLeft(new BinaryNode<Integer>().setData(3)
                        .setLeft(new BinaryNode<Integer>().setData(4))
                        .setRight(new BinaryNode<Integer>().setData(6))
                )
                //右树叶
                .setRight(new BinaryNode<Integer>().setData(10)
                        .setLeft(new BinaryNode<Integer>().setData(7))
                        .setRight(new BinaryNode<Integer>().setData(4))
                );
        return rootNode;
    }

}
