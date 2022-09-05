package algorithm.twoPoint;

import java.util.Arrays;

/**
 * @PackageName:algorithm.twoPoint
 * @ClassName:SquaresOfSortedArray
 * @Description:
 * @author: wq
 * @date 2022/2/4 20:49
 */
public class SquaresOfSortedArray {
    public static int[] sortedSquares(int[] nums) {
        int point = findFirstPoint(nums);
        int left = point-1;
        int right = point;
        int[] result = new int[nums.length];

        int index = 0;
        while(index<nums.length){
            if(left<0 && right<nums.length){
                result[index] = nums[right]*nums[right];
                right++;
                index++;
                continue;
            }
            if(right>=nums.length && left>=0 ){
                result[index] = nums[left]*nums[left];
                left--;
                index++;
                continue;
            }
            int squaresLeft = nums[left]*nums[left];
            int squaresRight = nums[right]*nums[right];
            if(squaresLeft > squaresRight){
                result[index] = squaresRight;
                right++;

            }else{
                result[index] = squaresLeft;
                left--;
            }
            index++;
        }
        return result;
    }

    private static int findFirstPoint(int[] nums){
        int i=0;
        for(; i<nums.length; i++){
            if(nums[i]>=0){
                break;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int[] nums= {-4,-1,0,3,10};
        System.out.println(Arrays.toString(sortedSquares(nums)));
    }
}
