package algorithm.leetcode;

/**
 *
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数。
 *
 * 进阶：你能设计一个时间复杂度为 O(log (m+n)) 的算法解决此问题吗？
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * 示例 2：
 *
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 * 示例 3：
 *
 * 输入：nums1 = [0,0], nums2 = [0,0]
 * 输出：0.00000
 *
 * @program: wqIntegration
 * @description:
 * @author: 王强
 * @create: 2020-12-23 09:53
 */
public class FindTwoArgsMidle {


    public static void main(String[] args) {
        int[] nums1 = {}, nums2 = {};
        System.out.println(findMedianSortedArrays(nums1, nums2));
    }


    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int i=0,j=0;
        int sumLength =nums1.length+nums2.length;

        int midleIndex = 0;
        boolean hasTwo = false;
        if(sumLength%2 == 0){
            hasTwo = true;
            midleIndex = sumLength/2-1;
        }else{
            midleIndex = sumLength/2;
        }


        int k =0;
        double midle1 = 0;
        int tem = 0;
        while(i<nums1.length || j<nums2.length){

            if(i<nums1.length && j<nums2.length){
                if(nums1[i]<=nums2[j]){
                    tem = nums1[i];
                    i++;
                }else{
                    tem = nums2[j];
                    j++;
                }
            }else if(i<nums1.length){
                tem = nums1[i];
                i++;
            }else if(j<nums2.length){
                tem = nums2[j];
                j++;
            }

            if(k == midleIndex){
                midle1 = tem;
                if(!hasTwo){
                    break;
                }
            }
            if(hasTwo && k == midleIndex+1){
                midle1+=tem;
                break;
            }
            k++;
        }

        if(!hasTwo){
            return midle1;
        }else{
            return midle1/2;
        }
    }


}
