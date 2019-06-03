package src;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
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
        // float/double  precise problem , use BigDecimal String constructor method.
       /* BigDecimal sumDeductAmount = new BigDecimal(0);
        sumDeductAmount =  sumDeductAmount.add(new BigDecimal("0.06")).add(new BigDecimal("0.06")).add(new BigDecimal("0.24"));
        float sum = 0.06f+0.06f+0.24f;
        float a = sum;
        System.out.println(sumDeductAmount.floatValue());
        System.out.println(sum);
        System.out.println(sumDeductAmount.doubleValue());*/

    }


}


//communicate main thread to sub thread implement by Pipe
//class Piped {
//    public static void main(String[] args) throws Exception {
//        PipedWriter out = new PipedWriter();
//        PipedReader in = new PipedReader();
//// 将输出流和输入流进行连接，否则在使用时会抛出IOException
//        out.connect(in);
//        Thread printThread = new Thread(new Print(in), "PrintThread");
//        printThread.start();
//        BufferedReader filein = new BufferedReader(new InputStreamReader(new FileInputStream("E:\\wqdaily\\codespaces\\testgit\\test.txt"),"utf-8"));
//
//        char[] cbuffer = new char[1024];
//
//        int receive = 0;
//        try {
//            while ((receive = filein.read(cbuffer)) != -1) {
//                out.write(cbuffer,0,receive);
//                out.write("----------------one write one write -------------");
//            }
//        } finally {
//            out.close();
//        }
//    }
//    static class Print implements Runnable {
//        private PipedReader in;
//        public Print(PipedReader in) {
//            this.in = in;
//        }
//        public void run() {
//            int receive = 0;
//            try {
//                while ((receive = in.read()) != -1) {
//                    System.out.print((char) receive);
//                }
//            } catch (IOException ex) {
//            }
//        }
//    }
//}

//Thread Pool
 interface ThreadPool<Job extends Runnable> {
    // 执行一个Job，这个Job需要实现Runnable
    void execute(Job job);
    // 关闭线程池
    void shutdown();
    // 增加工作者线程
    void addWorkers(int num);
    // 减少工作者线程
    void removeWorker(int num);
    // 得到正在等待执行的任务数量
    int getJobSize();
}
 class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    // 线程池最大限制数
    private static final int MAX_WORKER_NUMBERS = 10;
    // 线程池默认的数量
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    // 线程池最小的数量
    private static final int MIN_WORKER_NUMBERS = 1;
    // 这是一个工作列表，将会向里面插入工作
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    // 工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new
            ArrayList<Worker>());
    // 工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    // 线程编号生成
    private AtomicLong threadNum = new AtomicLong();
    public DefaultThreadPool() {
        initializeWokers(DEFAULT_WORKER_NUMBERS);
    }
    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
        initializeWokers(workerNum);
    }
    public void execute(Job job) {
        if (job != null) {
// 添加一个工作，然后进行通知
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }
    public void addWorkers(int num) {
        synchronized (jobs) {
// 限制新增的Worker数量不能超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWokers(num);
            this.workerNum += num;
        }
    }
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("beyond workNum");
            }
// 按照给定的数量停止Worker
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }
    public int getJobSize() {
        return jobs.size();
    }
    // 初始化线程工作者
    private void initializeWokers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.
                    incrementAndGet());
            thread.start();
        }
    }
    // 工作者，负责消费任务
    class Worker implements Runnable {
        // 是否工作
        private volatile boolean running = true;
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
// 如果工作者列表是空的，那么就wait
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException ex) {
// 感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
// 取出一个Job
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception ex) {
// 忽略Job执行中的Exception
                    }
                }
            }
        }
        public void shutdown() {
            running = false;
        }
    }
}

// SimpleHttpServer use
class SimpleHttpServer {
    public static void main(String[] args) throws Exception {
        SimpleHttpServer.setBasePath("E:\\wqdaily\\codespaces\\testgit");
        SimpleHttpServer.start();
    }
    // 处理HttpRequest的线程池
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool
            <HttpRequestHandler>(5);
    // SimpleHttpServer的根路径
    static String basePath;
    static ServerSocket serverSocket;
    // 服务监听端口
    static int port = 8080;
    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }
    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).
                isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }
    // 启动SimpleHttpServer
    public static void start() throws Exception {

        serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
// 接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池执行
            threadPool.execute(new HttpRequestHandler(socket));
            System.out.println("accept");
        }
        serverSocket.close();
    }
    static class HttpRequestHandler implements Runnable {
        private Socket socket;
        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            String line = null;
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
// 由相对路径计算出绝对路径
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
// 如果请求资源的后缀为jpg或者ico，则读取资源并输出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");
                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    br = new BufferedReader(new InputStreamReader(new
                            FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: text/html; charset=UTF-8");
                    out.println("");
                    while ((line = br.readLine()) != null) {
                        out.println(line);
                    }
                }
                out.flush();
            } catch (Exception ex) {
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {
                close(br, in, reader, out, socket);
            }
        }
    }
    // 关闭流或者Socket
    private static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                try {
                    closeable.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}

