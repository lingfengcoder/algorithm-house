package bizfeng.leetcode.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK 动态代理
 */
public class JdkDynamicProxy {

    //代理类
    static class ProxyInvokeHandler<T> implements InvocationHandler {


        //真实对象
        private T realTarget;

        public ProxyInvokeHandler<T> setTarget(T target) {
            this.realTarget = target;
            return this;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(" proxy invoke start");
            Object res = method.invoke(realTarget, args);
            System.out.println(" proxy invoke end");
            return res;
        }
    }

    interface DemoInterface {
        void doSomething();

        Class<?> getClazz();

        String showName(String name);
    }

    static class DemoClazz implements DemoInterface {
        @Override
        public void doSomething() {
            System.out.println("this is DemoClazz invoke doSomething()");
        }

        @Override
        public Class<?> getClazz() {
            return this.getClass();
        }

        @Override
        public String showName(String name) {
            return name;
        }

    }

    public static void main(String[] args) {
        ProxyInvokeHandler<DemoClazz> proxy = new ProxyInvokeHandler<>();
        DemoClazz impl = new DemoClazz();
        proxy.setTarget(impl);
        ClassLoader classLoader = impl.getClass().getClassLoader();
        Class<?>[] interfaces = impl.getClass().getInterfaces();

        Object o = Proxy.newProxyInstance(classLoader, interfaces, proxy);
        DemoInterface newProxy = (DemoInterface) o;

        newProxy.doSomething();

        Class<?> clazz = newProxy.getClazz();
        System.out.println(clazz);
        newProxy.showName(" step by step to touch the sky");
        System.out.println();
    }
}
