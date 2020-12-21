package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 376. 摆动序列
 * s1: 1 0 0 1 0 1
 * s2: 1 0 1 0 1 0 || 0 1 0 1 0 1
 *
 * s1[i] = s2[j]
 * s1[i+n1] = s2[j+1]
 *       ...k
 * s1[i+n2] = s2[j+k]
 *
 * i[0, s1.length] j[0,s2.length]  n1>n2 s1.length = s2.length
 * 求： max k
 * @program: wqIntegration
 * @description:
 * @author: 王强
 * @create: 2020-12-17 15:55
 */
public class CrossNumbers {


    public static void main(String[] args) {

//        int[] inputs = {1,17,5,10,13,15,10,5,16,8}; +1
//        int[] inputs = {1,7,4,9,2,5};
//                        1 1 1 0 1
//        int[] inputs = {1,4,7,9,2,5};
        int[] inputs = {1,1};
        List s1 = get(inputs);

        int sameCount = 0;

        for(int i=0;i<s1.size();i++){
            System.out.print(s1.get(i));
        }
        for(int i=0;i+1<s1.size();i++){

            if(s1.get(i) == s1.get(i+1)){
                sameCount++;
            }
        }
        System.out.println();
        System.out.println(sameCount);
        System.out.println(s1.size()-sameCount+1);
    }


    public static List<Integer> get(int... inputs){
        List<Integer> list = new ArrayList<>();
        for(int i=0,length=inputs.length;i<length;i++){
            if(i+1<length){
                int dif = inputs[i+1]-inputs[i];
                if(dif>0){
                    list.add(1);
                }
                if(dif<0){
                    list.add(-1);
                }
            }
        }
        return list;
    }

}
