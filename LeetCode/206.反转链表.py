#
# @lc app=leetcode.cn id=206 lang=python3
#
# [206] 反转链表
#
# https://leetcode-cn.com/problems/reverse-linked-list/description/
#
# algorithms
# Easy (70.98%)
# Likes:    1361
# Dislikes: 0
# Total Accepted:    377.7K
# Total Submissions: 532K
# Testcase Example:  '[1,2,3,4,5]'
#
# 反转一个单链表。
# 
# 示例:
# 
# 输入: 1->2->3->4->5->NULL
# 输出: 5->4->3->2->1->NULL
# 
# 进阶:
# 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
# 
#

# @lc code=start
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution:
    def reverseList(self, head: ListNode) -> ListNode:
        """
        递归 Recursively
        【key】 想明白要返回的是头，但要将当前的节点拼到尾结点后
                (因为只能返回头，所以中间值要作为参数传递)

        Time Complexity: O(n)
        Space Complexity: O(logn)
        """
        def _reverse(node: ListNode, prev: ListNode=None) -> ListNode:
            """
            prev: 已经逆转好的链表
            """
            if not node:
                return prev
            
            n = node.next
            node.next = prev            
            return _reverse(n, node)

        return _reverse(head)

    def reverseList2(self, head: ListNode) -> ListNode:
        """
        迭代 Iteratively
        【tip】注意python 连续赋值的先后顺序

        Time Complexity: O(n)
        Space Complexity: O(1)
        """
        dummy = ListNode(None)
        while head:
            head.next, dummy.next, head = dummy.next, head, head.next
            # tmp = head.next
            # head.next = dummy.next
            # dummy.next = head
            # head = tmp
        return dummy.next
    
    def reverseList3(self, head: ListNode) -> ListNode:
        """
        迭代 Iteratively
        【tip】注意python 连续赋值的先后顺序

        Time Complexity: O(n)
        Space Complexity: O(1)
        """
        prev = None
        while head:
            curr = head
            head = head.next
            curr.next = prev
            prev = curr
        return prev

# @lc code=end

