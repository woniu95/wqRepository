package src.base;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: testgit
 * @description:
 * @author: 王强
 * @create: 2020-11-30 17:16
 */
public class IteratorFailFast {
    public static void main(String[] args) {
       List list = new LinkedList();
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
    }
}
