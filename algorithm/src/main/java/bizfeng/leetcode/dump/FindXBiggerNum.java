package bizfeng.leetcode.dump;


import java.util.PriorityQueue;

/**
 * 数组中第K大的数字
 * <p>
 * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最⼤的元素。
 * 请注意，你需要找的是数组排序后的第 k 个最⼤的元素，⽽不是第 k 个不同的元素
 * <p>
 * 输⼊：[3,2,1,5,6,4] 和 k = 2
 * 输出：5
 * <p>
 * <p>
 * 输⼊：[3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出：4
 * <p>
 * <p>
 * 解题思路
 * <p>
 * 可以把⼩顶堆 pq 理解成⼀个筛⼦，较⼤的元素会沉淀下去，较⼩的元素会浮上来；
 * 当堆⼤⼩超过 k 的时 候，我们就删掉堆顶的元素，因为这些元素⽐较⼩，⽽我们想要的是前 k 个最⼤元素嘛。
 * 当 nums 中的所有元素都过了⼀遍之后，筛⼦⾥⾯留下的就是最⼤的 k 个元素，⽽堆顶元素是堆中最⼩的元 素，也就是「第 k 个最⼤的元素」。
 * ⼆叉堆插⼊和删除的时间复杂度和堆中的元素个数有关，在这⾥我们堆的⼤⼩不会超过 k，所以插⼊和删除 元素的复杂度是 O(logK)，
 * 再套⼀层 for 循环，总的时间复杂度就是 O(NlogK)。
 * 当然，这道题可以有效率更⾼的解法叫「快速选择算法」，只需要 O(N) 的时间复杂度。
 * 快速选择算法不⽤借助⼆叉堆结构，⽽是稍微改造了快速排序的算法思路，有兴趣的读者可以看
 */
public class FindXBiggerNum {


    public static void main(String[] args) {
        Integer[] data = new Integer[]{3, 2, 1, 5, 6, 4};
        int k = 2;
        int target = findXBiggerNum(data, k);
        System.out.println(target);

        data = new Integer[]{3, 2, 3, 1, 2, 4, 5, 5, 6};
        k = 4;
        target = findXBiggerNum(data, k);
        System.out.println(target);

    }

    /**
     * 寻找第K大的元素 时间复杂度  N*logK
     *
     * @param data
     * @param k
     * @return
     */
    private static int findXBiggerNum(Integer[] data, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < data.length; i++) {
            //每个元素都添加进去
            queue.offer(data[i]);
            if (queue.size() > k) {
                //将堆顶 最小的元素剔除
                queue.poll();
            }
        }
        return queue.peek();
    }
}
