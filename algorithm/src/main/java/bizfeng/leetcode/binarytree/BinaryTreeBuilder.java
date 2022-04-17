package bizfeng.leetcode.binarytree;

import bizfeng.leetcode.base.BinaryNode;

/**
 * 二叉树建造者
 */
public class BinaryTreeBuilder {

    public static BinaryNode<Integer> build() {
        //树张这样

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


    public static BinaryNode<Integer> buildPerfectTree() {
        //树张这样

        //         0
        //      /     \
        //     3        8
        //   /  \     /   \
        //  1     2  6     7

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
                .setRight(new BinaryNode<Integer>().setData(2))
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
}
