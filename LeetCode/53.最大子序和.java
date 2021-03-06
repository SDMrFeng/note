/*
 * @lc app=leetcode.cn id=53 lang=java
 *
 * [53] 最大子序和
 *
 * https://leetcode-cn.com/problems/maximum-subarray/description/
 *
 * algorithms
 * Easy (49.53%)
 * Likes:    1707
 * Dislikes: 0
 * Total Accepted:    178.2K
 * Total Submissions: 358.7K
 * Testcase Example:  '[-2,1,-3,4,-1,2,1,-5,4]'
 *
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 
 * 示例:
 * 
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 * 
 * 
 * 进阶:
 * 
 * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
 * 
 */

// @lc code=start
class Solution {
    // 暴力：组合枚举
    public int maxSubArray1(int[] nums) {
        int sumMax = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum > sumMax)
                    sumMax = sum;
            }
        }

        return sumMax;
    }

    // 如果当前subArray的和<=0了，则可以重新开始计数
    public int maxSubArray(int[] nums) {
        int sumMax = Integer.MIN_VALUE;
        int subArraySum = 0;
        for (int num : nums) {
            subArraySum += num;
            if (subArraySum > sumMax)
                sumMax = subArraySum;
            if (subArraySum < 0)
                subArraySum = 0;
            
        }

        return sumMax;
    }
}
// @lc code=end

