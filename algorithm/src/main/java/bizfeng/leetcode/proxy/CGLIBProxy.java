package bizfeng.leetcode.proxy;


public class CGLIBProxy {

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


//    static class CGLIBDynamicProxy<T> implements MethodInterceptor {
//
//
//        private T realTarget;
//
//        private CGLIBDynamicProxy<T> setTarget(T t) {
//            this.realTarget = t;
//            return this;
//        }
//
//
//
//        @Override
//        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//            return null;
//        }
//    }

}
