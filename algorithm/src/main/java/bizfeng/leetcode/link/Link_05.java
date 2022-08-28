package bizfeng.leetcode.link;


import bizfeng.leetcode.base.ListNode;
import lombok.extern.slf4j.Slf4j;

/**
 * 一个很巧妙的算法，找中间位置，可以用两个指针，一快一慢，快的是慢的 2倍速度，如果有环，那么两个指针必定相遇
 *
 *
 * <p>
 */
@Slf4j
public class Link_05 {

    public static void main(String[] args) {

        ListNode link1 = ListNode.trans(new int[]{1, 2, 3});
        ListNode[] array = new ListNode[1];
        log.info("{}", linkHasCycle(link1));
    }


    // 1->2->3
    // fast : 1 3
    // slow : 1 2

    // 1->2->3->4
    // fast : 1  3 null
    // slow : 1  2  3
    private static boolean linkHasCycle(ListNode head) {
        ListNode slow, fast = slow = head;
        while (fast != null && fast.next != null) {
            //一倍速
            slow = slow.next;
            //两倍速
            fast = fast.next.next;
            // 快慢指针相遇，说明含有环
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }


}
