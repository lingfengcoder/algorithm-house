package bizfeng.leetcode.binarytree;

import bizfeng.leetcode.base.BinaryNode;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 最大二叉树
 * <p>
 * 给定一个不含重复元素的整数数组。一个一次数组 构建的最大二叉树定义如下：
 * 1.二叉树的根是数组中最大的元素
 * 2.左子树是通过 数组中最大值 左边部分 构造出来的 最大二叉树
 * 3.右子树是通过 数组中最大值 右边部分 构建出来的 最大二叉树
 * #
 * 输入:[3,2,1,6,0,5]
 * 输出: 返回下面这棵树的根节点：
 * #               6
 * #             /   \
 * #            3     5
 * #            \    /
 * #             2  0
 * #             \
 * #              1
 * #
 * 函数签名如下：
 * TreeNode constructMaxBinaryTree(int[]array);
 * 解法思路：分割数组+构建左右子树 递归
 */
@Slf4j
public class MaxBinaryTree {


    public static void main(String[] args) {
        int[] array = new int[]{3, 2, 1, 6, 0, 5};
        BinaryNode<Integer> tree = generate(array);
        log.info("{}", tree);
    }


    //思路：先从array 指定的范围内找到最大值，然后左右分别转换为树
    private static BinaryNode<Integer> generate(int[] array) {
        //找到最大值的坐标
        int maxValIdx = findMax(array);
        if (maxValIdx == -1) {
            return null;
        }
        //生成根节点
        BinaryNode<Integer> root = new BinaryNode<>();
        //设置根节点的val
        root.setData(array[maxValIdx]);
        if (maxValIdx != 0) {
            //对左边构建树
            int[] leftPart = Arrays.copyOfRange(array, 0, maxValIdx);
            BinaryNode<Integer> left = generate(leftPart);
            root.setLeft(left);
        }
        if (maxValIdx != array.length - 1) {
            //对右边构建树
            int[] rightPart = Arrays.copyOfRange(array, maxValIdx + 1, array.length);
            BinaryNode<Integer> right = generate(rightPart);
            root.setRight(right);
        }

        return root;
    }


    private static int findMax(int[] array) {
        if (array == null) {
            return -1;
        }
        int max = array[0];
        int idx = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
                idx = i;
            }
        }
        return idx;
    }

}
