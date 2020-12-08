package thread;

/**
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-12-08 13:47
 */
public class ThreadInterupt {


    private static class TestWorker extends Thread{

        @Override
        public void run() {
            while (true) {
                try {
                    //阻塞的方法接收到 中断信号会抛出InterruptedException，并清除线程中断信息
                    sleep(1000);
                } catch (InterruptedException e) {
                    //恢复被清除的中断信号
                    interrupt();
                }
                if(isInterrupted()){
                    System.out.println("worker is interrupted ");
                    //会清除中断
                    Thread.interrupted();
                }else{

                    System.out.println("running ....");
                }

            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        TestWorker worker = new TestWorker();
        worker.start();
        System.out.println("before interrupt, worker state: "+ worker.getState());


        // 设置中断
        worker.interrupt();
        System.out.println("interrupted ....");
        while(true){

            System.out.println("main wait clean interrupt ...., worker state: "+worker.getState());
            System.out.println();

            // isInterrupted 不会清除中断
            if(!worker.isInterrupted()){
                // 设置中断
                worker.interrupt();
                Thread.currentThread().sleep(2000);
            }

        }
    }
}
