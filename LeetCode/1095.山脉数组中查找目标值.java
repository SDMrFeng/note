/*
 * @lc app=leetcode.cn id=1095 lang=java
 *
 * [1095] 山脉数组中查找目标值
 *
 * https://leetcode-cn.com/problems/find-in-mountain-array/description/
 *
 * algorithms
 * Hard (31.06%)
 * Likes:    21
 * Dislikes: 0
 * Total Accepted:    1.9K
 * Total Submissions: 5.5K
 * Testcase Example:  '[1,2,3,4,5,3,1]\n3'
 *
 * （这是一个 交互式问题 ）
 * 
 * 给你一个 山脉数组 mountainArr，请你返回能够使得 mountainArr.get(index) 等于 target 最小 的下标 index
 * 值。
 * 
 * 如果不存在这样的下标 index，就请返回 -1。
 * 
 * 
 * 
 * 何为山脉数组？如果数组 A 是一个山脉数组的话，那它满足如下条件：
 * 
 * 首先，A.length >= 3
 * 
 * 其次，在 0 < i < A.length - 1 条件下，存在 i 使得：
 * 
 * 
 * A[0] < A[1] < ... A[i-1] < A[i]
 * A[i] > A[i+1] > ... > A[A.length - 1]
 * 
 * 
 * 
 * 
 * 你将 不能直接访问该山脉数组，必须通过 MountainArray 接口来获取数据：
 * 
 * 
 * MountainArray.get(k) - 会返回数组中索引为k 的元素（下标从 0 开始）
 * MountainArray.length() - 会返回该数组的长度
 * 
 * 
 * 
 * 
 * 注意：
 * 
 * 对 MountainArray.get 发起超过 100 次调用的提交将被视为错误答案。此外，任何试图规避判题系统的解决方案都将会导致比赛资格被取消。
 * 
 * 为了帮助大家更好地理解交互式问题，我们准备了一个样例
 * “答案”：https://leetcode-cn.com/playground/RKhe3ave，请注意这 不是一个正确答案。
 * 
 * 
 * 
 * 
 * 
 * 
 * 示例 1：
 * 
 * 输入：array = [1,2,3,4,5,3,1], target = 3
 * 输出：2
 * 解释：3 在数组中出现了两次，下标分别为 2 和 5，我们返回最小的下标 2。
 * 
 * 示例 2：
 * 
 * 输入：array = [0,1,2,4,2,1], target = 3
 * 输出：-1
 * 解释：3 在数组中没有出现，返回 -1。
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 3 <= mountain_arr.length() <= 10000
 * 0 <= target <= 10^9
 * 0 <= mountain_arr.get(index) <= 10^9
 * 
 * 
 */

// @lc code=start
/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */
 
class Solution {
    /**
     * 解决方案：多支的二分法(访问次数超了)
     *       1. 取get(mid)和get(mid+1)
     *       2. if get(mid) > get(mid+1) 则左半部分还是山脉数组，右侧递减
     *              ret = 递归找左半部分是否存在target
     *              return ret >= 0 ? ret : 二分处理右半部分
     *       3. else 左侧递增，右半部分还是山脉数组
     *              ret = 二分处理左半部分
     *              return ret >= 0 ? ret : 递归处理右侧山脉部分
     */
    public int findInMountainArray1(int target, MountainArray mountainArr) {
        return findInMountainArray(target, mountainArr, 0, mountainArr.length() - 1);
    }

    private int findInMountainArray(int target, MountainArray mountainArr, int start, int end) {
        if (end >= mountainArr.length()) end = mountainArr.length() - 1;
        if (start < 0) start = 0;
        if (start > end) return -1;

        int mid = start + (end - start) / 2;
        int vMid = mountainArr.get(mid);
        if (start == end && vMid != target)
            return -1;
        int vMidP1 = mountainArr.get(mid + 1);
        if (vMid > vMidP1) { // 左侧山脉数组，右侧递减
            int ret = findInMountainArray(target, mountainArr, start, mid - 1);
            return ret >= 0 ? ret : binarySearchDecrease(target, mountainArr, mid, end);
        } else { // 左侧递增，右侧山脉数组
            int ret = binarySearchIncrease(target, mountainArr, start, mid + 1);
            return ret >= 0 ? ret : findInMountainArray(target, mountainArr, mid + 2, end);
        }
    }

    private int binarySearchIncrease(int target, MountainArray mountainArr, int start, int end) {
        if (end >= mountainArr.length()) end = mountainArr.length() - 1;
        if (start < 0) start = 0;
        if (start > end) return -1;

        int mid = start + (end - start) / 2;
        int vMid = mountainArr.get(mid);
        if (target == vMid) {
            return mid;
        } else if (target > vMid) {
            return binarySearchIncrease(target, mountainArr, mid + 1, end);
        } else {
            return binarySearchIncrease(target, mountainArr, start, mid - 1);
        }
    }

