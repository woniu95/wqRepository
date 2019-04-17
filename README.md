SOME LOG


public class Main {

    public static void main(String[] args) throws InterruptedException {
    
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
	//compare  object wait/notify   and  Lock  await/signal   
	/*
	when use notify the syn objec will choose some one wait thread random , maybe the syn obj is used by other thread and other usage. 
	Some wirte by other:
   	 例如 用wait/notify 实现阻塞队列时，当生产者put队列已满，所有的生产者现场阻塞，某个时刻消费者消费了一个元素，则需要唤醒某个生产者线程，而通过 Object notify 方式唤醒的线程不能确保一定就是一个生产者线程，因为 notify 是随机唤醒某一个正在该 synchronized 对应的锁上面通过 wait 方式阻塞的线程，如果这时正好还有消费者线程也在阻塞中，则很可能唤醒的是一个消费者线程；signalAll 更是会唤醒所有在对应锁上通过 wait 方式阻塞的线程，而不管是生产者还是消费者线程。
   	 与之不同的 Condition await, signal 方式则可以对应多个谓词条件（notEmpty, notFull），可以很方便的实现让生产者线程和消费者线程分别在不同的谓词条件上进行等待


	*/	
        }

}


