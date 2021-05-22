package base;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMapTest {

    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 100,
            60, TimeUnit.SECONDS, new ArrayBlockingQueue(1000), new ThreadFactory() {
        private  AtomicInteger threadId = new AtomicInteger(0);
        private String THREAD_NAME_PRE = "MY_THREAD:";
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, THREAD_NAME_PRE+System.currentTimeMillis()+":"+threadId.getAndIncrement());
        }
    });

    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put("a", "1");
        Object a = new Object();
        System.out.println(a.hashCode());
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                throw new Error(new Throwable("testError"));
            }
        });

    }

}
