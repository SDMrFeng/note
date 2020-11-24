#
# @lc app=leetcode.cn id=222 lang=python3
#
# [222] 完全二叉树的节点个数
#
# https://leetcode-cn.com/problems/count-complete-tree-nodes/description/
#
# algorithms
# Medium (73.46%)
# Likes:    366
# Dislikes: 0
# Total Accepted:    63K
# Total Submissions: 82.6K
# Testcase Example:  '[1,2,3,4,5,6]'
#
# 给出一个完全二叉树，求出该树的节点个数。
# 
# 说明：
# 
# 
# 完全二叉树的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第
# h 层，则该层包含 1~ 2^h 个节点。
# 
# 示例:
# 
# 输入: 
# ⁠   1
# ⁠  / \
# ⁠ 2   3
# ⁠/ \  /
# 4  5 6
# 
# 输出: 6
# 
#

# @lc code=start
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def countNodes1(self, root: TreeNode) -> int:
        """
        解决思路：
        【核心】深度优先遍历（递归）
        【辅助】通过纸上画图，更容易形成思路，找规律，将整体问题化解为小问题        

        时间复杂度：O(n)  递归遍历一遍
        空间复杂度：O(logn)  递归栈空间
        """
        if not root:
            return 0
        return 1 + self.countNodes(root.left) + self.countNodes(root.right)
        # return 0 if not root else 1 + self.countNodes(root.left) + self.countNodes(root.right)

    def countNodes(self, root: TreeNode) -> int:
        """
        解决思路：
        【核心】广度优先遍历（递归）
        【辅助】通过纸上画图，更容易形成思路，找规律，将整体问题化解为小问题        

        时间复杂度：O(n)  递归遍历一遍
        空间复杂度：O(logn)  单层空间
        """
        count = 0
        if root:
            queue = [root]
            while queue:
                currentNode = queue.pop(0)
                count += 1
                if currentNode.right:
                    queue.append(currentNode.left)
                    queue.append(currentNode.right)
                elif currentNode.left:
                    queue.append(currentNode.left)

        return count
    
    def countNodes3(self, root: TreeNode) -> int:
        """
        解决思路：
        【核心】（二分处理）通过右子树的深度和整体深度比较，判断出来左右子树那一边是完全二叉树
        【辅助】通过纸上画图，更容易形成思路，找规律，将整体问题化解为小问题        
        【Tip】 (1)位运算代替指数运算 (2) 2^h - 1 + 根节点 = 2^h

        时间复杂度：O((logn)^2)  递归遍历一遍
        空间复杂度：O(1)
        """
        height = self.depth(root)
        print(height)
        if height < 0:
            # 空节点
            return 0
        elif self.depth(root.right) == height - 1:
            # 不齐整的节点位于右子树，需要单独计数（左子树是完全二叉树）
            return (1 << height) + self.countNodes(root.right)
            # return pow(2, height) + self.countNodes(root.right)
            # return 2**height + self.countNodes(root.right)
        else:
            # 右子树齐整，右子树是完全二叉树（左子树不齐整，需要单独计数）
            return (1 << height - 1) + self.countNodes(root.left)
            # return pow(2, height - 1) + self.countNodes(root.left)
        
    def depth(self, root):
        """
        树的深度/高度
        """
        return -1 if not root else 1 + self.depth(root.left)
# @lc code=end

