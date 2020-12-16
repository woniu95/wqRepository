package algorithm;

/**
 * @program: wqIntegration
 * @description:
 * @author: 王强
 * @create: 2020-12-16 19:35
 */
public class CommonSubStr {

    public static void main(String[] args) {
        /**
         * S1 = “123456778”
         * S2 = “357486782”
         *
         *   s1[i] = s2[j]
         *   s1[i+1] = s2[j+1]
         *         ...
         *   s1[i+k] = s2[j+k]
         *
         *  i[0, s1.length] j[0, s2.length]
         *
         *  求： max k
         */
        String s1 = "asdfasdfxcvtxfa";
        String s2 = "asasdfasd";
        int maxK = 0;
        for(int i=0;i<s1.length();i++){
            for(int j=0;j<s2.length();j++){
                if(s1.charAt(i) == s2.charAt(j)){
                    int k = 1;
                    //todo 重复情况 dp
                    while(i+k<s1.length() && j+k<s2.length() && s1.charAt(i+k) == s2.charAt(j+k)){
                        k++;
                    }
                    if(k>maxK){
                        maxK = k;
                    }
                }
            }
        }
        System.out.println("max k:"+maxK);
    }
}