    private int binarySearchDecrease(int target, MountainArray mountainArr, int start, int end) {
        if (end >= mountainArr.length()) end = mountainArr.length() - 1;
        if (start < 0) start = 0;
        if (start > end) return -1;

        int mid = start + (end - start) / 2;
        int vMid = mountainArr.get(mid);
        if (target == vMid) {
            return mid;
        } else if (target < vMid) {
            return binarySearchDecrease(target, mountainArr, mid + 1, end);
        } else {
            return binarySearchDecrease(target, mountainArr, start, mid - 1);
        }
    }

    /**
     * 解决思路：
     * 1. 利用二分查找，找到数组中的 i ，判断元素 i 是否小于 target，若元素 i 小于 target，则直接返回-1
     * 2. 先判断首个元素值是否大于 target ，若大于则说明 target 不在递增区间内。否则对 i 之前的元素做二分查找，找到 target 直接返回
     * 3. 先判断最后一个元素是否大于 target ，若大于则说明 target 不在递减区间内。否则对 i 之后的元素做二分查找，找到 target 直接返回
     * 4. 执行到这一步，说明上面两步都没有找到 target ，则返回-1
     *    下面用 mid 表示 i(峰值)
     *    用 m 表示 target(目标值) 的下标
     * 
     * 时间复杂度：O(logN)
     * 空间复杂度：O(1)
     */
    public int findInMountainArray2(int target, MountainArray mountainArr) {
        int len = mountainArr.length();
        int left = 0;
        int right = len - 1;
        int mid = left + (right - left) / 2;
        while(left <= right){
            // 若 mid 前一个元素小于 mid 元素，说明 mid 暂时处于递增区间
            if(mountainArr.get(mid - 1) < mountainArr.get(mid)){
                // 若 mid 后一个元素大于 mid 元素，确定 mid 一定处于递增区间
                if(mountainArr.get(mid) < mountainArr.get(mid + 1)){
                    left = mid + 1;
                }else{
                    // 否则 mid 元素为峰值元素
                    break;
                }
            }else{
                // 如果 mid 的前一个元素大于 mid 元素，则 mid 一定处于单调递减区间
                right = mid - 1;
            }
            mid = left + (right - left) / 2;
            // 当 mid 指向数组首尾两个元素时，为了满足数组为山脉数组，
            //           则峰值必定出现在第 2 个或者倒数第 2 个元素
            if(mid == 0 || mid == len - 1){
               mid = mid == 0 ? 1 : len - 2;
               break;
            }
        }
        // 走到这一步已经找到了山脉数组的峰值
        if(mountainArr.get(mid) < target){
            // 若数组峰值都小于目标值，则直接返回-1
            return -1;
        }
        // 首个元素不大于 target 时，在峰值之前的递增区间内寻找 target 。 否则说明递增区间不存在 target
        if(mountainArr.get(0) <= target){
            // 左半部分递增区间内，l 指针指向0， r 指针指向峰值
            int l = 0;
            int r = mid;
            int m = l + (r - l) / 2;
            while(l <= r){
                // 递增区间内，若 中间值 小于 目标值，则往右半部分查找 目标值
                if(mountainArr.get(m) < target){
                    l = m + 1;
                // 递增区间内，若 中间值 大于 目标值，则往左半部分查找 目标值
                }else if(mountainArr.get(m) > target){
                    r = m - 1;
                }else{
                    // 中间值 等于 目标值，直接返回
                    return m;
                }
                m = l + (r - l) / 2;
            }
        }
        // 最后一个元素不大于 target 时，在峰值之后的递减区间内寻找 target 。 否则说明递减区间不存在target
        if(mountainArr.get(len - 1) <= target){
            // 右半部分递减区间内， l 指针指向峰值， r 指针指向最后一个元素
            int l = mid;
            int r = len - 1;
            int m = l + (r - l) / 2;
            while(l <= r){
                // 递减区间内，若 中间值 小于 目标值，则往左半部分查找 目标值
                if(mountainArr.get(m) < target){
                    r = m - 1;
                // 递减区间内，若 中间值 大于 目标值，则往右半部分查找 目标值
                }else if(mountainArr.get(m) > target){
                    l = m + 1;
                }else{
                    // 中间值 等于 目标值，直接返回
                    return m;
                }
                m = l + (r - l) / 2;
            }
        }
        // 若存在target，则在上面两个判断已经return，走到这一步说明数组不存在 target
        return -1;
    }


    /**
     * 三遍二分查找： 国际站精简代码
     * 
     */
    public int findInMountainArray(int target, MountainArray A) {
        int n = A.length(), l, r, m, peak = 0;
        // find index of peak
        l  = 0;
        r = n - 1;
        while (l < r) {
            m = (l + r) / 2;
            if (A.get(m) < A.get(m + 1))
                l = peak = m + 1;
            else
                r = m;
        }
        // find target in the left of peak
        l = 0;
        r = peak;
        while (l <= r) {
            m = (l + r) / 2;
            if (A.get(m) < target)
                l = m + 1;
            else if (A.get(m) > target)
                r = m - 1;
            else
                return m;
        }
        // find target in the right of peak
        l = peak;
        r = n - 1;
        while (l <= r) {
            m = (l + r) / 2;
            if (A.get(m) > target)
                l = m + 1;
            else if (A.get(m) < target)
                r = m - 1;
            else
                return m;
        }
        return -1;
    }
}
// @lc code=end

