package bizfeng.leetcode.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Setter
@Getter
/**
 * 链表节点
 */
public class LinkedNode<T> {
    public T data;
    public LinkedNode<T> next;
    public LinkedNode<T> prev;

    public LinkedNode() {
    }

    public LinkedNode(T data) {
        this.data = data;
    }


    public static <T> LinkedNode<T> trans(T[] array) {
        LinkedNode<T> head = new LinkedNode<>(), tmp = head;
        for (T t : array) {
            tmp.next = (new LinkedNode<>(t));
            tmp = tmp.next;
        }
        return head.next;
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5};
        LinkedNode<Integer> trans = trans(array);
        System.out.println(trans);
    }


}
