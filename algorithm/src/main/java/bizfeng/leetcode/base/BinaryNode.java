package bizfeng.leetcode.base;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
/**
 * 二叉树节点
 */
public class BinaryNode<T> {
    private T data;
    private BinaryNode<T> parent;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    @Override
    public String toString() {
        return "BinaryNode{" +
                "data=" + data +
                ", parent=" + (parent != null ? parent.getData() : null) +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryNode<?> that = (BinaryNode<?>) o;
        return Objects.equals(data, that.data) && Objects.equals(parent, that.parent) && Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, parent, left, right);
    }
}