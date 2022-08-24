package bizfeng.leetcode.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
/**
 * 链表节点
 */
public class LinkedNode<T> {
    private T data;
    private LinkedNode<T> next;
    private LinkedNode<T> prev;

    public LinkedNode() {
    }

    public LinkedNode(T data) {
        this.data = data;
    }


    public static <T> LinkedNode<T> trans(T[] array) {
        LinkedNode<T> head = new LinkedNode<>(), tmp = head;
        for (T t : array) {
            tmp.setNext(new LinkedNode<>(t));
            tmp = tmp.getNext();
        }
        return head.getNext();
    }

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5};
        LinkedNode<Integer> trans = trans(array);
        System.out.println(trans);
    }
}
