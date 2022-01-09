package bizfeng.leetcode.sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Test {
    private static Random random = new Random();

    public static void main(String[] args) {
        List<Integer[]> data = generalData(20000, 2000);

        test(data, QuickSort.me);//2371
        test(data, SelectSort.me);//10790
    }

    public static void test(List<Integer[]> data, Sort<Integer> sort) {
        Date start = new Date();
        for (Integer[] array : data) {
            sort.sort(array);
        }
        Date end = new Date();
        System.out.println(sort.name() + " 耗时:" + (end.getTime() - start.getTime()));
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
