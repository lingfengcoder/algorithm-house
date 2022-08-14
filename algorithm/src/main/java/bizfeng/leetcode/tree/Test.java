package bizfeng.leetcode.tree;

/**
 * @Auther: wz
 * @Date: 2022/8/14 21:11
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        int[] arr = {2, 1, 6, 5, 7, 3};
        AVLTree avl = new AVLTree();
        for (int i = 0; i < arr.length; i++) {
            avl.add(new Node(arr[i]));
        }
        avl.infixOrder();
        System.out.println(avl.root.height());
        System.out.println(avl.root.leftHeight());
        System.out.println(avl.root.rightHeight());
    }

}
