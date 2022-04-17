package bizfeng.leetcode.array;

import java.util.Random;
import java.util.Stack;

/**
 * @Author: wz
 * @Date: 2022/2/26 17:17
 * @Description: [特殊数据结构之单调队列]  寻找 随机数组中 下一个最大数的值和位置  利用 反向入栈stack 正向出栈 实现 O(N)时间复杂度
 */
public class FindNextBiggerInArray {

    public static void main(String[] args) {
        int x = 1000000;
        Random random = new Random();
        Integer[] data = new Integer[x];
        for (int i = 0; i < x; i++) {
            data[i] = random.nextInt(100);
        }
        long start = System.currentTimeMillis();


        Integer[] nge = nextGreaterElement(data);
        System.out.println(nge);
        System.out.println("nextGreaterElement 耗时：" + (System.currentTimeMillis() - start));


        start = System.currentTimeMillis();
        Integer[] normal = normal(data);
        System.out.println("normal 耗时：" + (System.currentTimeMillis() - start));


        test();
    }

    private static void test() {
        // 7 8 1 4 3 5  9  4  2 1 2 4
        // 8 9 4 5 5 9 -1 -1  4 2 4 -1
        Integer[] meta = new Integer[]{7, 8, 1, 4, 3, 5, 9, 4, 2, 1, 2, 4};

        Integer[] result = normal(meta);
        System.out.println(result);
        //测试下一个大数集合
        result = nextGreaterElement(meta);
        System.out.println(result);

        //  测试距离下一个大数的差距
        result = lookForHighWeather(meta);
        System.out.println(result);
        // [2,1,2,4,3]，返回数组 [4,2,4,-1,4]。
        meta = new Integer[]{2, 1, 2, 4, 3};

        // [2,1,2,4,3]，返回数组 [4,2,4,-1,4]。
        meta = new Integer[]{2, 1, 3, 4, 3};
        //测试环形数组的下一个大数
        result = recycleArrayNextBigger(meta);
        System.out.println(result);
    }


    //常规做法 时间复杂度 O(N^2)
    private static Integer[] normal(Integer[] data) {
        Integer[] result = new Integer[data.length]; // 存放答案的数组
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] > data[i]) {
                    result[i] = data[j];
                    break;
                }
            }
            if (result[i] == null) {
                result[i] = -1;
            }
        }
        return result;
    }


    //题目：
    //给⼀个数组 nums，请你返回⼀个等⻓的结果数组，结果数组中对应索引存储着下⼀个更⼤元素，如果没有 更⼤的元素，就存 -1。
    //例如  ，输⼊⼀个数组 nums = [2,1,2,4,3]，你返回数组 [4,2,4,-1,-1]。

    //解析：
    // 这个算法的时间复杂度不是那么直观，如果你看到 for 循环嵌套 while 循环，可能认为这个算法的复杂度也是
    //O(n^2)，但是实际上这个算法的复杂度只有 O(n)。 分析它的时间复杂度，要从整体来看：总共有 n 个元素，每个元素都被 push ⼊栈了⼀次，⽽最多会被 pop
    //⼀次，没有任何冗余操作。所以总的计算规模是和元素规模 n 成正⽐的，也就是 O(n) 的复杂度
    private static Integer[] nextGreaterElement(Integer[] nums) {
        Integer[] result = new Integer[nums.length]; // 存放答案的数组
        //存放身高记录，用于寻找比自己高的第一个人
        Stack<Integer> stack = new Stack<>();
        // 倒着往栈⾥放
        for (int i = nums.length - 1; i >= 0; i--) {
            // 判定个⼦⾼矮
            while (!stack.empty() && stack.peek() <= nums[i]) {
                // 矮个起开，反正也被挡着了。。。
                stack.pop();
            }
            // nums[i] 身后的 next great number
            result[i] = (stack.empty() ? -1 : stack.peek());
            //继续存入
            stack.push(nums[i]);
        }
        return result;
    }

    //天气预报
    //「每⽇温度」
    // 给你⼀个数组 T，这个数组存放的是近⼏天的天⽓⽓温，你返回⼀个等⻓的数组，
    // 计算：对于每⼀天，你还 要⾄少等多少天才能等到⼀个更暖和的⽓温；如果等不到那⼀天，填 0

    //7, 8, 1, 4, 3, 5, 9, 4, 2, 1, 2, 4
    //1  5  1  2  1  1  0  0  3  1  1 0
    private static Integer[] lookForHighWeather(Integer[] data) {
        Integer[] result = new Integer[data.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = data.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && data[stack.peek()] <= data[i]) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? 0 : (stack.peek() - i);
            stack.add(i);
        }
        return result;
    }


    //⼒扣第 503 题「下⼀个更⼤元 素 II」就是
    // 这个问题： ⽐如输⼊⼀个数组 [2,1,2,4,3]，你返回数组 [4,2,4,-1,4]。
    // 拥有了环形属性，最后⼀个元素 3 绕了⼀ 圈后找到了⽐⾃⼰⼤的元素 4。
    private static Integer[] recycleArrayNextBigger(Integer[] data) {
        int len = data.length;
        Integer[] result = new Integer[len];
        //栈内内次循环完毕一定保留最大的值，所以在i%len后再次对最后一位进行循环的时候，
        // 必然会与前一轮留下的最大值进行比较，此时就可以得到最后一位的环形模式下的最大值
        Stack<Integer> stack = new Stack<>();
        //把[2,1,2,4,3] 虚拟成 [2,1,2,4,3,  2,1,2,4,3]
        for (int i = 2 * len - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= data[i % len]) {
                stack.pop();
            }
            result[i % len] = stack.isEmpty() ? -1 : stack.peek();
            stack.add(data[i % len]);
        }
        return result;
    }


    //将数组arr变为环形数组(首尾相接)     1,2,3,4 ->  1,2,3,4,1,2,3,4
    private static Integer[] recycleArray(Integer[] arr) {
        int len = arr.length;
        Integer[] result = new Integer[2 * len];
        for (int i = 0; i < 2 * len; i++) {
            result[i] = arr[i % len];
        }
        return result;
    }

    private static void testD() {
        int x = 20;
        int mod = 5;
        for (int i = 0; i < x; i++) {
            System.out.print(i % mod + " ");
        }
    }
}
