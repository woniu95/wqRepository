package leetcode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
 *
 * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
 *
 * 每个孩子至少分配到 1 个糖果。
 * 相邻的孩子中，评分高的孩子必须获得更多的糖果。
 * 那么这样下来，老师至少需要准备多少颗糖果呢？
 *
 * 示例 1:
 *
 * 输入: [1,0,2]
 * 输出: 5
 * 解释: 你可以分别给这三个孩子分发 2、1、2 颗糖果。
 * 示例 2:
 *
 * 输入: [1,2,2]
 * 输出: 4
 * 解释: 你可以分别给这三个孩子分发 1、2、1 颗糖果。
 *      第三个孩子只得到 1 颗糖果，这已满足上述两个条件。
 *   2  1 2  1
 * 【5，1，5，2】
 *
 * 【5，6，3，3，1，2，8】
 *
 * //need Add: 0 1 2
 * //          3 2 1 1 2 1 1
 * //          3,2,1,1,4,3,3
 * //2 2 1
 */
public class 分发糖果135 {


    public static void main(String[] args) {
//                 1 2 3  1  2  2 1
//                 1 2 3  1  2  2 1
//                 3 2 1 1 2 1 1
//                 1 2 3 4 1 1 1 1
        int[] p = {1,2,3,5,4,3,2,1};
        System.out.println(candy(p));
    }

   static public int candy(int... ratings) {
        int length = ratings.length;

        int index = 1;
        int sum =1;
        int addCount = 0;
        int befCount = 1;
        int updateAdd = 0;
        int lastAscAount = 1;
        boolean needUpdateAdd;
        boolean beforeAdd = false;
        while(index<length){

            needUpdateAdd = false;

            if(ratings[index-1]<ratings[index]){

                if(lastAscAount != 0){
                    lastAscAount++;
                    beforeAdd = true;
                }
                addCount = befCount+1;

            }else if(ratings[index-1] == ratings[index]){
                addCount = 1;
                beforeAdd = false;
                lastAscAount=0;
            }else if(ratings[index-1] > ratings[index]){

                if(befCount>1){
                    addCount = 1;
                }else{
                    needUpdateAdd = true;
                    updateAdd += 1;
                    addCount = 1;
                }
                if(lastAscAount==0 && beforeAdd){
                    updateAdd += 1;
                    beforeAdd = false;
                }
                lastAscAount--;
            }
            System.out.println("lastAscAount: "+lastAscAount+" index:"+index+" needUpdate: "+updateAdd);
            sum+=addCount;
            if(needUpdateAdd){
                sum+= updateAdd;
//                System.out.println("lastAscAount: "+lastAscAount+" index:"+index+" needUpdate: "+updateAdd);
            }
            befCount = addCount;
            index++;
        }

        return sum;
    }


    /**
     *暴力法 超出时间
     * @param ratings
     * @return
     */
    public int candyUseBrute(int[] ratings) {
        int length = ratings.length;
        if(length==0){
            return 0;
        }
        ArrayList<Integer> candyCount =  new ArrayList(length);
        candyCount.add(1);

        int index = 1;
        int temCount = 0;
        while(index<length){

            if(ratings[index-1]<ratings[index]){
                temCount = candyCount.get(index-1)+1;

            }else if(ratings[index-1] == ratings[index]){
                temCount = 1;
            }else if(ratings[index-1] > ratings[index]){
                if(candyCount.get(index-1)>1){
                    temCount = 1;
                }else{
                    doUpdateBefore(index-1,candyCount, ratings);
                    temCount = 1;
                }
            }
            candyCount.add(temCount);
            index++;
        }

        int minCandy=0;
        for(Integer count : candyCount){
            minCandy+=count;
        }
        return minCandy;
    }

    private void doUpdateBefore(int i, List<Integer> candyCount, int[] ratings) {
        candyCount.set(i, candyCount.get(i)+1);
        while(i-1>=0 && ratings[i]<ratings[i-1]){
            if(candyCount.get(i)>=candyCount.get(i-1)){
                candyCount.set(i-1, candyCount.get(i-1)+1);
            }
            i--;
        }
    }

}
