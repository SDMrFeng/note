/*
 * @lc app=leetcode.cn id=1111 lang=java
 *
 * [1111] 有效括号的嵌套深度
 *
 * https://leetcode-cn.com/problems/maximum-nesting-depth-of-two-valid-parentheses-strings/description/
 *
 * algorithms
 * Medium (67.09%)
 * Likes:    91
 * Dislikes: 0
 * Total Accepted:    17.6K
 * Total Submissions: 22.6K
 * Testcase Example:  '"(()())"'
 *
 * 有效括号字符串 定义：对于每个左括号，都能找到与之对应的右括号，反之亦然。详情参见题末「有效括号字符串」部分。
 * 
 * 嵌套深度 depth 定义：即有效括号字符串嵌套的层数，depth(A) 表示有效括号字符串 A 的嵌套深度。详情参见题末「嵌套深度」部分。
 * 
 * 有效括号字符串类型与对应的嵌套深度计算方法如下图所示：
 * 
 * 
 * 
 * 
 * 
 * 给你一个「有效括号字符串」 seq，请你将其分成两个不相交的有效括号字符串，A 和 B，并使这两个字符串的深度最小。
 * 
 * 
 * 不相交：每个 seq[i] 只能分给 A 和 B 二者中的一个，不能既属于 A 也属于 B 。
 * A 或 B 中的元素在原字符串中可以不连续。
 * A.length + B.length = seq.length
 * 深度最小：max(depth(A), depth(B)) 的可能取值最小。 
 * 
 * 
 * 划分方案用一个长度为 seq.length 的答案数组 answer 表示，编码规则如下：
 * 
 * 
 * answer[i] = 0，seq[i] 分给 A 。
 * answer[i] = 1，seq[i] 分给 B 。
 * 
 * 
 * 如果存在多个满足要求的答案，只需返回其中任意 一个 即可。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 输入：seq = "(()())"
 * 输出：[0,1,1,1,1,0]
 * 
 * 
 * 示例 2：
 * 
 * 输入：seq = "()(())()"
 * 输出：[0,0,0,1,1,0,1,1]
 * 解释：本示例答案不唯一。
 * 按此输出 A = "()()", B = "()()", max(depth(A), depth(B)) = 1，它们的深度最小。
 * 像 [1,1,1,0,0,1,1,1]，也是正确结果，其中 A = "()()()", B = "()", max(depth(A),
 * depth(B)) = 1 。 
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 < seq.size <= 10000
 * 
 * 
 * 
 * 
 * 有效括号字符串：
 * 
 * 仅由 "(" 和 ")" 构成的字符串，对于每个左括号，都能找到与之对应的右括号，反之亦然。
 * 下述几种情况同样属于有效括号字符串：
 * 
 * ⁠ 1. 空字符串
 * ⁠ 2. 连接，可以记作 AB（A 与 B 连接），其中 A 和 B 都是有效括号字符串
 * ⁠ 3. 嵌套，可以记作 (A)，其中 A 是有效括号字符串
 * 
 * 
 * 嵌套深度：
 * 
 * 类似地，我们可以定义任意有效括号字符串 s 的 嵌套深度 depth(S)：
 * 
 * ⁠ 1. s 为空时，depth("") = 0
 * ⁠ 2. s 为 A 与 B 连接时，depth(A + B) = max(depth(A), depth(B))，其中 A 和 B 都是有效括号字符串
 * ⁠ 3. s 为嵌套情况，depth("(" + A + ")") = 1 + depth(A)，其中 A 是有效括号字符串
 * 
 * 例如：""，"()()"，和 "()(()())" 都是有效括号字符串，嵌套深度分别为 0，1，2，而 ")(" 和 "(()"
 * 都不是有效括号字符串。
 * 
 * 
 */

// @lc code=start
class Solution {
    // 解决方案：max(depth(A), depth(B))可能取值最小 <==> 让A字符串和B字符串的depth尽可能相近
    //         栈中的每个左括号对应一个深度，左括号要么属于A要么属于B；将栈中的左括号按奇偶分配给A和B就能满足要求
    //         小技巧：不用实际的栈，用一个深度来模拟栈
    //               用idx&1来判断奇偶
    // 时间复杂度：O(n)
    // 空间复杂度：O(1) 不计结果返回值所占空间
    public int[] maxDepthAfterSplit1(String seq) {
        int[] ans = new int[seq.length()];
        int idx = 0, depth = 0;  // 用depth记录栈中有效“(”的数量
        for (char c : seq.toCharArray()) {
            // if (c == '(') {
            //     ans[idx++] = (++depth) & 1;
            // }
            // else { // c == ')'
            //     ans[idx++] = (depth--) & 1;
            // }
            ans[idx++] = c == '(' ? ((++depth) & 1) : ((depth--) & 1);
        }

        return ans;
    }
    
    
    // 解决方案：max(depth(A), depth(B))可能取值最小 <==> 让A字符串和B字符串的depth尽可能相近
    //         栈中的每个左括号对应一个深度，左括号要么属于A要么属于B；将栈中的左括号按奇偶分配给A和B就能满足要求
    //         简化技巧：“左右括号”一起出栈，则depth奇偶和idx一致，所以去除depth，直接使用index判断奇偶
    // 时间复杂度：O(n)
    // 空间复杂度：O(1) 不计结果返回值所占空间
    public int[] maxDepthAfterSplit2(String seq) {
        int[] ans = new int[seq.length()];
        int idx = 0;
        for (char c : seq.toCharArray())
            ans[idx++] = c == '(' ? (idx & 1) : ((idx + 1) & 1);

        return ans;
    }
    // 简化技巧： 条件表达式的判断可以删掉的，因为括号本身字符的ascii就是一奇一偶了，直接和index异或，再取最低位就完了
    public int[] maxDepthAfterSplit(String seq) {
        int[] ans = new int[seq.length()];
        int idx = 0;
        for (char c : seq.toCharArray())
            ans[idx++] = (c ^ idx) & 1;

        return ans;
    }

}
// @lc code=end

