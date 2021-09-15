package leetcode;

/**
 *           0, 1
 *      dh  -1  0  1
 * (0,1)
 * (1,2)
 * (2,3)
 * (3,3)
 * (4,6)
 * (5,1)
 *
 *
 *  for (int i = 0; i < m; i++) {
 *       while (!stk.empty() && left[stk.top()][j] >= left[i][j]) {
 *             stk.pop();
 *       }
 *       up[i] = stk.empty() ? -1 : stk.top();
 *       stk.push(i);
 * }
 *
 * j\i
 * --------------------------
 *  0   0   0   0   0   1  1
 *
 *  0	0	0	0	1	1  1
 *
 *  1	0	0	1	1	1  1
 *
 *  0	1	0	1	1	1  1
 *
 *  1	1	1	1	1	1  1
 *
 *  1	0	0	1	0	1  0
 *
 * 求：由1组成的最大矩阵面积
 *
 * [n][m]
 * (i: [0,n] j:[0,m] r:[0,n-i] c:[0,m-j] )
 * [i][j]    [i][j+c]              [i][j]   [i][j+c+1]
 * [i+r][j]  [i+r][j+c]            [i+r][j] [i+r][j+c+1]
 *
 * dp[i][j]: 以[i][j]为右下顶点坐标的最大面积。
 *
 *
 * rows == matrix.length
 * cols == matrix[0].length
 * 0 <= row, cols <= 200
 * matrix[i][j] 为 '0' 或 '1'
 *
 * @program: wqIntegration
 * @description:
 * @author: 王强
 * @create: 2020-12-29 09:41
 */
public class MaxMatrix {
    public static void main(String[] args) {
//        int[][] arr = {{1,0,1,1},{1,1,1,0,1},{1,1,1,0,1},{1,1,0,1,0}};
        int i=0;
        do{
            char[][] arr = getTemple();
            System.out.print(getMaxMatrixUseBrute(arr)+"  , ");
            System.out.println(getMaxMatrixUseBrutePro(arr));
        }while (i++<100);


    }

    public static char[][] getTemple(){
        int rowL = (int)(Math.random()*100+1);
        int colL = (int)(Math.random()*100+1);
        char [][] tem = new char[rowL][colL];
        for(int i=0;i<rowL;i++){
            for(int j=0;j<colL;j++){
                int flag = (int)(Math.random()*10);
                if(flag>5){
                    tem[i][j] = '1';
                }else{
                    tem[i][j] = '0';
                }
            }
        }
        return tem;
    }

    public static  int getMaxMatix(char[][] arr){
        int[][] dp = new int[arr.length][arr[0].length];
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                if(arr[i][j] == '1'){
                    dp[i][j]= j-1>0 ? dp[i][j-1]+1 : 1;
                }else{
                    dp[i][j] = 0;
                }
            }
        }
        for(int j=0;j<arr[0].length;j++){
            for(int i=0;j<arr.length;i++){

            }
        }
        return 0;
    }
    public static int getMaxMatrixUseBrutePro(char[][] arr){
        int max = 0;

        for(int i=0,rowLength = arr.length; i<rowLength;i++){
            for(int j=0,colLength = arr[0].length;j<colLength;j++){
                if(arr[i][j] == '0'){
                    continue;
                }
                int rEnd=rowLength;
                int cEnd = colLength;
                for(int r=i;r<rEnd;r++){
                    for(int c=j;c<cEnd;c++){
                        if(arr[r][c] == '0'){
                            if(c==j){
                                rEnd = r;
                            }
                            cEnd = c;
                            break;
                        }else{
                            max = Math.max((r+1-i)*(c+1-j), max);
                        }
                    }
                }
            }
        }
        return max;
    }


    public static int getMaxMatrixUseBrute(char[][] arr){
        int max = 0;
        for(int i=0,rowLength = arr.length; i<rowLength;i++){
            for(int j=0,colLength = arr[0].length;j<colLength;j++){
                for(int r=i;r<rowLength;r++){
                    for(int c=j;c<colLength;c++){
                       max = Math.max(getArea(i,j, r, c, arr), max);
                    }
                }
            }
        }
        return max;
    }

    public static int getArea(int x, int y, int x2, int y2, char[][] arr){
        int area = 0;
        for(int i=x; i<=x2;i++){
            for(int j=y;j<=y2;j++){
                if(arr[i][j] == '0'){
                    return -1;
                }else{
                    area++;
                }
            }
        }
        return area;
    }
}
