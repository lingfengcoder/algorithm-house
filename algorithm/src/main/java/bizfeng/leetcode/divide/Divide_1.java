package bizfeng.leetcode.divide;

import bizfeng.leetcode.sort.QuickSort;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 分治法
 * <p>
 * 题目：
 * 有 20 个数组，每个数组有 500 个元素。如何在这 20*500 个数中找出前 500 的
 * 数？
 * 采用分治法，将大集合分解成小集合，然后采用大（小）顶堆进行TOP-N的筛选
 */
@Slf4j
public class Divide_1 {

    public static void main(String[] args) {
        int x = 10;
        int num = 0;
        List<Integer[]> data = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            Integer[] item = new Integer[x];
            data.add(item);
            for (int j = 0; j < x; j++) {
                item[j] = num++;//*RandomUtil.randomInt(2,5);
            }
        }
        int k = 20;
        Integer[] result = deal(data, k);
        log.info("从{}个数据中找到前{}的数字={}", data.size() * x, k, result);
    }

    private static Integer[] deal(List<Integer[]> data, int n) {
        sort(data);
        return divide(data, n);
    }

    //使用快速排序 从小到大排序
    private static void sort(List<Integer[]> data) {
        for (Integer[] item : data) {
            QuickSort.me.sort(item);
        }
    }


    static class HeapItem implements Comparable<HeapItem> {
        Integer data;
        int index;
        Integer[] belong;

        public HeapItem(Integer data, int index, Integer[] belong) {
            this.data = data;
            this.index = index;
            this.belong = belong;
        }


        @Override
        public int compareTo(HeapItem o) {
            //return data - o.data; //小顶堆 //每次pull都是最小数
            return o.data - data;//大顶堆  每次pull都是最大数
        }

    }

    //从所有桶中找出最小的N个数据
    //分治:1.桶元素有序 2.
    private static Integer[] divide(List<Integer[]> data, int n) {
        if (n <= 0) return null;
        PriorityQueue<HeapItem> heap = new PriorityQueue<>(n);
        for (Integer[] item : data) {
            //将每个桶的最值放入
            heap.offer(new HeapItem(item[item.length - 1], item.length - 1, item));
        }
        Integer[] result = new Integer[n];
        //找出X个最小的数字
        for (int i = 0; i < n; i++) {
            //获取最大的
            HeapItem item = heap.poll();
            if (item == null) {
                //如果没有了，说明全部遍历完成
                return result;
            }
            result[i] = item.data;
            Integer[] tmp = item.belong;
            if (item.index <= tmp.length - 1 && item.index > 0) {
                item.data = tmp[--item.index];
                heap.add(item);
            }
        }
        return result;
    }
}
