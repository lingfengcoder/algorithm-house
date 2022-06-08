package bizfeng.leetcode.base;

public class TreeNode<V> {
    public V val;
    public TreeNode<V> left;
    public TreeNode<V> right;

    public TreeNode() {
    }

    public TreeNode(V val) {
        this.val = val;
    }

    public TreeNode(V val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
