package src.algorithm;

import java.util.*;

class Solution {
    /**
     *   findKthLargest
     */

    public int findKthLargest(int[] nums, int k) {
        return q_select(nums, 0, nums.length-1, nums.length+1-k);
    }

    void insert_sort(int array[], int left, int loop_times)
    {
        for (int j = left+1; j < left+1+loop_times; j++)
        {
            int key = array[j];
            int i = j-1;
            while ( i>=left && key<array[i] )
            {
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = key;
        }
    }

    int find_median(int array[], int left, int right)
    {
        int[] midian_array = new int [(right-left)/5+1];
        if (left == right)
            return array[left];

        int index;
        for (index = left; index < right - 5; index += 5)
        {
            insert_sort(array, index, 4);
            int num = index - left;
            midian_array[num / 5] = array[index + 2];
        }

        // 处理剩余元素
        int remain_num = right - index + 1;
        if (remain_num > 0)
        {
            insert_sort(array, index, remain_num - 1);
            int num = index - left;
            midian_array[num / 5] = array[index + remain_num / 2];
        }

        int elem_aux_array = (right - left) / 5 - 1;
        if ((right - left) % 5 != 0)
            elem_aux_array++;

        // 如果剩余一个元素返回，否则继续递归
        if (elem_aux_array == 0)
            return midian_array[0];
        else
            return find_median(midian_array, 0, elem_aux_array);
    }

    // 寻找中位数的所在位置
    int find_index(int array[], int left, int right, int median)
    {
        for (int i = left; i <= right; i++)
        {
            if (array[i] == median)
                return i;
        }
        return -1;
    }

    int q_select(int array[], int left, int right, int k)
    {

        // 寻找中位数的中位数
        int median = find_median(array, left, right);

        // 将中位数的中位数与最右元素交换
        int index = find_index(array, left, right, median);
        swap(array, index, right);


        int pivot = array[right];

        // 申请两个移动指针并初始化
        int i = left;
        int j = right - 1;

        // 根据枢纽元素的值对数组进行一次划分
        while(true){
            while( i<right && array[i] <= pivot) i++;

            while(j>left && array[j] > pivot) j--;

            if (i < j)
                swap(array, i, j);
            else break;
        }
        swap(array, i, right);


        int m = i - left + 1;
        if (m == k)
            return array[i];
        else if(m > k)
            return q_select(array, left, i - 1, k);
        else
            return q_select(array, i + 1, right, k - m);
    }
    void swap(int array[],int i,int j){
        int tem = array[i];
        array[i]= array[j];
        array[j] = tem;
    }

    /**
     *    union-find-set
     */
    public int findCircleNum(int[][] M) {

        int size = M.length;
        int father[] = new int[size];
        int count = size;
        //初始化 每个人一个集合
        init(father);
        //
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                //unin i , j 所在集合
                if(M[i][j]==1){
                    if(union(father,i,j)){
                        count--;
                    }
                }
            }
        }
        return count;


    }
    void init(int[] father){
        for(int i=0; i< father.length;i++){
            father[i]=i;
        }
    }
    int find(int[] father, int x){
        int root =x;
        while(father[root]!=root){
            root = father[root];
        }
        int xAncestor = x;
        while(father[xAncestor] != xAncestor){
            father[xAncestor] = root;
            xAncestor = father[xAncestor];
        }
        return root;
    }
    boolean union(int[] father, int x, int y){
        int root1 = find(father, x);
        int root2 = find(father, y);
        if(root1 != root2){
            father[root1]=root2;
            return true;
        }else{
            return false;
        }
    }

    /**
     * constructMaximumBinaryTree
     */

     // Definition for a binary tree node.
     class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }

         @Override
         public String toString() {
             return "{" +
                     "val=" + val +
                     ", left=" + left +
                     ", right=" + right +
                     '}';
         }
     }

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTree(nums, 0, nums.length-1);
    }
    //3,2,1,6,0,5
    public TreeNode constructMaximumBinaryTree(int[] nums, int start, int end){
        if(start>end){
            return null;
        }else if(start == end){
            return new TreeNode(nums[start]);
        }else{
            int max = nums[start];
            int maxIndex = start;
            for(int i=start+1;i<=end;i++){
                if(nums[i]>max) {
                    max = nums[i];
                    maxIndex = i;
                }
            }
            TreeNode node = new TreeNode(max);
            node.left = constructMaximumBinaryTree(nums,  start, maxIndex-1);
            node.right = constructMaximumBinaryTree(nums,maxIndex+1,end);

            return node;
        }

    }
    /**
     * 辅助遍历树
     * @param current
     */
    void reverse(TreeNode current){
        if(current== null){
            return;
        }else{
            System.out.println(current.val);
            System.out.println("  left:"+current.left);
            System.out.println("  right:"+current.right);
            reverse(current.left);
            reverse(current.right);
        }

    }

    /**
     * lcs findLength
     * @param A
     * @param B
     * @return
     */
    public  int findLength(int[] A, int[] B) {
        int[][] lcs = new int[A.length+1][B.length+1];


        for(int i=1;i<=A.length;i++){

            for(int j=1;j<=B.length;j++){
               if(A[i-1] == B[j-1]){
                    lcs[i][j] = lcs[i-1][j-1]+1;
                }else{
                    lcs[i][j] = lcs[i-1][j] > lcs[i][j-1] ? lcs[i-1][j] : lcs[i][j-1];
                }
            }
        }
        //return nowMaxRow[A.length];
        return lcs[A.length][B.length];
    }

}
/**
 * 380. Insert Delete GetRandom O(1)    hashtable
 */

