package designPatterns.singleton;

/**
 * 单例模式：
 * 这种模式涉及到一个单一的类，该类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，
 * 可以直接访问，不需要实例化该类的对象。
 * @PackageName:designPatterns.singleton
 * @ClassName:Singleten
 * @Description:
 * @author: wq
 * @date 2022/9/5 20:11
 */
public class Singleten {
    public static class A{
        private A(){}
        // 饿汉式
        private static final A a = new A();
        public static A getInstanceA(){
            return a;
        }
        // 懒汉式
        private static volatile A b;
        public static synchronized A  getInstanceB(){
            if(b == null){
                b = new A();
            }
            return b;
        }
        // 懒汉式 双重检查锁  volatile 禁止指令重排序
        private static volatile A c;
        public static A getInstanceC(){
            if(c == null){
                synchronized(A.class){
                    if(c == null){
                        c = new A();
                    }
                }
            }
            return b;
        }
    }
}
