package bizfeng.leetcode.search;

public interface Search<T> {
    int search(T[] array, T target);

    String name();
}
