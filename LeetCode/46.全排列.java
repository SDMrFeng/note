/*
 * @lc app=leetcode.cn id=46 lang=java
 *
 * [46] 全排列
 *
 * https://leetcode-cn.com/problems/permutations/description/
 *
 * algorithms
 * Medium (75.88%)
 * Likes:    670
 * Dislikes: 0
 * Total Accepted:    120.1K
 * Total Submissions: 158.2K
 * Testcase Example:  '[1,2,3]'
 *
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 * 
 * 示例:
 * 
 * 输入: [1,2,3]
 * 输出:
 * [
 * ⁠ [1,2,3],
 * ⁠ [1,3,2],
 * ⁠ [2,1,3],
 * ⁠ [2,3,1],
 * ⁠ [3,1,2],
 * ⁠ [3,2,1]
 * ]
 * 
 */

// @lc code=start
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        LinkedList<Integer> track = new LinkedList<Integer>();
        backTrack(nums, track, ans);
        return ans;
    }

    private void backTrack(int[] nums, LinkedList<Integer> track, List<List<Integer>> ans) {
        if (track.size() == nums.length) {
            ans.add(new LinkedList(track));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (track.contains(nums[i])) {
                continue;
            }

            track.add(nums[i]);
            backTrack(nums, track, ans);
            track.removeLast();
        }
    }
}
// @lc code=end

