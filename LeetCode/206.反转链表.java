import java.util.Stack;

/*
 * @lc app=leetcode.cn id=206 lang=java
 *
 * [206] 反转链表
 *
 * https://leetcode-cn.com/problems/reverse-linked-list/description/
 *
 * algorithms
 * Easy (67.84%)
 * Likes:    821
 * Dislikes: 0
 * Total Accepted:    181.9K
 * Total Submissions: 268K
 * Testcase Example:  '[1,2,3,4,5]'
 *
 * 反转一个单链表。
 * 
 * 示例:
 * 
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * 
 * 进阶:
 * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
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
    // public ListNode reverseList(ListNode head) {
    //     if (null == head)  return head;
    //     ListNode next = head.next;
    //     next = reverseList(next);
    //     ListNode last = next;
    //     while (last != null) {
    //         last = last.next;
    //     }
    //     .next = head;
    //     head.next = null;
    //     return next;
    // }

    // public ListNode reverseList(ListNode head) {
    //     if (head == null) return head;

    //     ListNode preNode = null, currentNode = head;// nextNode = head.next;
    //     while (currentNode.next != null) {
    //         ListNode tempNode = currentNode;  // 记住当前节点
    //         currentNode = currentNode.next;   // 记住下一个待反转的节点
    //         tempNode.next = preNode;          // 将当前节点反转
    //         preNode = tempNode;               // 更新最近反转的节点
    //     }
    //     currentNode.next = preNode;           // 反转最后一个节点
    //     return currentNode;
    // }

    // public ListNode reverseList(ListNode head) {
    //     if (head == null || head.next == null) return head;
    //     ListNode remain = reverseList(head.next);
    //     head.next.next = head; // 重点：虽然反转之后，反转前的“head.next节点”已经没有了next节点，
    //     head.next = null;      // 但通过head.next还可以找到那个节点。
    //     return remain;
    // }

    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }

        return newHead;
    }

}
// @lc code=end

