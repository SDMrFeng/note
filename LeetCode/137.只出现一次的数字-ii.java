/*
 * @lc app=leetcode.cn id=137 lang=java
 *
 * [137] 只出现一次的数字 II
 *
 * https://leetcode-cn.com/problems/single-number-ii/description/
 *
 * algorithms
 * Medium (65.88%)
 * Likes:    286
 * Dislikes: 0
 * Total Accepted:    24.2K
 * Total Submissions: 36.6K
 * Testcase Example:  '[2,2,3,2]'
 *
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现了三次。找出那个只出现了一次的元素。
 * 
 * 说明：
 * 
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 * 
 * 示例 1:
 * 
 * 输入: [2,2,3,2]
 * 输出: 3
 * 
 * 
 * 示例 2:
 * 
 * 输入: [0,1,0,1,0,1,99]
 * 输出: 99
 * 
 */

// @lc code=start
class Solution {

    // 解决方案：位运算
    // 仅当 seen_twice 未变时，改变 seen_once。（位掩码 seen_once 仅保留出现一次的数字，不保留出现三次的数字。）
    // 仅当 seen_once 未变时，改变seen_twice。
    // 
    public int singleNumber1(int[] nums) {
        int once = 0, twice = 0;
        for (int num : nums) {
            // first appearence:
            // add num to seen_once
            // don't add to seen_twice because of presence in seen_once

            // second appearance:
            // remove num from seen_once
            // add num to seen_twice

            // third appearance:
            // don't add to seen_once because of presence in seen_twice
            // remove num from seen_twice
            once = once ^ num & ~twice;
            twice = twice ^ num & ~once;
        }

        return once;
    }

    /**
     * 解决方案：
     * A仅出现一次：once & (~twice)
     * A仅出现两次：twice & (~once)
     * A仅出现三次：tripl & (~twice) & (~once)
     * 
     * A仅出现一次时，要保证once = A， twive = 0， triple = 0；
     * A仅出现两次时，要保证once = 0， twice = A， triple = 0；
     * A仅出现三次时，要保证once = 0， twice = 0， triple = A；
     */
    public int singleNumber(int[] nums) {
        int once = 0, twice = 0, triple = 0;
        for (int num : nums) {
            once = once ^ num & ~twice;
            twice = twice ^ num & ~once;
            // triple = triple ^ num & ~once & ~twice;
        }

        // System.out.println("Once:" + once + "Twice:" + twice + "Triple:" + triple);
        return once;
    }
}
// @lc code=end

