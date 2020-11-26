#
# @lc app=leetcode.cn id=148 lang=python3
#
# [148] 排序链表
#
# https://leetcode-cn.com/problems/sort-list/description/
#
# algorithms
# Medium (67.86%)
# Likes:    905
# Dislikes: 0
# Total Accepted:    124.1K
# Total Submissions: 182.7K
# Testcase Example:  '[4,2,1,3]'
#
# 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
# 
# 进阶：
# 
# 
# 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
# 
# 
# 
# 
# 示例 1：
# 
# 
# 输入：head = [4,2,1,3]
# 输出：[1,2,3,4]
# 
# 
# 示例 2：
# 
# 
# 输入：head = [-1,5,3,4,0]
# 输出：[-1,0,3,4,5]
# 
# 
# 示例 3：
# 
# 
# 输入：head = []
# 输出：[]
# 
# 
# 
# 
# 提示：
# 
# 
# 链表中节点的数目在范围 [0, 5 * 10^4] 内
# -10^5 
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
    """
    「147. 对链表进行插入排序」要求使用插入排序的方法对链表进行排序，插入排序的时间复杂度是 O(n^2)，其中 n 是链表的长度。
    这道题考虑时间复杂度更低的排序算法。题目的进阶问题要求达到O(nlogn) 的时间复杂度和O(1) 的空间复杂度，
    时间复杂度是O(nlogn) 的排序算法包括归并排序、堆排序和快速排序（快速排序的最差时间复杂度是 O(n^2))），其中最适合链表的排序算法是归并排序。
    归并排序基于分治算法。最容易想到的实现方式是自顶向下的递归实现，考虑到递归调用的栈空间，自顶向下归并排序的空间复杂度是 O(\log n)O(logn)。
                        如果要达到 O(1) 的空间复杂度，则需要使用自底向上的实现方式。
    """
    def merge(self, head1: ListNode, head2: ListNode) -> ListNode:
        """
        注意合并链表的细节
        【Tip】 注意两处代码的简写
                （1）python 连续赋值的注意事项
                （2）or 使用技巧
        """
        dummy = tail = ListNode(None)
        while head1 and head2:
            if head1.val < head2.val:
                tail.next, tail, head1 = head1, head1, head1.next
                # tail.next = head1
                # tail = tail.next # head1
                # head1 = head1.next
            else:
                tail.next, tail, head2 = head2, head2, head2.next
        tail.next = head1 or head2
        # if head1:
        #     tail.next = head1
        # elif head2:
        #     tail.next = head2
        return dummy.next
        
    def sortList1(self, head: ListNode) -> ListNode:
        """
        递归Recursively (自上而下归并)
        【Tip】 注意内置函数map的应用，非常赞赞

        Time Complexity: O(nlogn)
        Space Complexity: O(logn)
        """
        if not head or not head.next:
            return head
        
        # 快慢指针将链表一分为二
        pre, slow, fast = None, head, head
        while fast and fast.next:
            pre, slow, fast = slow, slow.next, fast.next.next
        pre.next = None # 将链表截断，pre是前一段的最后一个节点

        return self.merge(*map(self.sortList, (head, slow)))
    
    def sortList(self, head: ListNode) -> ListNode:
        """
        迭代Iteratively (自下而上归并)
        【Tip】 

        Time Complexity: O(nlogn)
        Space Complexity: O(1)
        """
        if not head or not head.next:
            return head
        
        # 计算链表长度
        length, curr = 0, head
        while curr:
            length += 1
            curr = curr.next
        
        subLength, dummy = 1, ListNode(None, head)
        while subLength < length:
            prev, curr = dummy, dummy.next
            while curr:
                # 获取“本轮中”长度为subLength的第一段
                head1 = curr
                for i in range(1, subLength):
                    if curr.next:
                        curr = curr.next
                    else:
                        break
                # 获取“本轮中”长度为subLength的第二段（需要将第一段截断None）
                # head2, curr, curr.next = curr.next, curr.next, None  # 如果连续赋值不是普通变量，有风险
                head2 = curr.next
                curr.next = None
                curr = head2
                for i in range(1, subLength):
                    if curr and curr.next:
                        curr = curr.next
                    else:
                        break
                
                # 截断“本轮中”第二段，保留剩余的头部
                succ = None
                if curr:
                    succ, curr.next = curr.next, None
                
                # 合并“本轮中”前两段
                merged = self.merge(head1, head2)
                # 将“本轮中”合并后的前两段拼接到“前n轮后”，并记录几轮过后的最后一个节点prev
                prev.next = merged
                while prev.next:
                    prev = prev.next
                # 为下一轮准备
                curr = succ
            
            subLength <<=1 # 升级(翻倍)归并的长度
        
        return dummy.next


# @lc code=end

