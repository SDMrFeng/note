/*
 * @lc app=leetcode.cn id=33 lang=java
 *
 * [33] 搜索旋转排序数组
 *
 * https://leetcode-cn.com/problems/search-in-rotated-sorted-array/description/
 *
 * algorithms
 * Medium (36.47%)
 * Likes:    542
 * Dislikes: 0
 * Total Accepted:    79.6K
 * Total Submissions: 218.4K
 * Testcase Example:  '[4,5,6,7,0,1,2]\n0'
 *
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 * 
 * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
 * 
 * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
 * 
 * 你可以假设数组中不存在重复的元素。
 * 
 * 你的算法时间复杂度必须是 O(log n) 级别。
 * 
 * 示例 1:
 * 
 * 输入: nums = [4,5,6,7,0,1,2], target = 0
 * 输出: 4
 * 
 * 
 * 示例 2:
 * 
 * 输入: nums = [4,5,6,7,0,1,2], target = 3
 * 输出: -1
 * 
 */

// @lc code=start
class Solution {
    // 二分处理，因为拐点处于数组中间某位置，二分判断时条件复杂一些
    // 原理：1.mid左侧或右侧是递增，如果在此递增区间，则相当于正常二分处理即可；
    //      2.如果不再此递增区间，将另一半区间作为旋转排序数组继续搜索即可。
    public int search1(int[] nums, int target) {
        int hi = nums.length - 1, lo = 0, mid = 0;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) 
                return mid;
            else {
                if (nums[lo] <= nums[mid]) { // 前半部分递增
                    if (target > nums[mid] || target < nums[lo])
                        lo = mid + 1;
                    else
                        hi = mid - 1;
                } else { // 后半部分递增
                    if (target > nums[hi] || target < nums[mid])
                        hi = mid - 1;
                    else
                        lo = mid + 1;
                }
            }
        }

        return -1;
    }

    // 思路2: 正常的传统二分
    //      1. 先找到拐点在数组中的坐标位置rot；
    //      2. 进行二分查找时，每次都加上“rot”作为偏移量，就相当于正常的二分查找；
    public int search2(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1, mid = 0;
        // 二分查找拐点位置
        while (lo < hi) {
            mid = lo +  (hi - lo) / 2;
            if (nums[mid] > nums[hi])
                lo = mid + 1;
            else
                hi = mid;
        }
        int rot = lo; // 拐点位置

        // 二分查找target元素的位置
        // 重点理解：lo、hi、mid仅作为虚拟的搜索参考；
        lo = 0;
        hi = nums.length - 1;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            int readMid =  (mid + rot) % nums.length; // 关键：计算现搜索区间所有值的“中间值”的index
            if (nums[readMid] == target)
                return readMid;
            if (nums[readMid] < target)
                lo = mid + 1;
            else
                hi = mid - 1;
        }

        return -1;
    }

    public int search3(int[] nums, int target) {
        int hi = nums.length - 1, lo = 0, rot = 0, mid = 0;

        while (lo < hi) {
            mid = (lo + hi) / 2;
            if (nums[mid] > nums[hi]) 
                lo = mid + 1;
            else
                hi = mid;
        }
        rot = lo;

        lo = 0;
        hi = nums.length - 1;
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            int readMid = (rot + mid) % nums.length;
            if (nums[readMid] == target)
                return readMid;
            if (nums[readMid] > target)
                hi = mid - 1;
            else
                lo = mid + 1;
        }

        return -1;
    }

    /*
     * 解决方案：二分查找
     * 
     * 时间复杂度：O(logN)
     * 空间复杂度：O(1)
     */
    public int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1, mid = 0;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;

            if (target == nums[mid]) { // 找到目标值
                return mid;
            } else if (nums[lo] <= nums[mid]) { // 左侧递增（意味着右侧为子旋转排序数组）
                if (target >= nums[lo] && target < nums[mid]) // 目标值在左侧递增区间
                    hi = mid - 1;
                else // 目标值在右侧旋转排序数组区间
                    lo = mid + 1;
            } else { // 右侧递增（意味着左侧为子旋转排序数组）
                if (target > nums[mid] && target <= nums[hi]) // 目标值在右侧递增区间
                    lo = mid + 1;
                else 
                    hi = mid - 1;
            }
        }
        return -1;
    }
}
// @lc code=end

