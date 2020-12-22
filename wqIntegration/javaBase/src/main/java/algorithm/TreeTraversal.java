package algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * 给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 *
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回锯齿形层序遍历如下：
 *
 * [
 *   [3],
 *   [20,9],
 *   [15,7]
 * ]
 *
 *
 * public class TreeNode {
 *     int val;
 *      TreeNode left;
 *      TreeNode right;
 *      TreeNode(int x) { val = x; }
 *  }
 * @program: wqIntegration
 * @description:
 * @author: 王强
 * @create: 2020-12-22 09:36
 */
public class TreeTraversal {

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        //true: left false: right
        List<List<Integer>> result = new LinkedList<>();
        boolean direction = true;
        LinkedList<TreeNode> rightStack = new LinkedList<>();
        LinkedList<TreeNode> leftStack = new LinkedList<>();
        leftStack.push(root);
        while(leftStack.size() >0 || rightStack.size() >0){
            TreeNode currentNode;
            List<Integer> row = new LinkedList<>();
            if(direction){
                while ((currentNode = leftStack.pollFirst()) != null){
                    row.add(currentNode.val);
                    if(currentNode.left!=null){
                        rightStack.push(currentNode.left);
                    }
                    if(currentNode.right!=null){
                        rightStack.push(currentNode.right);
                    }
                }

            }else{
                while((currentNode = rightStack.pollFirst())!=null){
                    row.add(currentNode.val);
                    if(currentNode.right!=null){
                        leftStack.push(currentNode.right);
                    }
                    if(currentNode.left!=null){
                        leftStack.push(currentNode.left);
                    }
                }
            }
            direction = !direction;
            result.add(row);
        }
        return result;
    }


    public class TreeNode {
      int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }
   }
}
