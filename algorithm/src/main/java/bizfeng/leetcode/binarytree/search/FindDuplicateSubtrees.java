package bizfeng.leetcode.binarytree.search;


import bizfeng.leetcode.base.BinaryNode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 寻找重复的子树
 * 给定一个二叉树，返回所有重复的子树。对于同一类重复的子树，返回其中任意一颗的根节点即可。
 * 两颗重复是指它们具有相同的结构以及相同的节点值。
 * <p>
 * 示例：
 * #               1
 * #             /  \
 * #            2    3
 * #           /    / \
 * #          4    2   4
 * #              /
 * #             4
 * 两颗重复的子树:
 * #     2         和     4   (叶子节点也是一颗树)
 * #    /
 * #   4
 * 要以列表的形式返回上述重复子树的根节点。
 * 函数签名如下：
 * List<TreeNode> findDuplicateSubtrees(TreeNode root);
 */
@Slf4j
public class FindDuplicateSubtrees {


    public static void main(String[] args) {
        //note 此处使用前序和中序反向构建，其实是不安全的，如果有重复的元素，可能会定位失败从而导致还原树失败
        BinaryNode<Integer> root = new BinaryNode<>();
        root.setData(1);
        //left
        BinaryNode<Integer> left = new BinaryNode<>();
        root.setLeft(left);
        left.setParent(root);
        left.setData(2);
        left.setLeft(new BinaryNode<Integer>().setData(4).setParent(left));
        //right
        BinaryNode<Integer> right = new BinaryNode<>();
        root.setRight(right);
        right.setParent(root);
        right.setData(3);


        BinaryNode<Integer> right_leftNode = new BinaryNode<Integer>().setData(2).setParent(right);
        right.setLeft(right_leftNode);

        BinaryNode<Integer> right_left_leftNode = new BinaryNode<Integer>().setData(4).setParent(right_leftNode);
        right_leftNode.setLeft(right_left_leftNode);

        right.setRight(new BinaryNode<Integer>().setData(4).setParent(right));

        log.info(right.toString());

        List<BinaryNode<Integer>> result = findDuplicateSubtrees(root);
        System.out.println(result);
    }


    public static List<BinaryNode<Integer>> findDuplicateSubtrees(BinaryNode<Integer> root) {
        List<BinaryNode<Integer>> leafList = new ArrayList<>();
        List<BinaryNode<Integer>> result = new ArrayList<>();
        _find(root, leafList, result);
        leafList.clear();
        return result;
    }

    //1.找叶子节点，如果叶子节点不重复，那么子树肯定也不重复；如果叶子节点重复，子树不一定重复
    //2.相同结构，也就意味着，左树只能在左树中找，右树也只能在右树中找
    private static BinaryNode<Integer> _find(BinaryNode<Integer> node, List<BinaryNode<Integer>> leafList, List<BinaryNode<Integer>> list) {
        //step1: 叶子节点的判断，如果叶子节点不重复，那么子树肯定也不重复；如果叶子节点重复，子树不一定重复
        //先找叶子节点
        BinaryNode<Integer> left = node.getLeft();
        if (left != null) {
            BinaryNode<Integer> leftLeaf = _find(left, leafList, list);
            if (leftLeaf == null) {
                subNodeHandler(node, left, leafList, list);
                //加入叶子节点集合
                leafList.add(left);
            }
        }
        BinaryNode<Integer> right = node.getRight();
        if (right != null) {
            BinaryNode<Integer> rightLeaf = _find(right, leafList, list);
            if (rightLeaf == null) {
                subNodeHandler(node, right, leafList, list);
                //加入叶子节点集合
                leafList.add(right);
            }
        }

        if (node.getLeft() == null & node.getRight() == null) {
            //说是叶子节点
            return null;
        }
        return null;
    }

    private static void subNodeHandler(BinaryNode<Integer> node, BinaryNode<Integer> subNode, List<BinaryNode<Integer>> leafList, List<BinaryNode<Integer>> list) {
        if (!leafList.isEmpty()) {
            for (BinaryNode<Integer> leaf : leafList) {
                //有相同的节点
                if (leaf.getData().equals(subNode.getData())) {
                    list.add(subNode);
                    //如果父节点也相同，则加入
                    if (leaf.getParent() != null && node.getData().equals(leaf.getParent().getData())) {
                        list.add(node);
                    }
                }
            }
        }
    }


}
