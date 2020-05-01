import java.util.Map;

/*
 * @lc app=leetcode.cn id=260 lang=java
 *
 * [260] 只出现一次的数字 III
 *
 * https://leetcode-cn.com/problems/single-number-iii/description/
 *
 * algorithms
 * Medium (70.13%)
 * Likes:    189
 * Dislikes: 0
 * Total Accepted:    16.9K
 * Total Submissions: 23.8K
 * Testcase Example:  '[1,2,1,3,2,5]'
 *
 * 给定一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。
 * 
 * 示例 :
 * 
 * 输入: [1,2,1,3,2,5]
 * 输出: [3,5]
 * 
 * 注意：
 * 
 * 
 * 结果输出的顺序并不重要，对于上面的例子， [5, 3] 也是正确答案。
 * 你的算法应该具有线性时间复杂度。你能否仅使用常数空间复杂度来实现？
 * 
 * 
 */

// @lc code=start
class Solution {
    /***********************************************************
     * 此题与面试题56-I 数组中出现的次数  完全一致
     * 
     **********************************************************/
    
    // 哈希表方案
    // 时间复杂度O(n)
    // 空间复杂度O(n)
    public int[] singleNumber1(int[] nums) {
        Map<Integer, Integer> hashmap = new HashMap();
        for (int num : nums) 
            hashmap.put(num, hashmap.getOrDefault(num, 0) + 1);
        int[] result = new int[2];
        int idx = 0;
        for (Map.Entry<Integer, Integer> item : hashmap.entrySet())
            if (item.getValue() == 1) result[idx++] = item.getKey();

        return result;
    }

    // 位运算：两个掩码
    // 思路巧妙： 1. 一个掩码bitMask存储两个出现一次的数字A和B的异或结果A^B
    //          2. 一个掩码diff记录数字A和B二进制表达式中倒数第一个差异位，使用bitMask & (-bitMask)得到
    //          按照差异位，将数组分为两组，将其中一组连续异或得到其中一个数字A；
    //                                 用A ^ bitMask得到另一个数字B；
    // 时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int[] singleNumber(int[] nums) {
        int bitMask = 0; // 存储两个出现一次的数字A和B的异或结果A^B
        for (int num : nums) bitMask ^= num;

        int diff = bitMask & (-bitMask);

        int A = 0;
        for (int num : nums)
            if ((num & diff) != 0)
                A ^= num;

        return new int[]{A, bitMask ^ A};
    }
}
// @lc code=end

