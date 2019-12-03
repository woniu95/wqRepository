package testgit.src.JavaReference;

import java.lang.ref.*;


/**
 * 测试java 4种 引用类型
 * 调小堆  为了更容易触发GC
 *
 * -Xms60m
 * -Xmx60m
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=C:\Users\Administrator\Desktop\error_logs
 * -verbose:gc
 * 显示每次垃圾回收事件
 *
 * 常用jvm 参数
 *-Xms
 * 设置堆的初始值，该值必须是1024的倍数并且大于1MB。
 * -Xmx
 * 最大内存大小，该值必须是1024的倍数并且大于2MB。对于服务型应用，-Xms和-Xmx常设置为相同，如果两者设置相同则应用占据的内存不进行动态扩展。
 * -Xmn
 * 设置堆中年轻代(young generation)的初始值和最大值大小。单位是bytes，常用K,M,G (不区分大小写)
 * -Xsssize
 * 设置线程栈的大小。
 * -XX:+HeapDumpOnOutOfMemory
 * 启用该选项，当出现java.lang.OutOfMemoryError异常时，Java的堆信息会dump到当前目录的文件中，文件命名为： java_pid[进程PID].hprof
 */
public class ReferenceTest {
    public static void main(String[] args){
        ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
        ReferenceQueue<Object> refQueue1 = new ReferenceQueue<Object>();
        // 强引用
        String weakObj = new String("weak reference String");
        String softObj = new String("soft reference String");

        //自定义弱引用
        //发生GC时  当对象只有弱引用指向时, GC 会回收该对象内存，并把对应的弱引用放入指定的ReferenceQueue中， 方便清除弱引用对象
        // eg: WeakHashMap.Entry extends WeakReference 构造方法调用  super(key, queue);把key指定为WeakReference, 当key对象无强引用后,
        // GC会把该Entry放入指定的ReferenceQueue中.  在WeakHashMap.expungeStaleEntries 方法中清除无用的Entry和Entry.value;

        WQWeakReference customWeak = new  WQWeakReference(weakObj, refQueue);

        // 软引用
        // 当对象只被SoftReference 软引用指向时, 当内存不够，发生OutOfMemoryException之前会该对象堆空间释放，并把对应的弱引用放入指定的ReferenceQueue中.
        // 暂未测试出符合的结果
        SoftReference soft = new SoftReference(softObj, refQueue1);

        weakObj = null;
        softObj = null;
        System.gc();
        System.out.println("Weak Reference: "+refQueue.poll().toString());
        Object[] requstMemory = new Object[1024*60];
        System.out.println("Soft Reference: "+refQueue1.poll());
    }

    static class  WQWeakReference<K> extends WeakReference<K>{
        private String k = "wq";
        public WQWeakReference(K referent, ReferenceQueue<? super K> q) {
            super(referent, q);
        }

        @Override
        public String toString() {
            return "WQWeakReference{}, string:"+k;
        }
    }
}
