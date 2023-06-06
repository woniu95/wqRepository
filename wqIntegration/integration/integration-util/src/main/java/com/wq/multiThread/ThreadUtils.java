package com.wq.multiThread;

import java.util.List;
import java.util.concurrent.*;

/**
 * @PackageName:com.wq.multiThread
 * @ClassName:ThreadUtils
 * @Description:
 * @author: wq
 * @date 2021/7/18 22:41
 */
public class ThreadUtils {

    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime){
        ThreadFactory threadFactory = r -> new Thread(r);
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.SECONDS, workQueue, threadFactory, handler);

        return threadPoolExecutor;
    }


    public static void doMultiThread(Runnable runnable, Integer threadCount, String desc){
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadCount, threadCount, 0, null, workQueue);
        for(int i=0;i<threadCount;i++){
            threadPoolExecutor.submit(runnable);
        }
    }

    public static void runInConcurrent(List<Runnable> concurrentJobs){
        int concurrentSize = concurrentJobs.size();
        CountDownLatch countDownLatch = new CountDownLatch(concurrentSize);

        ThreadPoolExecutor fixedPool = new ThreadPoolExecutor(concurrentSize, concurrentSize, 0 , null, null);
        for (Runnable concurrentJob : concurrentJobs){
            fixedPool.submit(new ConcurrentJob(concurrentJob, countDownLatch));
        }
    }

    public static class ConcurrentJob implements Runnable{

        private  CountDownLatch countDownLatch;

        private Runnable runnable;

        public ConcurrentJob(Runnable runnable, CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
            this.runnable = runnable;
        }

        @Override
        public void run() {

            try {
                countDownLatch.countDown();
                countDownLatch.await();
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
