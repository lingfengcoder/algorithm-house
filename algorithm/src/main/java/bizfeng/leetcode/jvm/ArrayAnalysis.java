package bizfeng.leetcode.jvm;

import bizfeng.leetcode.base.BinaryNode;
import cn.hutool.core.util.RandomUtil;
import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: wz
 * @Date: 2022/5/23 13:35
 * @Description:
 */
public class ArrayAnalysis {
    private static String name;//Stream.generate(()->RandomUtil.randomString( 20)).limit(10).collect(Collectors.joining());
    private int age = 20;

    public static void main(String[] args) {
        ccode();

        if (true) {
            return;
        }
        modifyArrayByUnsafe();
    }

    private static void modifyArrayByUnsafe() {
        int[] a = new int[7];
//        Unsafe unsafe = Unsafe.getUnsafe();
        Unsafe unsafe = UnsafeUtil.UNSAFE;
        //第一个元素的偏移地址
        int baseOffset = unsafe.arrayBaseOffset(int[].class);
        //第一个元素的占用大小
        int indexScale = unsafe.arrayIndexScale(int[].class);
        //目标偏移地址
        int offset = baseOffset + 4 * indexScale;
        //根据偏移地址修改数组数据 为666
        unsafe.putOrderedInt(a, offset, 666);
        System.out.println(a[4]);// 666
    }

    private static void ccode() {
        int[] arr = new int[6];
        ArrayAnalysis node = new ArrayAnalysis();
        System.out.println(node.name);
        //分析数组内存分布
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());
        System.out.println("======================================================");
        System.out.println(ClassLayout.parseInstance(ArrayAnalysis.class).toPrintable());
    }
}