class RandomizedSet {
    HashMap<Integer,Integer> location;
    List<Integer> allValue;
    /** Initialize your data structure here. */
    public RandomizedSet() {
        location = new HashMap<Integer,Integer>();
        allValue = new ArrayList<Integer>();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(location.containsKey(val))
            return false;
        allValue.add(val);
        location.put(val,allValue.size()-1);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(!location.containsKey(val))
            return false;
        Integer index = location.get(val);
        allValue.set(index, allValue.get(allValue.size()-1));
        location.put(allValue.get(index), index);

        allValue.remove(allValue.size()-1);
        location.remove(val);

        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        Random r = new Random();
        int index = r.nextInt(allValue.size());
        return allValue.get(index);
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */

public class LeetCodeMain {
    public static int[] stringToIntegerArray(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return new int[0];
        }

        String[] parts = input.split(",");
        int[] output = new int[parts.length];
        for(int index = 0; index < parts.length; index++) {
            String part = parts[index].trim();
            output[index] = Integer.parseInt(part);
        }
        return output;
    }

    public static void main(String[] args)  {

      /* findCircleNum
            int[][] nums = {{1,1,0},{1,1,0},{0,0,1}};
            int ret = new Solution().findCircleNum(nums);
            String out = String.valueOf(ret);
            System.out.print(out);
       */
        /**
         * 380. Insert Delete GetRandom O(1)
         *  ["RandomizedSet","remove","remove","insert","getRandom","remove","insert"]
         * [[],[0],[0],[0],[],[0],[0]]
         */
//        RandomizedSet obj = new RandomizedSet();
//        obj.remove(0);
//        obj.remove(0);
//        obj.insert(0);
//        obj.getRandom();
//        obj.remove(0);
//        obj.insert(0);
        /**
         *constructMaximumBinaryTree
         */
//        int[] param = {3,2,1,6,0,5};
//        Solution.TreeNode res = new Solution().constructMaximumBinaryTree(param);
//        new Solution().reverse(res);
        /**
         * lcs findLength
         */
        int[] a = {1,2,3,2,1};
        int[] b = {3,2,1,4,7};
        int longest = new Solution().findLength(a, b);
        System.out.print(longest);
    }
}
