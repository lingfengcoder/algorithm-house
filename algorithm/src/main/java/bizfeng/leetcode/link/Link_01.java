package bizfeng.leetcode.link;

import bizfeng.leetcode.base.LinkedNode;
import lombok.extern.slf4j.Slf4j;

/**
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * ################################
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 * ################################
 * 输入：l1 = [], l2 = []
 * 输出：[]
 * ################################
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 */
@Slf4j
public class Link_01 {

    public static void main(String[] args) {
        Integer a=127;
        Integer b=127;
        Integer c=-129;
        Integer d=-129;
        System.out.println(a==b);
        System.out.println(c==d);

        LinkedNode<Integer> link1 = LinkedNode.trans(new Integer[]{1, 2, 4, 9});
        LinkedNode<Integer> link2 = LinkedNode.trans(new Integer[]{1, 3, 5, 7, 8});
        log.info("merge :{} ", mergeTwoLists(link1, link2));
    }

    private static LinkedNode<Integer> mergeTwoLists(LinkedNode<Integer> list1, LinkedNode<Integer> list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        LinkedNode<Integer> head = new LinkedNode<>(), tmp = head;
        LinkedNode<Integer> p1 = list1;
        LinkedNode<Integer> p2 = list2;
        while (p1 != null && p2 != null) {
            if (p1.data > p2.data) {
                tmp.next = (p2);
                p2 = p2.next;
            } else {
                tmp.next = (p1);
                p1 = p1.next;
            }
            tmp = tmp.next;
        }
        //补偿机制
        if (p1 != null) {
            //说明P2遍历完毕了
            tmp.next = (p1);
        }
        if (p2 != null) {
            //说明P1遍历完了
            tmp.next = (p2);
        }
        return head.next;
    }

}
