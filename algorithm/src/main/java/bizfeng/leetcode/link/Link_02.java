package bizfeng.leetcode.link;


import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * #### 本题目和 MergeSomeSortedLinkQueue 题目相同 解法不同
 * <p>
 * 给你一个链表数组，每个链表都已经按升序排列。
 * <p>
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * 示例
 * #############################################
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 * ###################################################
 * 输入：lists = []
 * 输出：[]
 * <p>
 * ###################################################
 * 输入：lists = [[]]
 * 输出：[]
 */
@Slf4j
public class Link_02 {

    public static void main(String[] args) {

        // ListNode link1 = ListNode.trans(new Integer[]{1, 4, 5});
        //  ListNode link2 = ListNode.trans(new Integer[]{1, 3, 4});
        //ListNode link3 = ListNode.trans(new Integer[]{2, 6});
        ListNode[] array = new ListNode[1];
        array[0] = new ListNode();
        // array[1] = link2;
        //  array[2] = link3;
        log.info("merge :{} ", mergeSomeLists(array));
        log.info("merge 2 :{} ", mergeSomeLists_02(array));
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    private static ListNode mergeSomeLists_02(ListNode[] ListNodes) {
        if (ListNodes.length == 0) return null;
        //从小到达排序的队列
        PriorityQueue<ListNode> queue = new PriorityQueue<>(ListNodes.length, (a, b) -> a.val - b.val);
        ListNode tmp;
        for (ListNode listNode : ListNodes) {
            if (listNode != null)
                queue.add(listNode);
        }
        ListNode head = new ListNode();
        tmp = head;
        while (!queue.isEmpty()) {
            ListNode poll = queue.poll();
            tmp.next = poll;
            tmp = tmp.next;
            if (poll.next != null) {
                queue.add(poll.next);
            }
        }
        return head.next;
    }

    private static ListNode mergeSomeLists(ListNode[] ListNodes) {
        if (ListNodes.length == 0) {
            return null;
        }

        ListNode head = new ListNode(Integer.MAX_VALUE), tmp = head;
        tmp.next = head;
        //每个链表的访问节点
        ListNode[] tmpArr = new ListNode[ListNodes.length];
        //初始化为头部节点
        for (int i = 0; i < ListNodes.length; i++) {
            tmpArr[i] = ListNodes[i];
        }
        while (true) {
            int minIndex = 0;
            int count = 0;
            //每次都对 N各链表的头部进行比较，找出最小的那个
            for (int i = 0; i < ListNodes.length; i++) {
                if (tmpArr[minIndex] == null) {
                    minIndex = i;
                }
                //避免短的链表先遍历完毕
                if (tmpArr[i] == null) {
                    ++count;
                    continue;
                }

                if (tmpArr[i].val < tmpArr[minIndex].val) {
                    minIndex = i;
                }
            }

            //进行链接
            tmp.next = tmpArr[minIndex];
            // 第minIndex的链表往后移动一个
            if (tmpArr[minIndex] != null) {
                tmpArr[minIndex] = tmpArr[minIndex].next;
            }
            //此时tmp就是这N个链表里的最小值
            //tmp继续往后移动
            tmp = tmp.next;
            if (count == ListNodes.length) {
                break;
            }
        }
        return head.next;
    }

}
