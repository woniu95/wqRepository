package src.base;

import java.math.BigInteger;

/**
 *
 * Cache Line effect. Three cache level first and second used for one core of cpu, third can used for core of
 * cpu in same slot. cache read data unit from memory is cache line usually is 64 byte. one line can storage 8
 * long type data. so access successive data is more fast.
 *
 * FalseShare problem caused in multi thread on change different data in same cache line, cpu core will
 * competition the ownership of cache line,and one get then change data will cause this cache line invalidate
 * even other thread are using. Content data for exist in different cache line, 1.6,1.7 use 7 long contention
 * data. 1.8 can ues @Contended on class or field level.
 *
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 17:24
 */
public class CaCheLineEffect {

    public static void main(String[] args) {

        long arr[][] = new long[1024*1024][];
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
        System.out.println("cost time :"+(System.currentTimeMillis()-startTime)+" ms, result is"+sum );

    }



}
