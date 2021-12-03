package base;

/**
 * @PackageName:base
 * @ClassName:StringTest
 * @Description:
 * @author: wq
 * @date 2021/9/12 21:55
 */
public class StringTest {

    public static void main(String[] args) {

    }

    public int[] test(int[] nums){

        if(nums!=null){

            int length = nums.length;
            int[] result = new int[length];
            int resultIndex = 0;

            for(int i=0; i<length; i++){
                if(nums[i] != 0){
                  result[resultIndex++]=nums[i];
                }
            }

        }else{
            return null;
        }
        return null;
    }
}
