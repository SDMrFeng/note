/*
 * @lc app=leetcode.cn id=1248 lang=java
 *
 * [1248] 统计「优美子数组」
 *
 * https://leetcode-cn.com/problems/count-number-of-nice-subarrays/description/
 *
 * algorithms
 * Medium (46.99%)
 * Likes:    39
 * Dislikes: 0
 * Total Accepted:    5.3K
 * Total Submissions: 10.5K
 * Testcase Example:  '[1,1,2,1,1]\n3'
 *
 * 给你一个整数数组 nums 和一个整数 k。
 * 
 * 如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
 * 
 * 请返回这个数组中「优美子数组」的数目。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 输入：nums = [1,1,2,1,1], k = 3
 * 输出：2
 * 解释：包含 3 个奇数的子数组是 [1,1,2,1] 和 [1,2,1,1] 。
 * 
 * 
 * 示例 2：
 * 
 * 输入：nums = [2,4,6], k = 1
 * 输出：0
 * 解释：数列中不包含任何奇数，所以不存在优美子数组。
 * 
 * 
 * 示例 3：
 * 
 * 输入：nums = [2,2,2,1,2,2,1,2,2,2], k = 2
 * 输出：16
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= nums.length <= 50000
 * 1 <= nums[i] <= 10^5
 * 1 <= k <= nums.length
 * 
 * 
 */

// @lc code=start
class Solution {
    /*
     * 解决方案：优美子数组‘核’ & ‘核’的贡献值（数学法）
     *        1.统计每个奇数出现的位置的下标，记录在oddIndex数组中；
     *        2.oddIndex[i]到oddIndex[i+k-1]的k个奇数组成了“某一组优美子数组的‘核’”；
     *        3.“这一组优美子数组的‘核’”如何贡献优美子数组呢？通过前面拼接偶数或者后面拼接偶数都是优美子数组；
     *          “这一组优美子数组的‘核’”可以贡献优美子数组的数目 = 前后偶数位置总的“组合”数目  PS：可以拼接0个偶数
     *                             即可以贡献的优美子数组数目 = “oddIndex[i-1]到oddIndex[i]间偶数数目 + 1” * “oddIndex[i+k-1]到oddIndex[i+k]间偶数数目 + 1”
     *          因为oddIndex存储了所有奇数的在原数组中的下表，两个奇数间的偶数数目 = oddIndex[i] - oddIndex[i-1] - 1
     *          所以“这一组优美子数组的‘核’”可以贡献优美子数组的数目 = (oddIndex[i]−oddIndex[i−1])*(oddIndex[i+k]−oddIndex[i+k−1])
     * 
     * 注意：边界条件容易出错
     *      oddIndex[0] = -1：因为左侧不补充任何偶数时，也是“1”种组合条件
     *      oddIndex[cnt+1] = n：因为最右侧奇数oddIndex[cnt]最大值是n-1，右侧不补充任何偶数时，也是要保证是“1”中组合条件
     * 
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     */
    public int numberOfSubarrays1(int[] nums, int k) {
        int n = nums.length, cnt = 0, ans = 0, oddIndex[] = new int[n + 2];
        
        // 统计奇数出现的位置的下标，同时处理oddIndex的边界；
        for (int i = 0; i < n; i++) {
            if ((nums[i] & 1) == 1) oddIndex[++cnt] = i;
        }
        oddIndex[0] = -1; oddIndex[++cnt] = n;
        
        // 计算每组“优美子数组‘核’” 的贡献值
        for (int i = 1; i + k <= cnt; ++i){
            ans += (oddIndex[i] - oddIndex[i - 1]) * (oddIndex[i + k] - oddIndex[i + k - 1]);
        }
        return ans;
    }

    /*
     * 解决方案：（前缀和+差分）
     *         1. 统计每个索引位置i之前共有多少个奇数出现-->preOddCnt[i]；
     *         2. 每个索引位置i可贡献的优美子数组的个数 = preOddCnt数组中等于“preOddCnt[i] - k”的位置个数；
     *         PS：普通实现会出现两层循环，时间复杂度为O(N^2)
     * 
     * 实际优化实现：（一次遍历）
     *         1. 先统计每一个奇数“未来”参数到优美子数组中时（作为优美子数组最左侧奇数出现时），可贡献的优美子数组数目ansCnt[i]
     *            或者说是每个奇数左侧的“附属偶数”有多少个 = 左侧实际附属偶数数目 + 1（0个偶数也会贡献一种组合）
     *         2. 对每个数字（含奇数和偶数）计算ansCnt[currentOddCnt - k]是否存在，如果存在则累加到ans中；
     * 
     * 关键变量说明：
     *          oddCnt：到目前为止，奇数的累计个数
     *          ansCnt数组：第“oddCnt+1”个奇数可贡献的“‘潜在’优美子数组”的个数
     *          边界条件：ansCnt[0] = 1  零个偶数也是1种组合
     * 
     * 
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     */
    public int numberOfSubarrays(int[] nums, int k) {
        int n = nums.length, ans = 0, currentOddCnt = 0, ansCnt[] = new int[n + 1];
        ansCnt[0] = 1;

        for (int i = 0; i < n; i++) {
            currentOddCnt += (nums[i] & 1);  // 到目前，累计遇到的奇数数量
            // 总奇数数目超过k时，遇到一个新数字（可以是偶数或奇数），都将原来“潜在”的解数目加入正式解数目中
            ans += currentOddCnt >= k ? ansCnt[currentOddCnt - k] : 0;
            ansCnt[currentOddCnt] += 1;  // 将当前累计奇数对应的“解数目”加1
        }

        return ans;
    }
}
// @lc code=end

