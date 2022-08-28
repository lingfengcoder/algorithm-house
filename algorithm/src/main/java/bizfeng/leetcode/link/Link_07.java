package bizfeng.leetcode.link;


import bizfeng.leetcode.base.LinkedNode;
import bizfeng.leetcode.base.ListNode;
import lombok.extern.slf4j.Slf4j;

/**
 * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
 * <p>
 * 图示两个链表在节点 3 开始相交：
 * #
 * # 1->2
 * #      \
 * #        3->4->5
 * #     /
 * # 8->9
 * ###########################################
 * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
 * 输出：Intersected at '8'
 * <p>
 * ############################################
 * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * 输出：null
 * <p>
 * <p>
 * 思路
 * //相交点
 * //如果两个链表长度一样长，且有交集，那么直接遍历就可以找出相交点；
 * // 实际不清楚链表长度是否相同，则利用 两个链表虚拟链接的方式，补齐两个链表的差值
 * // linkA: 1-2-3-4-5
 * // linkB: 8-9-4-5
 * //链接过程
 * // 1-2-3-4-5->8-9- 4-5
 * // 8-9-4-5->1-2-3- 4-5
 */
@Slf4j
public class Link_07 {

    public static void main(String[] args) {

        ListNode link1 = ListNode.trans(new int[]{1, 2, 3, 4, 5});
        ListNode link2 = ListNode.trans(new int[]{8, 9});
        appendTargetLink(link2, link1, 4);

        log.info("找到的环交点是 {}", getIntersectionNode(link1, link2).val);
    }

    private static void appendTargetLink(ListNode link, ListNode target, int val) {
        ListNode tmp = target;
        while (tmp.val != val) {
            tmp = tmp.next;
        }
        link.next = tmp;
    }

    //相交点
    //如果两个链表长度一样长，且有交集，那么直接遍历就可以找出相交点；
    // 实际不清楚链表长度是否相同，则利用 两个链表虚拟链接的方式，补齐两个链表的差值
    // linkA: 1-2-3-4-5
    // linkB: 8-9-4-5
    //链接过程
    // 1-2-3-4-5->8-9- 4-5
    // 8-9-4-5->1-2-3- 4-5
    private static ListNode getIntersectionNode(ListNode head1, ListNode head2) {
        ListNode t1 = head1, t2 = head2;
        while (true) {
            if (t1 == t2) {
                return t1;
            }
            if (t1 == null) {
                t1 = head2;
            } else {
                t1 = t1.next;
            }
            if (t2 == null) {
                t2 = head1;
            } else {
                t2 = t2.next;
            }
        }
    }


}
