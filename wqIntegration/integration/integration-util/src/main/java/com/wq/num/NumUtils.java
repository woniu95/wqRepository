package com.wq.num;

import java.util.Random;

/**
 * @program: crm-cloud
 * @description:
 * @author: 王强
 * @create: 2021-06-21 10:51
 */
public class NumUtils {

    /**
     * 生成指定范围的随机数
     * @param min
     * @param max
     * @return x 属于 [min, max]
     */
    public static int getRandom(int min, int max){

        if(min>max){
            throw new RuntimeException("范围最大值不能小于最小值");
        }else if(min == max){
            return min;
        }
        Random random = new Random();
        int bound = max-min+1;
        int temp = random.nextInt(bound);

        return min + temp;
    }

    /**
     * 生成指定范围的随机数
     * @param min
     * @param max
     * @return x 属于 [min, max)
     */
    public static int getRandomNotContainsRight(int min, int max){

        if(min>max){
            throw new RuntimeException("范围最大值不能小于最小值");
        }else if(min == max){
            return min;
        }
        Random random = new Random();
        int bound = max-min;
        int temp = random.nextInt(bound);

        return min + temp;
    }

    /**
     * 生成指定范围的随机数
     * @param min
     * @param max
     * @return x 属于 (min, max)
     */
    public static int getRandomNotContains(int min, int max){

        if(min >= max-1){
            throw new RuntimeException("范围最大值与最小值之间必须有数");
        }

        Random random = new Random();
        int bound = max-min;
        int temp = random.nextInt(bound);

        temp = temp == 0 ? 1 : temp;

        return min + temp;
    }
    /**
     * 是否在指定范围，包含边界
     * @param target
     * @param start
     * @param end
     * @return
     */
    public static boolean inRange(int target, int start, int end){
        boolean inRange = false;
        if(target>= start && target<= end){
            inRange = true;
        }
        return inRange;
    }

    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            System.out.println(NumUtils.getRandomNotContains(1, 6));
        }
    }
}
