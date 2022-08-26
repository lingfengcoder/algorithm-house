package bizfeng.leetcode.link;


import bizfeng.leetcode.base.ListNode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 一个很巧妙的算法，找中间位置，可以用两个指针，一快一慢，快的是慢的 2倍速度，这样快的走完，慢的正好在中间
 * *#
 * 给定一个头结点为 head 的非空单链表，返回链表的中间结点。
 * <p>
 * 如果有两个中间结点，则返回第二个中间结点。
 * <p>
 * ####################################################
 * 输入：[1,2,3,4,5]
 * 输出：此列表中的结点 3 (序列化形式：[3,4,5])
 * 返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
 * 注意，我们返回了一个 ListNode 类型的对象 ans，这样：
 * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
 * <p>
 * ################################################
 * 输入：[1,2,3,4,5,6]
 * 输出：此列表中的结点 4 (序列化形式：[4,5,6])
 * 由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
 * <p>
 */
@Slf4j
public class Link_04 {

    public static void main(String[] args) {

        ListNode link1 = ListNode.trans(new int[]{1, 2, 3});
        ListNode[] array = new ListNode[1];
        log.info("{}", middleNode(link1));
    }


    // 1->2->3
    // fast : 1 3
    // slow : 1 2

    // 1->2->3->4
    // fast : 1  3 null
    // slow : 1  2  3
    private static ListNode middleNode(ListNode head) {
        ListNode slow, fast = slow = head;
        while (fast != null) {
            //一倍速
            if (fast.next == null) {
                break;
            }
            slow = slow.next;
            //两倍速
            fast = fast.next.next;
        }
        return slow;
    }


}
