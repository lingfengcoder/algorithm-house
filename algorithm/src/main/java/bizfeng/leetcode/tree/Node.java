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
public class Node {


    int value;
    Node left;
    Node right;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return value == node.value && Objects.equals(left, node.left) && Objects.equals(right, node.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, left, right);
    }

    public Node() {
    }

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    //返回左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        } else {
            return left.height();
        }
    }

    //返回右子树的高度
    public int rightHeight() {
        if (right == null) {
            return 0;
        } else {
            return right.height();
        }
    }

    //返回以该节点为根节点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    //左旋转的方法
    private void leftRotate() {
        //创建一个新节点,值为当前根节点的值
        Node newNode = new Node(value);
        //把新节点的左子树设置为当前节点的左子树
        newNode.left = left;
        //把新节点的右子树设置为当前节点右子树的左子树
        newNode.right = right.left;
        //把当前节点的值替换为右子节点的值
        value = right.value;
        //把当前节点的右子树设置成为当前节点右子树的右子树
        right = right.right;
        //把当前节点的左子树(左子节点)设置为新的节点
        left = newNode;

    }

    //右旋转的方法
    private void rightRotate() {
        //创建一个新节点，值为当前根节点的值
        Node newNode = new Node(value);
        //把新节点的右子树设置为当前节点的右子树
        newNode.right = right;
        //把新节点的左子树设置为当前节点左子树的右子树
        newNode.left = left.right;
        //把当前节点的值替换为左子节点的值
        value = left.value;
        //把当前节点的左子树设置成为当前节点左子树的左子树
        left = left.left;
        //把当前节点的右子树(右子节点)设置为新的节点
        right = newNode;
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        // System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    //添加节点
    public void add(Node node) {
        if (node == null) {
            return;
        }
        if (node.value < this.value) {
            //如果当前结点的左子节点为null，则将node添加到当前结点的左子节点上
            if (this.left == null) {
                this.left = node;
            } else { //递归向左子树添加
                this.left.add(node);
            }
        } else { //传入结点的值大于等于当前子树的根节点的值
            if (this.right == null) {
                this.right = node;
            } else { //递归向右子树添加
                this.right.add(node);
            }
        }
        //当添加完一个节点后，如果右子树的高度-左子树的高度大于1，左旋转
        if (rightHeight() - leftHeight() > 1) {
            //如果他的右子树的左子树的高度大于它的右子树的右子树的高度
            if (right != null && right.leftHeight() > right.rightHeight()) {
                //先对当前节点的右节点(右子树)进行右旋转
                right.rightRotate();
                //然后再对当前节点进行左旋转
                leftRotate();
            } else {
                //直接进行左旋转即可
                leftRotate();
            }
            return;
        }
        //当添加完一个节点后，如果左子树的高度-右子树的高度大于1，右旋转
        if (leftHeight() - rightHeight() > 1) {
            //如果它的左子树的右子树高度大于它的左子树的左子树的高度
            if (left != null && left.rightHeight() > left.leftHeight()) {
                //先对当前节点的左节点(左子树)进行左旋转
                left.leftRotate();
                //再对当前节点进行右旋转
                rightRotate();
            } else {
                //直接进行右旋转即可
                rightRotate();
            }
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
