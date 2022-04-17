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
}
