#
# @lc app=leetcode.cn id=21 lang=python3
#
# [21] 合并两个有序链表
#
# https://leetcode-cn.com/problems/merge-two-sorted-lists/description/
#
# algorithms
# Easy (64.75%)
# Likes:    1397
# Dislikes: 0
# Total Accepted:    417.2K
# Total Submissions: 644K
# Testcase Example:  '[1,2,4]\n[1,3,4]'
#
# 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
# 
# 
# 
# 示例：
# 
# 输入：1->2->4, 1->3->4
# 输出：1->1->2->3->4->4
# 
# 
#

# @lc code=start
# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
class Solution:
    def mergeTwoLists1(self, l1: ListNode, l2: ListNode) -> ListNode:
        """
        迭代iteratively 注意合并链表的细节
        【Tip】 注意两处代码的简写
                （1）python 连续赋值的注意事项
                （2）or 使用技巧

        时间复杂度 time complexity：O(n) 遍历
        空间复杂度 space complexity：O(1)
        """
        dummy = tail = ListNode(None)
        # 两链都不为空时，对比着合并
        while l1 and l2:
            if l1.val < l2.val:
                tail.next, tail, l1 = l1, l1, l1.next
            else:
                tail.next, tail, l2 = l2, l2, l2.next
        
        # 再接上两链中剩余的部分
        tail.next = l1 or l2

        return dummy.next
    
    def mergeTwoLists(self, l1: ListNode, l2: ListNode) -> ListNode:
        """
        recursively
        """
        if not l1 or not l2:
            return l1 or l2
        
        if l1.val > l2.val:
            l1, l2 = l2, l1
        l1.next = self.mergeTwoLists(l1.next, l2)
        return l1
        # 上面这样写更简洁，但下面这段理论上效率更高一些
        # if l1.val < l2.val:
        #     l1.next = self.mergeTwoLists(l1.next, l2)
        #     return l1
        # else:
        #     l2.next = self.mergeTwoLists(l2.next, l1)
        #     return l2

# @lc code=end

