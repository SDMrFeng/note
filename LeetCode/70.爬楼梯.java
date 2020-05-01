/*
 * @lc app=leetcode.cn id=70 lang=java
 *
 * [70] 爬楼梯
 *
 * https://leetcode-cn.com/problems/climbing-stairs/description/
 *
 * algorithms
 * Easy (48.03%)
 * Likes:    945
 * Dislikes: 0
 * Total Accepted:    175.2K
 * Total Submissions: 362.5K
 * Testcase Example:  '2'
 *
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 
 * 注意：给定 n 是一个正整数。
 * 
 * 示例 1：
 * 
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 * 
 * 示例 2：
 * 
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 * 
 * 
 */

// @lc code=start
class Solution {
    /*
     * 解决方案：动态规划
     *      递推方程：dp[n] = dp[n-1] + dp[n-2]
     *      dp[n]: 到达第n个台阶，有多少种不同的方法
     *            = 到达第n-1个台阶的方法数 + 到达第n-2的方法数
     *  
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     */
    public int climbStairs1(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /*
     * 优化空间复杂度：将用固定的两个变量first，second来代替dp
     * 
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     */
    public int climbStairs(int n) {
        if (n < 2) return 1;
        int first = 1, second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        return second;
    }
}
// @lc code=end

