package bizfeng.leetcode.dump;


import bizfeng.leetcode.base.LinkedNode;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 合并 K 个升序链表
 * <p>
 * <p>
 * <p>
 * 给你⼀个链表数组，每个链表都已经按升序排列，请你将这些链表合并成⼀个升序链表，返回合并后的链 表。
 * <p>
 * 输⼊：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 将它们合并到⼀个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 * <p>
 * 合并两个有序链表 的延伸，利⽤ 优先级队列（⼆叉堆） 进⾏节点排序即可。
 */
public class MergeSomeSortedLinkQueue {


    public static void main(String[] args) {
        int[] data1 = new int[]{1, 4, 5};
        int[] data2 = new int[]{1, 3, 4};
        int[] data3 = new int[]{2, 6};
        LinkedNode<Integer> node1 = generalData(data1);
        LinkedNode<Integer> node2 = generalData(data2);
        LinkedNode<Integer> node3 = generalData(data3);
        LinkedNode resultNode = merge(new LinkedNode[]{node1, node2, node3});
        System.out.println(resultNode);
    }

    private static LinkedNode<Integer> generalData(int[] data) {
        LinkedNode<Integer> head = new LinkedNode<>();
        LinkedNode<Integer> tmpHead = head;
        for (int item : data) {
            LinkedNode<Integer> tmp = new LinkedNode<>();
            tmp.setData(item);
            tmpHead.setNext(tmp);
            tmpHead = tmpHead.getNext();
        }
        return head.getNext();
    }

    private static LinkedNode<Integer> merge(LinkedNode<Integer>[] data) {
        //头节点
        LinkedNode<Integer> resultHead = new LinkedNode<>();
        PriorityQueue<LinkedNode<Integer>> queue = new PriorityQueue<>(data.length, Comparator.comparingInt(LinkedNode::getData));

        //将各个链表的头节点放入queue
        for (int i = 0; i < data.length; i++) {
            queue.offer(data[i]);
        }

        LinkedNode<Integer> tmpNode = resultHead;
        while (!queue.isEmpty()) {
            //找到最小值
            LinkedNode<Integer> minNode = queue.poll();
            if (minNode != null) {
                //指定下一个节点
                tmpNode.setNext(minNode);
                //将当前minNode最小节点从queue剔除了，
                //要找到minNode下一个最小的节点 存放到queue中(注意下次while循环 queue.poll出来的不定义是minNode的next节点，因为要在queue中重新排序)
                if (minNode.getNext() != null) {
                    queue.offer(minNode.getNext());
                }
                //驱动链表形成
                tmpNode = tmpNode.getNext();
            }
        }
        return resultHead;
    }
}
