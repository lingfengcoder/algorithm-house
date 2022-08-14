package bizfeng.leetcode.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @Auther: wz
 * @Date: 2022/8/14 21:09
 * @Description:
 */
@Setter
@Getter
public class AVLTree implements Cloneable{


    Node root;

    //添加节点
    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("null");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AVLTree avlTree = (AVLTree) o;
        return Objects.equals(root, avlTree.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }

    @Override
    public AVLTree clone() {
        try {
            AVLTree clone = (AVLTree) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
