package bizfeng.leetcode;

/**
 * 从数组中找到两数之和的下标
 */
public class FindTwoNumSum {

    public static void main(String[] args) {
        int[] array = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int target = 18;
        int[] result = find(array, target);
        StringBuilder builder = new StringBuilder();
        assert result != null;
        for (int i : result) {
            builder.append(" 找到下标为" + i + " array[" + i + "]=" + array[i]);
        }
        System.out.println(builder.toString());
    }

    /**
     * 解法一：双层循环
     *
     * @param array
     * @param target
     * @return
     */
    private static int[] find(int[] array, int target) {
        //len-1 如果找到最后一个还是没有，其实就不用再找了
        for (int x = 0; x < array.length - 1; x++) {
            //因为x+1之前的数字已经和x进行过比较了，
            // 每个数字都要和其他的所有数字进行比较
            //所以到达数字X的时候就只用比较后面的就OK
            //看边界 1.第一个数字不用和自己比较所以直接+1
            // 2.最后一个数字y=x+1后大于len直接跳出循环
            for (int y = x + 1; y < array.length; y++) {
                if (x == y) {//跳过自己
                    continue;
                }
                if (target - array[x] == array[y]) {
                    return new int[]{x, y};
                }
            }
        }
        return null;
    }

}
