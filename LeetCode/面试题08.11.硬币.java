class Solution {
    /*
     * 解决方案：利用“518.硬币兑换II”的思路
     *       dp[k,i]：用前k种硬币兑换i元的总兑换方法数目；
     *       dp[k,i] = dp[k-1][i] + dp[k][i-k]
     *              即：用k-1种硬币兑换i元的总兑换方法数目 + 用前k种硬币兑换i-k元的总兑换方法数目
     *       优化后dp[i] = dp[i] + dp[i-k]      
     *
     * 时间复杂度：O(N) 硬币种类数为4，是常数
     * 空间复杂度：O(N)
     */
    public int waysToChange1(int n) {
        int[] coins = new int[]{1, 5, 10, 25};
        return change(n, coins);
    }
    // 硬币兑换II的解决方法
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) for (int i = 1; i <= amount; i++) {
            if (i >= coin)
                dp[i] = (dp[i] + dp[i - coin]) % 1000000007;
        }

        return dp[amount];
    }

    /*
     * 解决方案：等差数列求和
     * 步骤：1. n分钱里，如果有i个25分硬币，则剩余n-25*i分钱
     *      2. n-25*i可以用多少种1、5、10分的硬币表示呢？
     *        2.1 剩余的n-25*i分，最多可以用a个10分硬币，其中a = (n-25i)/10
     *        2.2 剩余的n-25*i-10a，最多可以用b个5分硬币，其中b = (n-25i)%10/5
     *        2.3 剩余的n-25*i-10a，都用1分硬币表示，假设用了c个1分硬币
     *        (*) 即：n = 25i + 10a + 5b + c
     *      3. 如果使用了i个25分硬币，剩下的只使用ax个10分硬币（其中ax选择范围[0, a])，之后剩下的金额为10(a-ax)+5b+c
     *        3.1 剩余的金额10(a-ax)+5b+c = 5(2a-2ax+b)+c，则5分硬币选择范围[0,2a-2ax+b],之后不足的部分，用1分的硬币来凑；
     *        3.2 即：在用i个25分硬币、用ax个10分硬币的情况下，总选择方法数目为2a-2ax+b+1种；
     *        3.3 则，在用i个25分硬币的情况下，总选择方法数目为ax从0到a取值时，2a-2ax+b+1的累加和
     *            累加和是2a+b+1+2ax的关于ax等差数列之和
     *            累加和 = (2a+b+1)*(a+1) - 2a(a+1)/2 = (a+1)(a+b+1)
     *      4. 遍历i from 0 to n/25，将累加和再累加就得到了总的组合方法数目
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     */
     public int waysToChange(int n) {
         long ans = 0;
         int mod = 1000000007;
         for (int i = 0; i * 25 <= n; ++i) {
             int rest = n - i * 25;
             int a = rest / 10;
             int b = rest % 10 / 5;
             ans = (ans + (long)(a + 1) * (a + b + 1) % mod) % mod;
         }
         return (int)ans;
     }
}