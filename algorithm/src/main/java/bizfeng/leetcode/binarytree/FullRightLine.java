package bizfeng.leetcode.binarytree;

import bizfeng.leetcode.base.BinaryNode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * 题目：给定一个完美的二叉树，其所有的叶子节点都在同一层，每一个父节点都有两个子节点。
 * Node 是二叉树节点定义。填充它的每一个next指针，让这个指针指向其下一个右侧节点。
 * 如果找不到下一个右侧节点，则将next指针设置为NULL。
 * 初始状态下，所有next指针都被设置为NULL。
 * <p>
 * //         0
 * //      /     \
 * //     3        8
 * //   /  \     /   \
 * //  1     2  6     7
 * <p>
 * 结果：
 * //         0 -->NULL
 * //      /     \
 * //     3  -->  8 -->NULL
 * //   /  \     / \
 * //  1 -->2-->6-->7 -->NULL
 */
@Slf4j
public class FullRightLine {

    @Setter
    @Getter
    @ToString
    //定义一个题目中的Node 因为是二叉树所以可以直接继承BinaryNode
    //同时题目中规定每个节点都有一个指向右侧节点的指针next
    static class Node<T> {
        private T data;
        private Node<T> left;
        private Node<T> right;
        private Node<T> next;
    }


    //将传入的普通二叉树BinaryNode，转换为带有 “右指针” 的二叉树Node
    private static <T> Node<T> make(BinaryNode<T> tree) {
        Node<T> node = new Node<>();
        //先获取元数据
        node.setData(tree.getData());
        //左子树克隆
        if (tree.getLeft() != null) {
            Node<T> newLeft = make(tree.getLeft());
            node.setLeft(newLeft);
        }
        //右子树克隆
        if (tree.getRight() != null) {
            Node<T> newRight = make(tree.getRight());
            node.setRight(newRight);
        }
        return node;
    }

    public static void main(String[] args) {
        //构建一个完美二叉树
        BinaryNode<Integer> tree = BinaryTreeBuilder.buildPerfectTree();
        Node<Integer> newTree = make(tree);

        //         15
        //      /      \
        //    13        14
        //   /  \      /   \
        //  9    10   11    12
        // / \   / \ / \   / \
        // 1  2 3  4 5  6  7  8

        log.info("tree={}", tree);


        //解法：
        fullBetweenLine(newTree.getLeft(), newTree.getRight());
        log.info("newTree={}", newTree);
    }


    //⼆叉树的问题难点在于，如何把题⽬的要求细化成每个节点需要做的事情，但是如果只依赖 ⼀个节点的话，
    // 肯定是没办法连接「跨⽗节点」的两个相邻节点的。
    // 那么，我们的做法就是增加函数参数，
    // ⼀个节点做不到，我们就给他安排两个节点，
    // 「将每⼀层⼆叉树节点 连接起来」可以细化成「将每两个相邻节点都连接起来」：
    private static <T, N extends Node<T>> void fullBetweenLine(N left, N right) {
        if (left == null || right == null) {
            return;
        }
        //左右相连
        left.setNext(right);
        //对左子树的子树进行连接
        fullBetweenLine(left.getLeft(), left.getRight());
        //对右子树的子树进行连接
        fullBetweenLine(right.getLeft(), right.getRight());
        //子树之间连接
        fullBetweenLine(left.getRight(), right.getLeft());
    }

}
