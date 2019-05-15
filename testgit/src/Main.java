package src;

import java.io.*;
import java.math.BigInteger;
import java.util.EventListener;
import java.util.EventObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main{

    public static void main(String[] args) throws InterruptedException {
        // write your code here
     /*   Map hashMap = new HashMap<>();
        hashMap.put("a","a");
        Set entrySet = hashMap.entrySet();
        Iterator i = entrySet.iterator();

        while(i.hasNext()){


            System.out.print(entrySet);
            entrySet.remove("a");

            System.out.print(entrySet);
            entrySet.add(new Object());
            i.next();
        }
        System.out.print(entrySet);*/

//iterator fail-fast  test

   /*    List list = new LinkedList();
       Iterator i = list.iterator();
       list.add("aaaa");
       i.next();
       System.out.println(list);

       new Thread(new Runnable(){
            @Override
            public void run() {
                list.add("aaaa");
            }
        }).start();
       try{
           Thread.currentThread().sleep(3000L);
       }catch (Exception e){

       }

       i.next();
        System.out.println(list);
     */
        //bit operation  按 二进制源码 -> 补码计算 -> 结果补码 -> 二进制源码
        //type extend byte -> char->int  左补符号位 char无符号 类型拓展时 补0
        //需要注意的是java中的移位操作会模除位数  如 long 类型  :   1L <<65  => 1L << (65 % 64(long 8 Byte)) => 1L << 1 = 2
      /*  byte aByte = new Byte((byte) -1);
        System.out.println("byte -1 -> int = "+(int)aByte);
        System.out.println("byte -1 -> char -> int = "+(int)(char)aByte);
        System.out.println("byte:127+1 = "+(byte)(aByte+(byte)1));*/

        //serialize mechanism . Custom Serialize : override writeObject and readObject method, LinkedList Has override those method.
      /*  LinkedList list = new LinkedList();
        list.push("firstIn");
        list.push("secondIn");
        list.push("thirdIn");
        list.pop();
        System.out.println(list);
        try{
            ObjectOutputStream  objectOutputStream = new ObjectOutputStream(new FileOutputStream("./list.object"));
            list.pop();
            objectOutputStream.writeObject(list);

            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("./list.object"));
            LinkedList serialyzedList = (LinkedList)objectInputStream.readObject();

            System.out.println(serialyzedList);
            objectInputStream.close();
            objectOutputStream.close();
        }catch(Exception e){

        }*/
        //观察者模式 被观察者继承: ObserverAble 观察者继承: Observer   升级版  事件模型
 /*        class Teacher extends Observable {

        }
        class Student implements Observer {

            @Override
            public void update(Observable o, Object arg) {

            }
        }*/
       // multi-thread   print A B C  implement with object wait notify
       /*  Object a = new Object();
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
        Thread.sleep(3000);
        new Thread(new PrintABC(b,a), "B").start();
        Thread.sleep(3000);
        new Thread(new PrintABC(c,b), "C").start();
        */

        //multi-thread a print 123 -> b print 456 -> a prent 789  implement with Lock Condition
      /*  ReentrantLock lock = new ReentrantLock();
        Condition reach3 = lock.newCondition();
        Condition reach6 = lock.newCondition();
        new Thread(new Runnable() {
            @Override
            public void run() {
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
            }
        },"B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
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
            }
        },"A").start();*/
       //Java PriorityQueue implement heep, used to sort, cost time O(nlgn);
        /*
        PriorityQueue  priorityQueue = new PriorityQueue();
            for(int i=0;i<50;){
                Random random = new Random();
                int tem = random.nextInt(10000);
                System.out.print(tem+", ");
                priorityQueue.add(tem);
                i++;
                if(i%10 == 0){
                    System.out.println();
                }
            }
        System.out.println("After heap sort: ");
            for(int i = 0 ;i<50;){
                System.out.print( priorityQueue.poll()+", ");
                i++;
                if(i%10 == 0){
                    System.out.println();
                }
            }
        */
        //Instrumentation can be used for change class-code , getObjectSize , get jvm run object info.
        //run  Instrumentation jvm init need agent jar. jvm param add : -javaagent:D:\\instrumemtationTest-1.0-SNAPSHOT.jar
        //agent class see InstrumemtationGetObjectShallowSizeAgent.java
        // other way is
         /*  System.out.println("Object size: "+ InstrumemtationGetObjectShallowSizeAgent.sizeOf(new Object()));*/


       //Unsafe can do some like operate memory, CAS(used by Atomic* class to implement atomic operate),...

        //CAS implement Atomic
        /*class IntAtomic{
            private int value;
            public int getAndIncrease(){
                final Unsafe unsafe = Unsafe.getUnsafe();
                long valueOffset = 0;
                try {
                    valueOffset = unsafe.objectFieldOffset(Main.class.getDeclaredField("value"));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                int old;
                int newint;
                do{
                    old = this.value;
                    newint = old + 1;
                } while(unsafe.compareAndSwapInt(this,valueOffset,old,newint));
                return newint;
            }
        }
        IntAtomic main = new IntAtomic();
        main.getAndIncrease();*/
        //Cache Line effect. Three cache level first and second used for one core of cpu, third can used for core of
        // cpu in same slot. cache read data unit from memory is cache line usually is 64 byte. one line can storage 8
        // long type data. so access successive data is more fast.

        //FalseShare problem caused in multi thread on change different data in same cache line, cpu core will
        // competition the ownership of cache line,and one get then change data will cause this cache line invalidate
        // even other thread are using. Content data for exist in different cache line, 1.6,1.7 use 7 long contention
        // data. 1.8 can ues @Contended on class or field level.
       /* long arr[][] = new long[1024*1024][];
        BigInteger sum = BigInteger.ZERO;
        for(int i=0; i<1024*1024; i++){
            arr[i] = new long[8];
            for(int j = 0;j<8;j++){
                arr[i][j] = i;
            }
        }
        System.out.println("successive data access ");
        long startTime = System.currentTimeMillis();
        for(int i=0; i<1024*1024; i++){
            for(int j = 0;j<8;j++){
                //sum = sum.add(BigInteger.valueOf(arr[i][j]));
                long c = arr[i][j];
            }
        }
        System.out.println("cost time :"+(System.currentTimeMillis()-startTime)+" ms, result is:"+sum );

        sum = BigInteger.ZERO;

        System.out.println("not successive data access ");
        startTime = System.currentTimeMillis();
        for(int j = 0;j<8;j++){
            for(int i=0; i<1024*1024; i++){
                // sum = sum.add(BigInteger.valueOf(arr[i][j]));
               long c = arr[i][j];
            }
        }
        System.out.println("cost time :"+(System.currentTimeMillis()-startTime)+" ms, result is"+sum );*/
        //
        ExecutorService excutorService = Executors.newFixedThreadPool(50);

    }


}


//communicate main thread to sub thread implement by Pipe
class Piped {
    public static void main(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
// 将输出流和输入流进行连接，否则在使用时会抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        BufferedReader filein = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\wqdaily\\test.txt"),"utf-8"));

        char[] cbuffer = new char[1024];

        int receive = 0;
        try {
            while ((receive = filein.read(cbuffer)) != -1) {
                out.write(cbuffer,0,receive);
                out.write("----------------one write one write -------------");
            }
        } finally {
            out.close();
        }
    }
    static class Print implements Runnable {
        private PipedReader in;
        public Print(PipedReader in) {
            this.in = in;
        }
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException ex) {
            }
        }
    }
}


