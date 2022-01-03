package bizfeng.leetcode;

/**
 * 二分查找
 */
public class BinarySearch {
    public static void main(String[] args) {
        int count = 33333;
        int[] array = new int[count];
        for (int i = 0; i < count; i++) {
            array[i] = i;
        }

        for (int i = 0; i < count; i++) {

            int x = find(array, i);

            System.out.println("i=" + i + "在索引位置" + x);
        }

    }

    //假设array是从小到大
    //找不到返回-1
    //1,2,3,4,5,6,7
    private static int find(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        int middle = 0;
        while (left <= right) {
            middle = (left + right) / 2;
            if (array[middle] == target) {
                return middle;
            } else if (array[middle] > target) {
                right = middle - 1;
            } else if (array[middle] < target) {
                left = middle + 1;
            }
        }
        return -1;
    }

}
