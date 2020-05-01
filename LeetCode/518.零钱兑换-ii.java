/*
 * @lc app=leetcode.cn id=518 lang=java
 *
 * [518] 零钱兑换 II
 *
 * https://leetcode-cn.com/problems/coin-change-2/description/
 *
 * algorithms
 * Medium (48.27%)
 * Likes:    137
 * Dislikes: 0
 * Total Accepted:    10.8K
 * Total Submissions: 21.6K
 * Testcase Example:  '5\n[1,2,5]'
 *
 * 给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。 
 * 
 * 
 * 
 * 
 * 
 * 
 * 示例 1:
 * 
 * 输入: amount = 5, coins = [1, 2, 5]
 * 输出: 4
 * 解释: 有四种方式可以凑成总金额:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 * 
 * 
 * 示例 2:
 * 
 * 输入: amount = 3, coins = [2]
 * 输出: 0
 * 解释: 只用面额2的硬币不能凑成总金额3。
 * 
 * 
 * 示例 3:
 * 
 * 输入: amount = 10, coins = [10] 
 * 输出: 1
 * 
 * 
 * 
 * 
 * 注意:
 * 
 * 你可以假设：
 * 
 * 
 * 0 <= amount (总金额) <= 5000
 * 1 <= coin (硬币面额) <= 5000
 * 硬币种类不超过 500 种
 * 结果符合 32 位符号整数
 * 
 * 
 */

// @lc code=start
class Solution {
    /*
     * 解决方案：动态规划
     *       递推方程：
     * 
     * 零钱兑换与爬楼梯问题比较：
     *      1.（排列问题）爬楼梯总爬法问题，先后迈出的步数之间有约束关系，1-2-1与2-1-1算不同的爬法；
     *          dp[i] = dp[i-1] + dp[i-2]
     *      2.（排列问题？）爬楼梯最小cost问题
     *          dp[i] = cost[i] + min(dp[i-1], dp[i-2])
     *      3.（组合问题）零钱兑换，求硬币的最小数量
     *          dp[amount] = 1 + min(dp[amount - coins[0]], ..., dp[amount - coins[n-1]])
     *      4.（组合问题）本题零钱兑换II，1-2-1与2-1-1算是一种兑换方法
     *          正确定义：dp[k,i]用前k种硬币，兑换i钱的总兑换方法数目
     *                  dp[k,i] = dp[k-1,i] + dp[k,i-k]
     *                  用前k种的硬币凑齐金额i，要分为两种情况开率，一种是只用k-1种硬币就凑齐了；另一种是前面不用第k种硬币已经凑到了i-k，现在就差一个第k种硬币了。
     * 
     *      *. 详细对比见：https://leetcode-cn.com/problems/coin-change-2/solution/ling-qian-dui-huan-iihe-pa-lou-ti-wen-ti-dao-di-yo/
     * 
     * 时间复杂度：O(M*N)
     * 空间复杂度：O(M*N)
     */
    public int change1(int amount, int[] coins) {
        int K = coins.length;
        int I = amount;
        int[][] dp = new int[K + 1][I + 1];
        for (int k = 0; k <= K; k++) dp[k][0] = 1;
        for (int k = 1; k <= K; k++) for (int i = 1; i <= I; i++) {
            if (i >= coins[k - 1])
                dp[k][i] = dp[k][i - coins[k - 1]] + dp[k - 1][i];
            else
                dp[k][i] = dp[k - 1][i];
        }

        return dp[coins.length][amount];
    }

    /*
     * 动态规划，优化空间复杂度；
     * dp[i] = dp[i] + dp[i-k]
     *        后面的dp[i]：不用第k种硬币时，兑换方法数目
     *        后面的dp[i-k]：用k种硬币兑换i-k的兑换方法数目
     * 
     * 时间复杂度：O(M*N)
     * 空间复杂度：O(N)
     */
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) for (int i = 1; i <= amount; i++) {
            if (i >= coin)
                dp[i] += dp[i - coin];
        }

        return dp[amount];
    }
}
// @lc code=end

