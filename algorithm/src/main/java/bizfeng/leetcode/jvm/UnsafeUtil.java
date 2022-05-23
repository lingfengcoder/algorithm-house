package bizfeng.leetcode.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @Author: wz
 * @Date: 2022/5/23 13:42
 * @Description:
 */
public class UnsafeUtil {
    public static final boolean SUPPORTS_GET_AND_SET_REF;
    public static final boolean SUPPORTS_GET_AND_ADD_LONG;
    public static final Unsafe UNSAFE;

    static {
        UNSAFE = getUnsafe();
        SUPPORTS_GET_AND_SET_REF = hasGetAndSetSupport();
        SUPPORTS_GET_AND_ADD_LONG = hasGetAndAddLongSupport();
    }

    private static Unsafe getUnsafe() {
        Unsafe instance;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            instance = (Unsafe) field.get((Object) null);
        } catch (Exception var4) {
            try {
                Constructor<Unsafe> c = Unsafe.class.getDeclaredConstructor();
                c.setAccessible(true);
                instance = (Unsafe) c.newInstance();
            } catch (Exception var3) {
                throw new RuntimeException(var3);
            }
        }

        return instance;
    }

    private static boolean hasGetAndSetSupport() {
        try {
            Unsafe.class.getMethod("getAndSetObject", Object.class, Long.TYPE, Object.class);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    private static boolean hasGetAndAddLongSupport() {
        try {
            Unsafe.class.getMethod("getAndAddLong", Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }
}
