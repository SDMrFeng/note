/*
 * @lc app=leetcode.cn id=21 lang=java
 *
 * [21] 合并两个有序链表
 *
 * https://leetcode-cn.com/problems/merge-two-sorted-lists/description/
 *
 * algorithms
 * Easy (61.22%)
 * Likes:    1004
 * Dislikes: 0
 * Total Accepted:    251K
 * Total Submissions: 405.3K
 * Testcase Example:  '[1,2,4]\n[1,3,4]'
 *
 * 将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 * 
 * 示例：
 * 
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 * 
 * 
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    /**
     * 解决方案：递归解决（代码会简洁一些）
     * 
     * 时间复杂度：O(m+n), m、n分别是l1和l2中元素的个数
     * 空间复杂度：O(m+n)
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val <= l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    /**
     * 解决方案：迭代解决（注意指针的使用谨慎些就没问题，注意优化代码简洁度）
     * 
     * 时间复杂度：O(m+n)
     * 空间复杂度：O(1)
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode ans = new ListNode(-1), lNew = ans;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                lNew.next = l1;
                l1 = l1.next;
            } else {
                lNew.next = l2;
                l2 = l2.next;
            }
            lNew = lNew.next;
            // lNew.next = null;  // 本意是断开结果链表的next指针，其实不需要这么操作，因为下轮迭代会自动赋新值
        }

        lNew.next = l1 != null ? l1 : l2; // 本行替代下面注释掉的多行
        // if (l1 != null) {
        //     lNew.next = l1;
        //     while(lNew.next != null) {
        //         lNew = lNew.next;
        //     }
        // }
        // if (l2 != null) {
        //     lNew.next = l2;
        // }

        return ans.next;
    }
}
// @lc code=end

