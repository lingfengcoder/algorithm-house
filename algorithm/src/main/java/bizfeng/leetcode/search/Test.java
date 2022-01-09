package bizfeng.leetcode.search;

import bizfeng.leetcode.sort.QuickSort;
import bizfeng.leetcode.sort.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Test {
    private static Random random = new Random();

    public static void main(String[] args) {
        List<Integer[]> data = generalData(20000, 2000);

        //test(data, SelectSort.me);//10790
        test(data, BinarySearch.me);//2371
    }

    public static void test(List<Integer[]> data, Search<Integer> search) {
        Date start = new Date();
        int x = 0;
        for (Integer[] array : data) {
            x = array.length / 2;
            search.search(array, array[x]);
        }
        Date end = new Date();
        System.out.println(search.name() + " 耗时:" + (end.getTime() - start.getTime()));
    }

    private static List<Integer[]> generalData(int count, int size) {
        List<Integer[]> data = new ArrayList<Integer[]>();
        for (int i = 0; i < count; i++) {
            Integer[] item = new Integer[size];
            for (int j = 0; j < size; j++) {
                item[j] = random.nextInt(200);
            }
            data.add(item);
        }
        return data;
    }
}
