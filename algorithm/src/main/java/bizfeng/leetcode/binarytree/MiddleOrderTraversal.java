package bizfeng.leetcode.binarytree;


import bizfeng.leetcode.base.BinaryNode;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 二叉树的前序、中序、后序遍历
 * <p>
 * 思考：其实无论三种遍历方式的任何一种都是进行了递归操作，那核心思想就在于建立 最小模型，
 * 当遍历的模型建立好了之后，其实每一层都只是一样的操作而已。无非三种执行的先后顺序不一致（添加根节点数据的顺序），导致的结果也不同而已。
 * <p>
 * <p>
 * 给定⼀个⼆叉树的根节点 root，返回它的 中序 遍历。
 * <p>
 * 输⼊：root = [1,null,2,3]
 * 输出：[1,3,2]
 */
@Slf4j
public class MiddleOrderTraversal {

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

        log.info("前序遍历结果：{}", preorder(tree));
        log.info("中序遍历结果：{}", inorder(tree));
        log.info("后序遍历结果：{}", postorder(tree));
    }


    /**
     * 前序遍历
     *
     * @param node
     * @return
     */
    private static LinkedList<Integer> preorder(BinaryNode<Integer> node) {
        if (node == null) {
            return null;
        }
        LinkedList<Integer> result = new LinkedList<>();
        preorderTraversal(node, result);
        return result;
    }

    /**
     * 前序遍历转换
     *
     * @param node
     * @param result
     */
    private static void preorderTraversal(BinaryNode<Integer> node, LinkedList<Integer> result) {
        if (node == null) {
            return;
        }
        //以为node的结构，认为进来的node就必然是根节点，所以直接添加
        result.offer(node.getData());
        //左子树
        if (node.getLeft() != null) {
            preorderTraversal(node.getLeft(), result);
        }
        //右子树
        if (node.getRight() != null) {
            preorderTraversal(node.getRight(), result);
        }
    }

    /**
     * 中序遍历
     *
     * @param node
     * @return
     */
    private static LinkedList<Integer> inorder(BinaryNode<Integer> node) {

        if (node == null) {
            return null;
        }
        LinkedList<Integer> result = new LinkedList<>();
        inorderTraversal(node, result);
        return result;
    }

    /**
     * 回溯法 中序遍历
     *
     * @param node
     * @param result
     */
    private static void inorderTraversal(BinaryNode<Integer> node, LinkedList<Integer> result) {
        if (node == null) {
            return;
        }
        //先左边
        if (node.getLeft() != null) {
            inorderTraversal(node.getLeft(), result);
        }
        //在中间
        result.offer(node.getData());
        //最后放右边
        if (node.getRight() != null) {
            inorderTraversal(node.getRight(), result);
        }
    }


    /**
     * 后序遍历
     *
     * @param node
     * @return
     */
    private static LinkedList<Integer> postorder(BinaryNode<Integer> node) {
        if (node == null) {
            return null;
        }
        LinkedList<Integer> result = new LinkedList<>();
        postorderTraversal(node, result);
        return result;
    }

    /**
     * 后序遍历转换器
     *
     * @param node
     * @param result
     */
    private static void postorderTraversal(BinaryNode<Integer> node, LinkedList<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.getLeft() != null) {
            postorderTraversal(node.getLeft(), result);
        }
        if (node.getRight() != null) {
            postorderTraversal(node.getRight(), result);
        }
        result.offer(node.getData());
    }


}
