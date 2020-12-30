package leetcode;

/**
 *
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
        int[][] arr = {{0,1,0,0},{1,0,1,1,1},{1,1,1,1,1},{1,0,0,1,0}};
        System.out.println(getMaxMatrixUseBrute(arr));
        System.out.println(getMaxMatrixUseBrutePro(arr));

    }

    public static int getMaxMatrixUseBrutePro(int[][] arr){
        int max = 0;

        return max;
    }


    public static int getMaxMatrixUseBrute(int[][] arr){
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

    public static int getArea(int x, int y, int x2, int y2, int[][] arr){
        int area = 0;
        for(int i=x; i<=x2;i++){
            for(int j=y;j<=y2;j++){
                if(arr[i][j] == 0){
                    return 0;
                }else{
                    area++;
                }
            }
        }
        return area;
    }
}
