package JavaReference;

import java.lang.ref.*;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;


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
 *
 *
 * 弱引用
 * 发生GC时  当对象只有弱引用指向时, GC 会回收该对象内存，并把对应的弱引用放入指定的ReferenceQueue中， 方便清除弱引用对象
 * eg: WeakHashMap.Entry extends WeakReference 构造方法调用  super(key, queue);把key指定为WeakReference, 当key对象无强引用后,
 * GC会把该Entry放入指定的ReferenceQueue中.  在WeakHashMap.expungeStaleEntries 方法中清除无用的Entry和Entry.value;
 *
 *
 * 软引用
 * 当对象只被SoftReference 软引用指向时, 当内存不够，发生OutOfMemoryException之前会该对象堆空间释放，并把对应的弱引用放入指定的ReferenceQueue中.
 * 以上两种引用 通常用于 解决缓存OOM 问题， 常见方式： Map<key, SoftReference>   SoftReference<CacheItem>
 * 通过ReferenceQueue<SoftReference> 清除SoftReference无效引用（map中的）
 *
 */
public class ReferenceTest {

    private static volatile boolean isRun = true;

    public static void main(String[] args) throws InterruptedException {


        ReferenceQueue<Object> weakRefQueue = new ReferenceQueue<Object>();
        ReferenceQueue<Object> softRefQueue = new ReferenceQueue<Object>();
        ReferenceQueue<Object> phantomRefQuene = new ReferenceQueue<>();
        //检测各种引用类型队列
        new Thread(() -> {
            while (isRun)
            {

                getFromReferenceQueue(weakRefQueue);
                getFromReferenceQueue(softRefQueue);
                getFromReferenceQueue(phantomRefQuene);

            }
        }).start();

        getReference(10, "WEAK", weakRefQueue);
        getReference(10, "SOFT", softRefQueue);
        getReference(10, "PHANTOM", phantomRefQuene);

        //申请内存使OutOfMemoryError
        byte[] requstMemory;
        try{
            System.out.println("Current memory total: "+ Runtime.getRuntime().totalMemory()/1024/8);

            System.out.println("Allocate new byte[1024*60] after 5 second");
            TimeUnit.SECONDS.sleep(5);
            int freeMemory = (int) Runtime.getRuntime().freeMemory();
            requstMemory = new byte[freeMemory+100];
            System.out.println("new byte["+freeMemory+100+"]");
            System.out.println("Current memory total: "+ Runtime.getRuntime().totalMemory()/1024/8);

            System.out.println("fill in all byte[1024*60]");
            System.out.println("Current memory total: "+ Runtime.getRuntime().totalMemory()/1024/8);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }


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

    static void getFromReferenceQueue(ReferenceQueue referenceQueue){
        Object ref = referenceQueue.poll();
        if (ref != null)
        {
            try
            {
                Field referent = Reference.class.getDeclaredField("referent");
                referent.setAccessible(true);
                Object result = referent.get(ref);

                System.out.println("==============>reference:" + ref.getClass() + "@" + ref.hashCode());
                if(result == null){
                    System.out.println("==============>referent: has bean null, gc will collect:");
                }else{
                    System.out.println("==============>referent: not null.  " + result.getClass() + "@" + result.hashCode());

                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param size 单位 M
     * @param type
     * @param referenceQueue
     */
    static Reference getReference(int size, String type, ReferenceQueue referenceQueue) throws InterruptedException {

        System.out.println("getReference size: "+size+", type:" + type);
        //用reference 保持对refMemory对应类型的引用
        // 如果直接new *Reference(refMemory, referenceQueue) ,会使refMemory无引用指向, 直接变成不可达, 被GC直接回收，
        // 不会放入referenceQueue中
        Reference reference = null;
        byte[] refMemory = new byte[1024*1024*size];
        switch (type){
            case "SOFT":
                reference = new SoftReference(refMemory, referenceQueue);
                break;
            case "WEAK":
                reference = new WeakReference(refMemory, referenceQueue);
                break;
            case "PHANTOM":
                reference = new PhantomReference(refMemory, referenceQueue);
        }
        //清除强引用
        refMemory = null;
        System.out.println("==============>sleep 3 second before System.gc()");
        TimeUnit.SECONDS.sleep(3);
        System.gc();
        System.out.println("==============>called System.gc()");
        System.out.println();
        System.out.println();
        return reference;
    }
}
