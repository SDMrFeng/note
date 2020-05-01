/*
 * @lc app=leetcode.cn id=62 lang=java
 *
 * [62] 不同路径
 *
 * https://leetcode-cn.com/problems/unique-paths/description/
 *
 * algorithms
 * Medium (59.12%)
 * Likes:    468
 * Dislikes: 0
 * Total Accepted:    86.9K
 * Total Submissions: 145.7K
 * Testcase Example:  '3\n2'
 *
 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。
 * 
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。
 * 
 * 问总共有多少条不同的路径？
 * 
 * 
 * 
 * 例如，上图是一个7 x 3 的网格。有多少可能的路径？
 * 
 * 
 * 
 * 示例 1:
 * 
 * 输入: m = 3, n = 2
 * 输出: 3
 * 解释:
 * 从左上角开始，总共有 3 条路径可以到达右下角。
 * 1. 向右 -> 向右 -> 向下
 * 2. 向右 -> 向下 -> 向右
 * 3. 向下 -> 向右 -> 向右
 * 
 * 
 * 示例 2:
 * 
 * 输入: m = 7, n = 3
 * 输出: 28
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= m, n <= 100
 * 题目数据保证答案小于等于 2 * 10 ^ 9
 * 
 * 
 */

// @lc code=start
class Solution {
    // 递归处理
    public int uniquePaths1(int m, int n) {
        return (1 == m || 1 == n) ? 1 : uniquePaths(m - 1, n) + uniquePaths(m, n - 1);
    }

    // 动态规划
    // 时间复杂度：O(m*n)
    // 空间复杂度：O(m*n)
    public int uniquePaths2(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++)  dp[i][1] = 1;
        for (int i = 1; i <= n; i++) dp[1][i] = 1;
        for (int i = 2; i <= m; i++)
            for (int j = 2; j <= n; j++)
                dp[i][j] = dp[i][j - 1] + dp[i - 1][j];
        return dp[m][n];
    }

    // 动态规划
    // 时间复杂度：O(m*n)
    // 空间复杂度：O(n)
    public int uniquePaths3(int m, int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1);
        for (int i = 2; i <= m; i++)
            for (int j = 2; j <= n; j++)
                dp[j] += dp[j-1];
        return dp[n];
    }
    
    // 排列组合，机器人总共需要向下走m-1步，向右n-1步，转化为数学问题就是在m+n-2个空格中寻找m-1个向下走，次数就是求C(m+n-2)(m-1)的结果。
    // 接下来的问题，就是如何求。如果直接求(m+n-2)!的结果，很容易超出int限制，所以使用double来储存，最后再将其转化为int输出。
    // 时间复杂度：O(m)
    // 空间复杂度：O(1)
    public int uniquePaths(int m, int n) {
        long result = 1, k = Math.min(m , n), top = m + n - 1;
        for (int i = 1; i < k; i++) {
            result = result * (top - i) / i;
            // result *= (top - i) / i; // 这种写法会先算出后面的部分，最终结果会有误差
        }
        return (int) result;
    }
}
// @lc code=end

