#
# @lc app=leetcode.cn id=164 lang=python3
#
# [164] 最大间距
#
# https://leetcode-cn.com/problems/maximum-gap/description/
#
# algorithms
# Hard (55.17%)
# Likes:    299
# Dislikes: 0
# Total Accepted:    40.1K
# Total Submissions: 66K
# Testcase Example:  '[3,6,9,1]'
#
# 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
# 
# 如果数组元素个数小于 2，则返回 0。
# 
# 示例 1:
# 
# 输入: [3,6,9,1]
# 输出: 3
# 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
# 
# 示例 2:
# 
# 输入: [10]
# 输出: 0
# 解释: 数组元素个数小于 2，因此返回 0。
# 
# 说明:
# 
# 
# 你可以假设数组中所有元素都是非负整数，且数值在 32 位有符号整数范围内。
# 请尝试在线性时间复杂度和空间复杂度的条件下解决此问题。
# 
# 
#

# @lc code=start
class Solution:
    def maximumGap0(self, nums: List[int]) -> int: 
        """
        不讲武德
         
        for + max/min 合二为一
        """
        if not nums or len(nums) < 2:
            return 0
        nums.sort()
        return max(nums[i] - nums[i - 1] for i in range(1, len(nums)))

    def maximumGap1(self, nums: List[int]) -> int:        
        """
        桶排序
        【key】桶的大小、桶的个数、“最大间距肯定不在桶内而在桶间”
        序列总长度：N
        值分布区间：[min, max]
        最大间距：maxDistance >= (max-min)/(N-1)  可用反证法证明
        桶的大小/区间大小：d = (max-min)/(N-1)下取整
        桶的个数：(max-min) / d + 1   “+1”保证数组的最大值也能分到一个桶
        【Tip】
        维护每个桶内元素的最大值与最小值。随后，只需从前到后不断比较相邻的桶，
        用后一个桶的最小值与前一个桶的最大值之差作为两个桶的间距，最终就能得到所求的答案。

        注意边界：桶大小下取整、桶个数下取整+1

        Time Complexity: O(n)
        Space Complexity: O(n)
        """
        if not nums or len(nums) < 2:
            return 0
        
        # 计算桶的大小、个数
        max_, min_ = max(nums), min(nums)
        max_gap = 0
        bucket_width = max(1, (max_ - min_) // (len(nums) - 1))
        buckets = [[] for _ in range((max_ - min_) // bucket_width + 1)]

        # 将数值放入桶中
        for num in nums:
            idx = (num - min_) // bucket_width
            buckets[idx].append(num)
        
        prev_max = float('inf')
        for bucket in buckets:
            if bucket:
                if prev_max != float('inf'):
                    max_gap = max(max_gap, min(bucket) - prev_max)
                prev_max =max(bucket) 

        return max_gap

    
    def maximumGap2(self, nums: List[int]) -> int:        
        """
        桶排序
        【key】桶的大小、桶的个数、“最大间距肯定不在桶内而在桶间”
        序列总长度：N
        值分布区间：[min, max]
        最大间距：maxDistance >= (max-min)/(N-1)  可用反证法证明
        桶的大小/区间大小：d = (max-min)/(N-1)下取整
        桶的个数：(max-min) / d + 1   “+1”保证数组的最大值也能分到一个桶
        【Tip】
        维护每个桶内元素的最大值与最小值。随后，只需从前到后不断比较相邻的桶，
        用后一个桶的最小值与前一个桶的最大值之差作为两个桶的间距，最终就能得到所求的答案。

        注意边界：桶大小下取整、桶个数下取整+1

        Time Complexity: O(n)
        Space Complexity: O(n)
        """
        if not nums or len(nums) < 2:
            return 0
        
        # 计算桶的大小、个数
        max_, min_ = max(nums), min(nums)
        max_gap = 0
        bucket_width = max(1, (max_ - min_) // (len(nums) - 1))
        buckets = [[-1, float('inf')] for _ in range((max_ - min_) // bucket_width + 1)]

        # 将数值放入桶中(其实桶并不真正放值，而是仅仅记录该桶范围内的最大最小值即可)
        for num in nums:
            idx = (num - min_) // bucket_width
            if buckets[idx][0] == -1 or buckets[idx][0] > num:
                buckets[idx][0] = num
            if buckets[idx][1] == float('inf') or buckets[idx][1] < num:
                buckets[idx][1] = num

        prev_max = buckets[0][1]
        for i in range(1, len(buckets)):
            if buckets[i][0] != -1:
                if prev_max != float('inf'):
                    max_gap = max(max_gap, buckets[i][0] - prev_max)
                prev_max = buckets[i][1]

        return max_gap
    
    def maximumGap3(self, nums: List[int]) -> int:        
        """
        基数排序 RadixSort LSD（Least significant digital）
        LSD的基数排序适用于位数少的数列，如果位数多的话，使用MSD的效率会比较好。
        【key】
        基数排序是通过“分配”和“收集”过程来实现排序。
            1)分配，先从个位开始，根据位值(0-9)分别放到0~9号桶中（比如53,个位为3，则放入3号桶中）
            2)收集，再将放置在0~9号桶中的数据按顺序放到数组中
            重复(1)(2)过程，从个位到最高位（比如32位无符号整形最大数4294967296，最高位10位）。
        【Tip】
        举例思考扑克牌排序：
          花色顺序：梅花<方块<红心<黑桃
          面值顺序：2<3<4<...<10<J<Q<K<A

        Time Complexity: O(n)
        Space Complexity: O(n)
        平均时间复杂度：O(dn)(d即表示整形的最高位数)
        空间复杂度：O(10n) （10表示0~9，用于存储临时的序列） 
        稳定性：稳定
        """
        if not nums or len(nums) < 2:
            return 0
        
        # 基数排序
        idx_digit = 0
        max_num = max(nums)
        width_num = len(str(max_num))
        while idx_digit < width_num:
            # 分配
            buckets = [[] for _ in range(10)]
            for num in nums:
                buckets[int(num/pow(10, idx_digit)) % 10].append(num)
            # 收集
            nums.clear() # nums = []
            for bucket in buckets:
                nums.extend(bucket)
                # for num in buckets:
                #     nums.append(num)
            idx_digit += 1
        
        # 求间距
        return max(nums[i] - nums[i - 1] for i in range(1, len(nums)))

    def maximumGap4(self, nums: List[int]) -> int:        
        """
        基数排序 RadixSort MSD（Most significant digital）
        LSD的基数排序适用于位数少的数列，如果位数多的话，使用MSD的效率会比较好。
        【key】
        基数排序是通过“分配”和“收集”过程来实现排序。
            1)分配，先从个位开始，根据位值(0-9)分别放到0~9号桶中（比如53,个位为3，则放入3号桶中）
            2)收集，再将放置在0~9号桶中的数据按顺序放到数组中
            重复(1)(2)过程，从个位到最高位（比如32位无符号整形最大数4294967296，最高位10位）。
        【Tip】
        举例思考扑克牌排序：
          花色顺序：梅花<方块<红心<黑桃
          面值顺序：2<3<4<...<10<J<Q<K<A        

        Time Complexity: O(n)
        Space Complexity: O(n)
        平均时间复杂度：O(dn)(d即表示整形的最高位数)
        空间复杂度：O(10n) （10表示0~9，用于存储临时的序列） 
        稳定性：稳定
        """
        if not nums or len(nums) < 2:
            return 0
        
        # 基数排序
        max_num = max(nums)
        exp = 1
        while max_num / exp > 0:
            # 分配
            buckets = [[] for _ in range(10)]
            for num in nums:
                buckets[int(num/exp) % 10].append(num)
            # 收集
            nums = []
            for bucket in buckets:
                nums.extend(bucket)
            
            # 升一位
            exp*=10
        
        # 求间距
        return max(nums[i] - nums[i - 1] for i in range(1, len(nums)))
    

    def maximumGap(self, nums: List[int]) -> int:
        """
        技巧使用
        """
        if len(nums) <= 1: return 0
		 # Convert nums list to reversed bit array list
        nums = [bin(num)[2:][::-1] for num in nums]                                 
        
        for i in range(max(map(len, nums))):
            nums0 = [x for x in nums if i >= len(x) or x[i] == '0']
            nums1 = [x for x in nums if i < len(x) and x[i] == '1']
			# it will only have two buckets (0, 1) in radix sort.
            nums = nums0 + nums1                                                                     
            
	    # convert the number back to base 10 integer. 
        nums = [int(num[::-1], 2) for num in nums]     
		# 1 pass to get the largest gap
        return max(nums[i] - nums[i - 1] for i in range(1, len(nums)))
# @lc code=end

