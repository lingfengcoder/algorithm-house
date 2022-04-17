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
    static class Node<T> extends BinaryNode<T> {
        //指向右侧下一个节点的指针
        private Node<T> next;
    }

    private static <T> Node<T> make(BinaryNode<T> tree) {
        Node<T> node = new Node<>();
        node.setData(tree.getData());
        if (tree.getLeft() != null) {
            Node<T> newLeft = make(tree.getLeft());
            node.setLeft(newLeft);
        }
        if (tree.getRight() != null) {
            Node<T> newRight = make(tree.getRight());
            node.setRight(newRight);
        }
        return node;
    }

    public static void main(String[] args) {
        BinaryNode<Integer> tree = BinaryTreeBuilder.buildPerfectTree();
        Node<Integer> newTree = make(tree);

        //         0
        //      /     \
        //     3        8
        //   /  \     /   \
        //  1     2  6     7
        log.info("tree={}", tree);
    }

//    private static Node fullLine(Node node) {
//
//    }

}
