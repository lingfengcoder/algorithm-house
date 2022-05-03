package bizfeng.leetcode.binarytree;


import bizfeng.leetcode.base.BinaryNode;
import lombok.extern.slf4j.Slf4j;

/**
 * 二叉树展开为链表
 * //         1
 * //      /    \
 * //     2      5
 * //   /  \      \
 * //  3     4      6
 * <p>
 * 转化为
 * <p>
 * #         1
 * #          \
 * #           2
 * #            \
 * #             3
 * #              \
 * #               4
 * #                \
 * #                 5
 * #                  \
 * #                   6
 */
@Slf4j
public class BinaryTreeTransToLink {

    //构建二叉树
    private static BinaryNode<Integer> buildTree() {
        BinaryNode<Integer> rootNode = new BinaryNode<>();
        BinaryNode<Integer> leftNode = new BinaryNode<>();
        BinaryNode<Integer> rightNode = new BinaryNode<>();
        rootNode
                .setData(1)
                //长出左边的树枝
                .setLeft(leftNode)
                //长出右边的树枝
                .setRight(rightNode);

        leftNode.setData(2)
                //左树叶
                .setLeft(new BinaryNode<Integer>().setData(3))
                //右树枝
                .setRight(new BinaryNode<Integer>().setData(4)
                );
        //长出右边的树枝
        rightNode.setData(5)
                //右树叶
                .setRight(new BinaryNode<Integer>().setData(6)
                );
        return rootNode;
    }

    public static void main(String[] args) {
        BinaryNode<Integer> tree = buildTree();

        trans2(tree);
        log.info("{}", tree);
    }


    // * //         1
    // * //      /    \
    // * //     2      5
    // * //   /  \      \
    // * //  3     4      6
    //   // /\     / \
    //     N  M    7  8

    //思路：后序遍历 + 转换

    private static <T> void trans2(BinaryNode<T> node) {
        if (node == null) {
            return;
        }
        //1.后序遍历
        trans2(node.getLeft());
        trans2(node.getRight());

        BinaryNode<T> left = node.getLeft();
        BinaryNode<T> right = node.getRight();
        //2.将左⼦树作为右⼦树
        node.setRight(left);
        node.setLeft(null);

        //3.将右子树换到 当前右子树的末端
        BinaryNode<T> tmpRight = node;
        while (tmpRight.getRight() != null) {
            tmpRight = tmpRight.getRight();
        }
        tmpRight.setRight(right);
    }

}
