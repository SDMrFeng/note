import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * @lc app=leetcode.cn id=145 lang=java
 *
 * [145] 二叉树的后序遍历
 *
 * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/description/
 *
 * algorithms
 * Hard (70.42%)
 * Likes:    233
 * Dislikes: 0
 * Total Accepted:    54.9K
 * Total Submissions: 78K
 * Testcase Example:  '[1,null,2,3]'
 *
 * 给定一个二叉树，返回它的 后序 遍历。
 * 
 * 示例:
 * 
 * 输入: [1,null,2,3]  
 * ⁠  1
 * ⁠   \
 * ⁠    2
 * ⁠   /
 * ⁠  3 
 * 
 * 输出: [3,2,1]
 * 
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 * 
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {

    // 不递归就会用到栈
    // 思路1: 遍历到父节点时，把父节点值取出来，但把父节点压栈（再出栈时，处理父节点的左子树），然后处理右节点
    public List<Integer> postorderTraversal1(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<Integer>();
        if (root == null)
            return result;

        Deque<TreeNode> stack = new ArrayDeque<TreeNode>(); // 不递归，就要使用到栈
        TreeNode node = root;
        while(!stack.isEmpty() || null != node) {
            if (null != node) {
                result.addFirst(node.val); // 父先进
                stack.push(node);
                node = node.right; // 右插在父前面
            }
            else {
                node = stack.pop();
                node = node.left;  // 左插在右前面
            }
        }

        return result;
    }

    // 思路2: 遍历到父节点时，把父节点值取出来；先把左子树压栈，再把右子树压栈；从栈顶依次处理就是后序了
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (null == root) return result;
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.addFirst(node.val);      // 父先处理
            if (node.left != null)       // 左节点压栈，等右节点处理完
                stack.push(node.left);
            if (node.right != null)     // 右节点压栈
                stack.push(node.right);
        }

        return result;
    }


    // public int[][] findContinuousSequence(int target) {
    //     // 双指针滑动窗口解法
    //     int i = 1, j = 2, res
    // }

    public int[][] findContinuousSequence(int target) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        while(target > 0) {
            target -= i++;
            if (target > 0 && target % i == 0) {
                int[] array = new int[i];
                for (int k = target / i, j = 0; k < target/i +i; k++, j++)
                    array[j] = k;
                
                result.add(array);
            }
        }
        Collections.reverse(result);
        return result.toArray(new int[0][]);
    }
}
// @lc code=end

