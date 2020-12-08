package algorithm;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * Java PriorityQueue implement heep, used to sort, cost time O(nlgn);
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 17:07
 */
public class Heep {


    public static void main(String[] args) {

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

    }
}


