package algorithm;

/**
 *
 *      s1[i] = s2[j]
 *         ...k
 *      s1[i+m] = s2[j+n]
 *      i[0,s1.length] j[0,s2.length]  m[1,s1.length-i] n[1,s2.length-j]
 *
 *      求:max k
 *
 * @program: wqIntegration
 * @description:
 * @author: 王强
 * @create: 2020-12-17 10:53
 */
public class MaxSubSequence {
    public static void main(String[] args) {

        System.out.println("maxk: "+maxLengthUseBrute("123345", "138723"));
        System.out.println("maxk: "+getMaxKUseDP("123345", "13823"));

    }



    /**
     * dp[i][j]数组含义： s1左子串 与 s2左子串的问题解
     * dp 分析过程：
     * s1:1       s1:1        s1:12         s1:12
     * s2:1       s2:13       s2:1          s2:13
     * dp[1][1]:1 dp[1][2]:1  dp[2][1]      dp[2][2]
     *
     *  case 1: s1[2] = s2[2]
     *        dp[2][2] = dp[1][1]+1;
     *  case 2: s1[2] != s2[2]
     *        dp[2][2] = Max(dp[1][2], dp[2][1])
     *       eg:
     *           1234  123     1234
     *        dp  2     2  ==>  3
     *           12    123     123
     *
     *  字符状态（1：相同）    dp 表
     *   1 3 8 2 3          0 0 0 0 0 0
     * 1 1 0 0 1 0          0 1 1 1 1 1
     * 2 0 0 0 0 0    ==>   0 1 1 1 2 2
     * 3 1 0 1 0 0          0 1 2 2 2 3
     * 3 0 1 1 0 1          0 1 2 2 2 3
     * 4 0 0 0 0 0          0 1 2 2 2 3
     * 5 0 0 0 0 0          0 1 2 2 2 3
     *
     * @param s1
     * @param s2
     * @return
     */
    private static int getMaxKUseDP(String s1, String s2) {

        int k = 0;
        int[][] dp = new int[s1.length()+1][s2.length()+1];

        for(int i=0,length=s1.length();i<length;i++){
            for(int j=0,jlength=s2.length();j<jlength;j++){

                if(s1.charAt(i) == s2.charAt(j)){
                    dp[i+1][j+1] =dp[i][j] + 1;
                }else{
                    dp[i+1][j+1] = Math.max(dp[i][j+1], dp[i+1][j]);
                }
                if(dp[i+1][j+1]>k){
                    k=dp[i+1][j+1];
                }
            }
        }

        return k;
    }


    private static int maxLengthUseBrute(String s1, String s2) {
        int maxk = 0;

        for(int i=0;i<s1.length();i++){
            for(int j=0;j<s2.length();j++){
                if(s1.charAt(i) == s2.charAt(j)){
                    NextSame nextSame;
                    NextSame before = new NextSame(i,j);
                    int k = 1;
                    do{
                        nextSame = getNextSame(s1, s2, before);
                        before = nextSame;
                        if(nextSame.has){
                            k++;
                        }else{
                            break;
                        }
                    }while (true);
                    if(k>maxk){
                        maxk = k;
                    }
                }
            }
        }
        return maxk;
    }


    static NextSame getNextSame(String s1, String s2, NextSame before){
        NextSame result = new NextSame();
        for(int i=before.s1Index+1;i<s1.length();i++){
            for(int j=before.s2Index+1;j<s2.length();j++){
                if(s1.charAt(i) == s2.charAt(j)){
                    result.has = true;
                    result.s1Index=i;
                    result.s2Index=j;
                    break;
                }
            }
            if(result.has){
                break;
            }
        }
        return result;
    }

    static  class NextSame{
        NextSame( int s1Index, int s2Index){
            this.s1Index = s1Index;
            this.s2Index = s2Index;
        }
        NextSame(){

        }
        int s1Index;
        int s2Index;
        boolean has=false;

        public int getS1Index() {
            return s1Index;
        }

        public void setS1Index(int s1Index) {
            this.s1Index = s1Index;
        }

        public int getS2Index() {
            return s2Index;
        }

        public void setS2Index(int s2Index) {
            this.s2Index = s2Index;
        }

        public boolean isHas() {
            return has;
        }

        public void setHas(boolean has) {
            this.has = has;
        }

        @Override
        public String toString() {
            return "NextSame{" +
                    "s1Index=" + s1Index +
                    ", s2Index=" + s2Index +
                    ", has=" + has +
                    '}';
        }
    }
}
