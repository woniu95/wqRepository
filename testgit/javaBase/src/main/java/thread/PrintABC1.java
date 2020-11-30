package thread;

/**
 * multi-thread   print A B C  implement with object wait notify
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 17:27
 */
public class PrintABC1 {

    public static void main(String[] args) throws InterruptedException {

         Object a = new Object();
         Object b = new Object();
         Object c = new Object();
         class PrintABC implements Runnable{
            Object self;
            Object pre;
            PrintABC(Object self, Object pre){
                this.self = self;
                this.pre = pre;
            }

            @Override
            public void run() {
                int count = 10;
                while(count>0){
                    synchronized(pre){
                        synchronized (self){
                            System.out.println(Thread.currentThread().getName());
                            self.notify();
                            count--;
                        }
                        try {
                            if(count!=0 ){
                                pre.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        new Thread(new PrintABC(a,c), "A").start();
//        Thread.sleep(3000);
        new Thread(new PrintABC(b,a), "B").start();
        Thread.sleep(3000);
//        new Thread(new PrintABC(c,b), "C").start();
    }
}
