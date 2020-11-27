#
# @lc app=leetcode.cn id=15 lang=python3
#
# [15] 三数之和
#
# https://leetcode-cn.com/problems/3sum/description/
#
# algorithms
# Medium (30.07%)
# Likes:    2780
# Dislikes: 0
# Total Accepted:    370.5K
# Total Submissions: 1.2M
# Testcase Example:  '[-1,0,1,2,-1,-4]'
#
# 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0
# ？请你找出所有满足条件且不重复的三元组。
# 
# 注意：答案中不可以包含重复的三元组。
# 
# 
# 
# 示例：
# 
# 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
# 
# 满足要求的三元组集合为：
# [
# ⁠ [-1, 0, 1],
# ⁠ [-1, -1, 2]
# ]
# 
# 
#

# @lc code=start
class Solution:
    def threeSum1(self, nums: List[int]) -> List[List[int]]:
        """
        同18.四数之和为target解法

        4数之和、3数之和、N数之和统一解法：
        先排序、再reduce到只剩2数之和、2数之和使用双指针来处理

        【Tip】 排序+双指针

        Time Complexity: O(n^2)
        Space Complexity: O(n^2)
        """
        def findNSum(l: int, r: int, target: int, N: int, tmp_ans: List[int], ans: List[int]):
            if r - l + 1 < N or N < 2 or target < nums[l] * N or target > nums[r] * N: # 早停 early termination
                return
            
            if N == 2: # 双指针夹逼two pointers solve sorted 2-sum problem
                while l < r:
                    s = nums[l] + nums[r]
                    if s == target:
                        ans.append(tmp_ans + [nums[l], nums[r]])
                        l += 1
                        while l < r and nums[l] == nums[l - 1]: # 跳过连续相同值skip same value
                            l += 1
                    elif s < target:
                        l += 1
                    else:
                        r -= 1
            else: # 递归recursively reduce N
                for i in range(l, r + 1):
                    if i == l or (i > l and nums[i - 1] != nums[i]):
                        findNSum(i + 1, r, target - nums[i], N - 1, tmp_ans + [nums[i]], ans)
        
        nums.sort()
        ans = []
        findNSum(0, len(nums) - 1, 0, 3, [], ans)
        return ans

# @lc code=end

