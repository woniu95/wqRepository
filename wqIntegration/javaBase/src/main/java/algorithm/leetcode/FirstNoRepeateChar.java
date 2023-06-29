package algorithm.leetcode;

import java.util.*;

/**
 *
 * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
 *
 *  
 *
 * 示例：
 *
 * s = "algorithm.leetcode"
 * 返回 0
 *
 * s = "loveleetcode"
 * 返回 2
 *
 * @program: wqIntegration
 * @description:
 * @author: 王强
 * @create: 2020-12-23 09:24
 */
public class FirstNoRepeateChar {

    public int firstUniqChar(String s) {

        Map<Character, Integer> map = new HashMap<>();

        for(int i=0, length=s.length();i<length;i++){
            Character ch = s.charAt(i);
            Integer count = map.get(ch);
            if(count !=null){
                map.put(ch, count+1);
            }else{
                map.put(ch, 1);
            }
        }

        for(int i=0, length=s.length();i<length;i++){
            Character ch = s.charAt(i);
            Integer count = map.get(ch);
            if(count != null && count == 1){
                return i;
            }
        }
        return -1;
    }

}
