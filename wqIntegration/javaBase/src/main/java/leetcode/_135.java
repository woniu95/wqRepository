package leetcode;

/**
 * @program: wqIntegration
 * @description: 老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
 * 你需要按照以下要求，帮助老师给这些孩子分发糖果：  每个孩子至少分配到 1 个糖果。 相邻的孩子中，评分高的孩子必须获得更多的糖果。
 * 那么这样下来，老师至少需要准备多少颗糖果呢？
 * 示例 1:  输入: [1,0,2] 输出: 5 解释: 你可以分别给这三个孩子分发 2、1、2 颗糖果。
 * 示例 2:  输入: [1,2,2] 输出: 4 解释: 你可以分别给这三个孩子分发 1、2、1 颗糖果。
 * 第三个孩子只得到 1 颗糖果，这已满足上述两个条件。
 * @author: 王强
 * @create: 2020-12-24 12:29
 */
public class _135 {


    public static void main(String[] args) {
//        [1,2,3,5,4,3,2,1]
        //           1 2 3 6 5 4 3 2  1  3  2  1  [1,0,2]
//        int[] p = {1,2,3,5,4,3,2,1, 0, 4, 3, 1};  1,0,2 ; 1,6,10,8,7,3,2;18  3,2,1,1,4,3,3;11  1,3,4,5,2;11  1,2,87,87,87,2,1;  1,2,2;4
//           2 1 3 2 1
//          [4,3,3,2,1]
//                 2 1 2 1
        int[] p = {4,3,3,2,1};
        System.out.println(candy(p));
    }
   static public int candy(int[] ratings) {
        int length = ratings.length;
        if(length == 0){
            return 0;
        }
        int index = 1;
        int sum =1;
        int addCount = 0;
        int befCount = 1;
        int updateAdd = 0;
        int lastAscAount = 0;
        boolean needUpdateAdd;
        boolean beforeAdd = false;
        while(index<length){

            needUpdateAdd = false;

//            System.out.println("index :"+index+", beforeAdd:"+beforeAdd+", updateAdd:" +updateAdd+", lastAscAount:"+lastAscAount);
            if(ratings[index-1]<ratings[index]){

                beforeAdd = true;
                addCount = befCount+1;
                lastAscAount = addCount-1;
                updateAdd = 0;

            }else if(ratings[index-1] == ratings[index]){
                addCount = 1;
                beforeAdd = false;
                lastAscAount = 0;
                updateAdd=0;

            }else if(ratings[index-1] > ratings[index]){

                if(befCount == 1){
                    needUpdateAdd = true;
                    updateAdd += 1;
                }

                if(lastAscAount == updateAdd && beforeAdd){
                    updateAdd += 1;
                    beforeAdd = false;
                    lastAscAount = 0;
                }
                addCount = 1;
            }
//            System.out.println("index :"+index+", beforeAdd:"+beforeAdd+", needUpdateAdd :"+needUpdateAdd+", updateAdd:" +updateAdd+", lastAscAount:"+lastAscAount+", count:"+addCount);

            sum+=addCount;
            if(needUpdateAdd){
                sum+= updateAdd;
            }
//            System.out.println("sum:"+sum);
            befCount = addCount;
            index++;
        }

        return sum;
    }
}
