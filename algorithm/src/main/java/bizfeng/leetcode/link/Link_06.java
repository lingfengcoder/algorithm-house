package bizfeng.leetcode.link;


import bizfeng.leetcode.base.ListNode;
import lombok.extern.slf4j.Slf4j;

/**
 * 找到链表的 环交点 元素
 * 思路：快慢指针，再走 K-M步骤
 *
 * <p>
 */
@Slf4j
public class Link_06 {

    public static void main(String[] args) {

        ListNode link1 = ListNode.trans(new int[]{1, 2, 3, 4});
        makeCycle(link1, 2);
        ListNode cycleNode = findLinkCycleNode(link1);
        log.info("找到的环交点是 {}", cycleNode.val);
    }

    private static void makeCycle(ListNode head, int cycleNodeVal) {
        ListNode tmp = head;
        ListNode cycleNode = null;
        while (tmp != null && tmp.next != null) {
            tmp = tmp.next;
            if (tmp.val == cycleNodeVal) {
                cycleNode = tmp;
            }
            if (tmp.next == null) {
                tmp.next = cycleNode;
                break;
            }
        }
    }


    // 1->2->3
    // fast : 1 3
    // slow : 1 2

    // 1->2->3->4
    // fast : 1  3 null
    // slow : 1  2  3
    private static ListNode findLinkCycleNode(ListNode head) {
        ListNode slow, fast = slow = head;
        while (fast != null && fast.next != null) {
            //一倍速
            slow = slow.next;
            //两倍速
            fast = fast.next.next;
            // 快慢指针相遇，说明含有环
            if (slow == fast) {
                break;
            }
        }
        //说明是因为遇到环才跳出循环的
        //如果fast=null 说明没有环
        if (fast != null && fast.next != null) {
            slow = head;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
            return slow;
        }
        return null;
    }


}
