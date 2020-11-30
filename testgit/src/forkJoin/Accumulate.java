package src.forkJoin;

import java.util.concurrent.*;

public class Accumulate {
    public static void main(String[] args){
        Long  start = System.nanoTime();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(0,1600000);
       Future<Integer> result = forkJoinPool.submit(countTask);

        try {
            System.out.println(result.get());
            Long end =  System.nanoTime();
            System.out.println("cost time:"+(end-start)/1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Long  start2 = System.nanoTime();
        Integer sum2 =0;
        for(int i=0;i<=1600000;i++){
         sum2 +=i;
        }
        System.out.println(sum2);
        Long  end2 = System.nanoTime();
        System.out.println("cost time:"+(end2-start2)/1000);
    }
}
class CountTask extends RecursiveTask<Integer> {
    private static int  THREASHOLD = 10000;
    private int start;
    private int end;

    CountTask(int start,int end){
        this.start = start;
        this.end = end;
    }
    @Override
    protected Integer compute() {
        int sum =0;
        if( (end - start)<=THREASHOLD){
            for(int i = start;i<=end;i++){
                sum+=i;
            }
        }else{
            int middle = (end + start)/2;

            CountTask countTaskLeft = new CountTask(start,middle);
            CountTask countTaskRight = new CountTask(middle+1,end);
            countTaskLeft.fork();
            countTaskRight.fork();
            sum = countTaskLeft.join()+countTaskRight.join();
        }
        return sum;
    }
}
