package thread;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 业务线程池统一管理类
 * @description: 线程池管理类
 * @author: 王强
 * @create: 2021-09-14 14:42
 */
public class TocThreadPoolManager {

    private static Map<BusinessCode, TocThreadPool> pools = new ConcurrentHashMap(16);

    /**
     * 获取业务线程池
     * @param businessCode
     * @return
     */
    public static TocThreadPool getBusinessThreadPool(BusinessCode businessCode){
        if(pools.containsKey(businessCode)){
            return pools.get(businessCode);
        }else{
            return createFlexThreadPool(businessCode);
        }
    }

    /**
     * 获取当前业务线程池，线程数量
     * @param businessCode
     * @return
     */
    public static int getBusinessThreadCount(BusinessCode businessCode){
        if(pools.containsKey(businessCode)){
            return pools.get(businessCode).getPoolSize();
        }else{
            return 0;
        }
    }

    /**
     * 获取当前所有业务线程数量总和
     * @return
     */
    public static synchronized int getCurrentTotalThreadCount(){

        AtomicInteger total = new AtomicInteger();

        pools.forEach((businessCode, tocThreadPool) -> {
            total.addAndGet(tocThreadPool.getPoolSize());
        });

        return total.intValue();
    }
    /**
     * 创建指定业务最大线程的弹性线程池
     * 1.不会拒绝任务
     * 2.线程数超过核心线程数 空闲30秒后释放
     * 3.允许核心线程空闲后释放
     * @param businessCode 线程池业务码
     * @return
     */
    private synchronized static TocThreadPool createFlexThreadPool(BusinessCode businessCode){

        if(pools.containsKey(businessCode)){
            return pools.get(businessCode);
        }
        TocThreadPool threadPool = new TocThreadPool(businessCode, businessCode.maxSize, businessCode.maxSize,
                businessCode.keepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue());

        threadPool.allowCoreThreadTimeOut(true);
        threadPool.setThreadFactory(TocThreadFactory.getTocThreadFactory(threadPool));

        pools.putIfAbsent(businessCode, threadPool);
        return threadPool;
    }

    public enum BusinessCode{

        INIT_TENANT(20, 20, 30, "init_tenant_thread_", "初始化租户");

        /**
         * 核心线程数
         */
        private int coreSize;
        /**
         * 最大线程数
         */
        private int maxSize;
        /**
         * 空闲线程保持多长时间(秒)
         */
        private int keepAliveTime;
        /**
         * 业务线程前缀
         */
        private String threadNamePre;
        /**
         * 描述
         */
        private String desc;

        BusinessCode(int coreSize, int maxSize, int keepAliveTime, String threadNamePre, String desc) {
            this.coreSize = coreSize;
            this.maxSize = maxSize;
            this.threadNamePre = threadNamePre;
            this.keepAliveTime = keepAliveTime;
            this.desc = desc;
        }


    }

    static class TocThreadPool extends ThreadPoolExecutor{

        private BusinessCode businessCode;

        public TocThreadPool(BusinessCode businessCode, int corePoolSize, int maximumPoolSize, long keepAliveTime,
                             TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
            this.businessCode = businessCode;
        }

        public TocThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        public TocThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public TocThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public TocThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        public BusinessCode getBusinessCode() {
            return businessCode;
        }
    }


    private static class TocThreadFactory implements ThreadFactory {

        private static Map<TocThreadPool, TocThreadFactory> factories = new ConcurrentHashMap();

        private final TocThreadPool tocThreadPool;

        static TocThreadFactory  getTocThreadFactory(TocThreadPool tocThreadPool){
            if(factories.containsKey(tocThreadPool)){
                return factories.get(tocThreadPool);
            }else{
                TocThreadFactory tocThreadFactory = new TocThreadFactory(tocThreadPool);
                factories.putIfAbsent(tocThreadPool, tocThreadFactory);
                return factories.get(tocThreadPool);
            }
        }

        private TocThreadFactory(TocThreadPool tocThreadPool){
            this.tocThreadPool = tocThreadPool;
        }

        @Override
        public Thread newThread(Runnable r) {

            String threadName = tocThreadPool.getBusinessCode().threadNamePre + tocThreadPool.getPoolSize();
            Thread thread = new Thread(r, threadName);

            return thread;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TocThreadPoolManager.getBusinessThreadPool(BusinessCode.INIT_TENANT).submit(()->{});

        System.out.println(TocThreadPoolManager.getBusinessThreadCount(BusinessCode.INIT_TENANT));
        TimeUnit.SECONDS.sleep(35);
        System.out.println(TocThreadPoolManager.getBusinessThreadCount(BusinessCode.INIT_TENANT));

        TocThreadPoolManager.getBusinessThreadPool(BusinessCode.INIT_TENANT).submit(()->{});

        System.out.println(TocThreadPoolManager.getBusinessThreadCount(BusinessCode.INIT_TENANT));
    }
}


