package com.wq.collection;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: 王强
 * @create: 2021-12-23 09:25
 */
public class CollectionUtils {

    /**
     * 比较两个集合不同
     * @param source1
     * @param source2
     */
    public static void compare(Collection<String> source1, Collection<String> source2){

        System.out.println("source1  not has:");
        for(String column2 : source2){
            boolean table1has = false;
            for(String column1 : source1){
                if(column1.equals(column2)){
                    table1has = true;
                    break;
                }
            }
            if(!table1has){
                System.out.println(column2);
            }
        }
        System.out.println("=====================================");

        System.out.println("source2 not has:");
        for(String column1 : source1){
            boolean table2has = false;
            for(String column2 : source2){
                if(column1.equals(column2)){
                    table2has = true;
                    break;
                }
            }
            if(!table2has){
                System.out.println(column1);
            }
        }
    }
}
