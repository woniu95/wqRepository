package algorithm.leetcode;

/**
 * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 *
 *  
 *
 * 示例 1：
 *
 *
 * 输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * 输出：6
 * 解释：最大矩形如上图所示。
 * 示例 2：
 *
 * 输入：matrix = []
 * 输出：0
 * 示例 3：
 *
 * 输入：matrix = [["0"]]
 * 输出：0
 * 示例 4：
 *
 * 输入：matrix = [["1"]]
 * 输出：1
 * 示例 5：
 *
 * 输入：matrix = [["0","0"]]
 * 输出：0
 *  
 *
 * 提示：
 *
 * rows == matrix.length
 * cols == matrix[0].length
 * 0 <= row, cols <= 200
 * matrix[i][j] 为 '0' 或 '1'
 *
 * dp[i][j][0] : 纵向单列矩形 起坐标
 * dp[i][j][1] : 横向单列矩形 起坐标
 * dp[i][j][2] : 纵向对角矩形 起坐标
 * dp[i][j][3] : 横向对角矩形 起坐标
 */
public class _85最大矩阵 {

    public static void main(String[] args) {
        char[][] matrix =  {{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}};
        System.out.println(maximalRectangle(matrix));
    }
   static public int maximalRectangle(char[][] matrix) {

        Position dp[][][] = new Position[matrix.length][matrix[0].length][5];

        for(int i=0,rlength=matrix.length;i<rlength;i++){
            for(int j=0, clength=matrix[0].length;j<clength;j++){
                if(matrix[i][j] == '1'){
                    dp[i][j][0] =  new Position(i,j);
                    dp[i][j][1] =  new Position(i,j);
                }

            }
        }
//0 0 0 0 1 1
//1 0 0 1 1 1
//1 0 1 1 1 1
//1 1 1 1 1 1
//1 0 0 1 0 1
        int max=0;
        for(int i=0,rlength=matrix.length;i<rlength;i++){
            for(int j=0, clength=matrix[0].length;j<clength;j++){
                if(matrix[i][j] == '1' ){
                    if(i-1>=0 && dp[i-1][j][0] != null  && matrix[i-1][j] == '1'){
                        dp[i][j][0].x = dp[i-1][j][0].x;
                        dp[i][j][0].y = dp[i-1][j][0].y;
                    }
                    if(j-1>=0 &&  dp[i][j-1][0] != null && matrix[i][j-1] == '1'){
                        dp[i][j][1].x = dp[i][j-1][1].x;
                        dp[i][j][1].y = dp[i][j-1][1].y;
                    }
                    //对角情况
                    if(i-1>=0  && j-1>=0 && matrix[i-1][j] == '1' && matrix[i][j-1] == '1'
                            && matrix[i-1][j-1] == '1' ){
                        dp[i][j][2].x = i-1;
                        dp[i][j][2].y = j-1;
                        if(dp[i-1][j][2] != null){
                            if(dp[i-1][j][3] == null) {
                                dp[i][j][2].x = dp[i - 1][j][2].x;
                                dp[i][j][2].y = dp[i - 1][j][2].y;
                            }else{
                                dp[i][j][3].x = dp[i][j-1][3].x;
                                dp[i][j][3].y = dp[i][j-1][3].y;
                            }
                        }

                        //两种对角矩阵相交部分
                        if(dp[i-1][j][2] !=null && dp[i][j-1][3] !=null){

                            dp[i][j][4].x =  Math.max(dp[i-1][j][2].x,  dp[i][j-1][3].x);
                            dp[i][j][4].y = Math.max(dp[i-1][j][2].y,  dp[i][j-1][3].y);
                        }

                    }
                    max = Math.max( max, measure(i, j, dp[i][j][0].x, dp[i][j][0].y)) ;
                    max = Math.max( max, measure(i, j, dp[i][j][1].x, dp[i][j][1].y));
                    if(dp[i][j][2] != null){
                        max = Math.max( max, measure(i, j, dp[i][j][2].x, dp[i][j][2].y));
                    }
                    if(dp[i][j][3] != null){
                        max = Math.max( max, measure(i, j, dp[i][j][3].x, dp[i][j][3].y));
                    }
                    if(dp[i][j][4] != null){
                        max = Math.max( max, measure(i, j, dp[i][j][4].x, dp[i][j][4].y));
                    }
                }
            }
        }

       for(int i=0,rlength=matrix.length;i<rlength;i++){
           for(int j=0, clength=matrix[0].length;j<clength;j++){
               System.out.print(matrix[i][j]+" ");
           }
           System.out.println("");
       }
       for(int i=0,rlength=matrix.length;i<rlength;i++){
           for(int j=0, clength=matrix[0].length;j<clength;j++){
                  System.out.print("i: "+i+" j: "+j+" ");
                   System.out.print("dp[0]:"+dp[i][j][0]+" ");
                   System.out.print("dp[1]:"+dp[i][j][1]+" ");
                   System.out.print("dp[2]:"+dp[i][j][2]+" ");
                   System.out.print("dp[3]:"+dp[i][j][3]+" ");
                   System.out.println("dp[4]:"+dp[i][j][4]+" ");
           }
       }


        return max;
    }

   static class Position{
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

       @Override
       public String toString() {
           return x +"," + y ;
       }
   }

   static int measure(int x, int y, int x1, int y2){
       return  (x+1-x1)*(y+1-y2);
    }
}
