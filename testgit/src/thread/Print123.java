package src.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * multi-thread a print 123 -> b print 456 -> a prent 789  implement with Lock Condition
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 17:28
 */
public class Print123 {
    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        Condition reach3 = lock.newCondition();
        Condition reach6 = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Thread in "+Thread.currentThread().getName()+" print 123");
                reach3.signal();
                System.out.println("Thread in "+Thread.currentThread().getName()+" await");
                reach6.await();
                System.out.println("Thread in "+Thread.currentThread().getName()+" print 789");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"A").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Thread in "+Thread.currentThread().getName()+"await");
                reach3.await();
                System.out.println("Thread in "+Thread.currentThread().getName()+" print 456");
                reach6.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"B").start();




    }
}
