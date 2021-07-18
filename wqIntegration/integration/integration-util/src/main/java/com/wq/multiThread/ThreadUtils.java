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

    public static void runInConcurrent(List<ConcurrentJob> concurrentJobs, ThreadPoolExecutor threadPoolExecutor){
        CountDownLatch countDownLatch = new CountDownLatch(concurrentJobs.size());
        for (ConcurrentJob concurrentJob : concurrentJobs){
            concurrentJob.setCountDownLatch(countDownLatch);
            threadPoolExecutor.submit(concurrentJob);
        }
    }

    public static class ConcurrentJob implements Runnable{

        private  CountDownLatch countDownLatch;

        private ConcurrentTask concurrentTask;

        public ConcurrentJob(ConcurrentTask concurrentTask) {
            this.concurrentTask = concurrentTask;
        }

        public void setCountDownLatch(final CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {

            try {
                countDownLatch.countDown();
                countDownLatch.await();
                concurrentTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public  interface ConcurrentTask{
        void execute();
    }
}
