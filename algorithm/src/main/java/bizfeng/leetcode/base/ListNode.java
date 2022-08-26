package bizfeng.leetcode.base;


import lombok.ToString;

/**
 * @Auther: wz
 * @Date: 2022/8/26 21:32
 * @Description:
 */
@ToString
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode trans(int[] array) {
        ListNode head = new ListNode(), tmp = head;
        for (int t : array) {
            tmp.next = (new ListNode(t));
            tmp = tmp.next;
        }
        return head.next;
    }
}
