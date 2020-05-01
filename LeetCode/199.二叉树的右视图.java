/*
 * @lc app=leetcode.cn id=199 lang=java
 *
 * [199] 二叉树的右视图
 *
 * https://leetcode-cn.com/problems/binary-tree-right-side-view/description/
 *
 * algorithms
 * Medium (63.67%)
 * Likes:    174
 * Dislikes: 0
 * Total Accepted:    26.1K
 * Total Submissions: 40.7K
 * Testcase Example:  '[1,2,3,null,5,null,4]'
 *
 * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
 * 
 * 示例:
 * 
 * 输入: [1,2,3,null,5,null,4]
 * 输出: [1, 3, 4]
 * 解释:
 * 
 * ⁠  1            <---
 * ⁠/   \
 * 2     3         <---
 * ⁠\     \
 * ⁠ 5     4       <---
 * 
 * 
 */

// @lc code=start
/**
 * Definition for a binary tree node. public class TreeNode { int val; TreeNode
 * left; TreeNode right; TreeNode(int x) { val = x; } }
 */
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
class Solution {
    /*
     * 解决方案：广度优先遍历（层次遍历+该层最右节点）
     *  1. 第一印象是用层次遍历，取最右节点；
     *  2. 二叉树层次遍历和图的广度优先遍历一致；
     *    
     *      key：怎么识别每一层的节点
     * 
     * 时间复杂度：O(N) 所有节点遍历一遍 
     * 空间复杂度：O(N) 维护中间节点队列
     */
    public List<Integer> rightSideView1(TreeNode root) {
        List<Integer> ans = new LinkedList<>();
        if (null == root) return ans;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) { // 遍历当前层（关键）
                TreeNode node = queue.poll();
                if (null != node.left)
                    queue.offer(node.left);
                if (null != node.right)
                    queue.offer(node.right);
                if (i == size - 1) // 当前层最后一个节点
                    ans.add(node.val);
            }
        }
        return ans;
    }

    /*
     * 解决方案：深度优先遍历（先右子树、后左子树，递归实现）
     *  1. 第一印象是用层次遍历，取最右节点；
     *  2. 二叉树层次遍历和图的广度优先遍历一致；
     * 
     *      key：深度遍历时，记录当前层depth
     * 
     * 时间复杂度：O(N) 所有节点遍历一遍 
     * 空间复杂度：O(N) 递归栈空间消耗
     */
    public List<Integer> rightSideView2(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        DFSHelper(root, 0, ans);
        return ans;
    }

    private void DFSHelper(TreeNode node, int depth, List<Integer> ans) {
        if (null == node) return;

        if (depth == ans.size()) {
            ans.add(node.val);
        }
        depth++;
        DFSHelper(node.right, depth, ans);
        DFSHelper(node.left, depth, ans);
    }

    /*
     * 解决方案：深度优先遍历（迭代实现）
     * 
     * 时间复杂度：O(N) 所有节点遍历一遍 
     * 空间复杂度：O(N) 递归栈空间消耗
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (null == root) return ans;
        Map<Integer, Integer> rightMostValueAtDepth = new HashMap<Integer, Integer>();
        int maxDepth = -1;

        Stack<TreeNode> stack = new Stack<TreeNode>();
        Stack<Integer> depthStack = new  Stack<Integer>();
        stack.push(root);
        depthStack.push(0);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            int depth = depthStack.pop();

            if (null != node) {
                maxDepth = Math.max(maxDepth, depth);

                if (!rightMostValueAtDepth.containsKey(depth))
                    rightMostValueAtDepth.put(depth, node.val);
            
                stack.push(node.left);
                stack.push(node.right);
                depthStack.push(depth + 1);
                depthStack.push(depth + 1);
            }
        }

        for (int depth = 0; depth <= maxDepth; depth++) {
            ans.add(rightMostValueAtDepth.get(depth));
        }
        return ans;
    }
    
}
// @lc code=end

