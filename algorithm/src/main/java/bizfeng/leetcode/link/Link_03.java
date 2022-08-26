package bizfeng.leetcode.link;


import bizfeng.leetcode.base.ListNode;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.util.PriorityQueue;

/**
 * 很巧妙的算法，根绝 倒数N个节点 也就是 正数第 K-N+1 个节点，然后利用双指针技巧找到第 K-N+1即可
 * <p>
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * #################################
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 * #################################
 * 输入：head = [1], n = 1
 * 输出：[]
 * #################################
 * 输入：head = [1,2], n = 1
 * 输出：[1]
 */
@Slf4j
public class Link_03 {

    public static void main(String[] args) {

        ListNode link1 = ListNode.trans(new int[]{1, 2, 3});
        ListNode[] array = new ListNode[1];
        log.info("{}", removeNthFromEnd(link1, 3));
    }

    //tmp -> 1->2->3->NULL
    //删除倒数第3个 也就是1 findNthFromEnd_2返回 tmp的引用
    //然后删除1
    //tmp 不作为数据节点，返回值为tmp.next

    private static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode tmp = new ListNode();
        tmp.next = head;
        //找到第N节点的上一个节点 因为是倒数，所以 n+1 就是第n节点的上一个节点
        ListNode nthNode = findNthFromEnd_2(tmp, n + 1);
        //执行删除
        nthNode.next = nthNode.next.next;
        return tmp.next;
    }

    private static ListNode findNthFromEnd_2(ListNode head, int n) {
        ListNode fast, slow;
        slow = fast = head;
        //fast先走N步
        while (n > 0 && fast != null) {
            fast = fast.next;
            --n;
        }
        //slow开始走,fast==null是结束条件，此时fast指针指向最后一个节点的下一个节点NULL,那么slow节点就走了 (k-n)+1步
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    //找到倒数第N个节点
    //考虑N>链表长度的算法
    private static ListNode findNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return head;
        }
        ListNode slow, fast;
        //快指针向前走N步
        slow = fast = head;
        for (int i = 0; i < n; i++) {
            //如果此处fast已经为null了，此时N!=0 说明N远远大于 链表的长度
            if (fast == null) {
                return null;
            }
            fast = fast.next;
            --n;
        }
        //fast 为空说明 链表全部遍历完毕
        if (fast == null) {
            return slow;
        }
        //开始走slow
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow.next;
    }


}
