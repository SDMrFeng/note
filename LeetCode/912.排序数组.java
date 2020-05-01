/*
 * @lc app=leetcode.cn id=912 lang=java
 *
 * [912] 排序数组
 *
 * https://leetcode-cn.com/problems/sort-an-array/description/
 *
 * algorithms
 * Medium (53.05%)
 * Likes:    98
 * Dislikes: 0
 * Total Accepted:    47.7K
 * Total Submissions: 80.5K
 * Testcase Example:  '[5,2,3,1]'
 *
 * 给你一个整数数组 nums，请你将该数组升序排列。
 * 
 * 
 * 
 * 
 * 
 * 
 * 示例 1：
 * 
 * 输入：nums = [5,2,3,1]
 * 输出：[1,2,3,5]
 * 
 * 
 * 示例 2：
 * 
 * 输入：nums = [5,1,1,2,0,0]
 * 输出：[0,0,1,1,2,5]
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= nums.length <= 50000
 * -50000 <= nums[i] <= 50000
 * 
 * 
 */

// @lc code=start
class Solution {
    // 冒泡排序 Bubble Sort
    // 原地排序
    // 稳定排序
    // 时间复杂度：O(n^2)  最坏时间复杂度：O(n^2)
    // 空间复杂度：O(1)
    public int[] sortArray1(int[] nums) {
        for (int i = nums.length - 1; i >= 0; i--)
            for (int j = 0; j < i; j++)
                if (nums[j] > nums[j + 1]) {
                    // 借助中间变量交换两个变量值
                    // int temp = nums[j + 1]; nums[j + 1] = nums[j]; nums[j] = temp;

                    // 位运算交换两个变量值 x = x ^ y; y = x ^ y; x = x ^ y;
                    // nums[j] ^= nums[j + 1]; nums[j + 1] ^= nums[j]; nums[j] ^= nums[j + 1];

                    // 数值相加减交换 x = x + y; y = x - y; x = x - y;
                    nums[j] += nums[j + 1];
                    nums[j + 1] = nums[j] - nums[j + 1];
                    nums[j] = nums[j] - nums[j + 1];
                }
        return nums;
    }

    // 冒泡排序 Bubble Sort 早停模式
    // 原地排序
    // 稳定排序
    // 时间复杂度：O(n^2) 最坏时间复杂度：O(n^2) 最好时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int[] sortArray2(int[] nums) {
        for (int i = nums.length - 1; i >= 0; i--) {
            boolean switchFlag = false;
            for (int j = 0; j < i; j++)
                if (nums[j] > nums[j + 1]) {
                    // 借助中间变量交换两个变量值
                    // int temp = nums[j + 1]; nums[j + 1] = nums[j]; nums[j] = temp;

                    // 位运算交换两个变量值 x = x ^ y; y = x ^ y; x = x ^ y;
                    // nums[j] ^= nums[j + 1]; nums[j + 1] ^= nums[j]; nums[j] ^= nums[j + 1];

                    // 数值相加减交换 x = x + y; y = x - y; x = x - y;
                    nums[j] += nums[j + 1];
                    nums[j + 1] = nums[j] - nums[j + 1];
                    nums[j] = nums[j] - nums[j + 1];

                    switchFlag = true;
                }
            if (!switchFlag) break;
        }
        return nums;
    }

    // 选择排序 Selection Sort
    // 原地排序
    // 不稳定排序
    // 时间复杂度：O(n^2) 最坏时间复杂度：O(n^2) 最好时间复杂度：O(n^2)
    // 空间复杂度：O(1)
    public int[] sortArray3(int[] nums) {
        int maxPos = 0;
        for (int i = nums.length - 1; i > 0; i--) {
            maxPos = 0;
            for (int j = 1; j <= i; j++)
                if (nums[maxPos] < nums[j])
                    maxPos = j;

            if (i != maxPos) {
                // 交换maxPos和i位置的值
                nums[i] = nums[i] ^ nums[maxPos];
                nums[maxPos] = nums[i] ^ nums[maxPos];
                nums[i] = nums[i] ^ nums[maxPos];
            }
        }

        return nums;
    }

    // 插入排序 Insert Sort
    // 原地排序
    // 稳定排序
    // 时间复杂度：O(n^2) 最坏时间复杂度：O(n^2) 最好时间复杂度：O(n)
    // 空间复杂度：O(1)
    public int[] sortArray4(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int insertValue = nums[i];
            int j = i - 1;
            while (j >= 0) {
                if (nums[j] <= insertValue) break;
                nums[j + 1] = nums[j--];
            }
            nums[j + 1] = insertValue;
        }
        return nums;
    }

    // 希尔排序 Shell Sort
    // 原地排序
    // 稳定排序
    // 时间复杂度：O() 最坏时间复杂度：O() 最好时间复杂度：O()
    // 空间复杂度：O()
    public int[] sortArray5(int[] nums) {
        
        return nums;
    }

    // 堆排序 Heap Sort
    // 原地排序
    // 稳定排序
    // 时间复杂度：O() 最坏时间复杂度：O() 最好时间复杂度：O()
    // 空间复杂度：O()
    public int[] sortArray6(int[] nums) {
        
        return nums;
    }

    // 快速排序 Quick Sort
    // 原地排序
    // 稳定排序
    // 时间复杂度：O() 最坏时间复杂度：O() 最好时间复杂度：O()
    // 空间复杂度：O()
    public int[] sortArray7(int[] nums) {
        
        return nums;
    }

    // 归并排序 Merge Sort
    // 原地排序
    // 稳定排序
    // 时间复杂度：O(logN) 最坏时间复杂度：O(logN) 最好时间复杂度：O(logN)
    // 空间复杂度：O(N)
    public int[] sortArray(int[] nums) {
        mergeSort(nums);
        return nums;
    }

    private void mergeSort(int[] nums) {
        if (nums.length < 2) return;
        int[] temp = new int[nums.length]; // 一次申请，处处使用；免得多处申请时，索引位置移位，处理不当容易出错；
        mergeSort(nums, 0, nums.length - 1, temp);
    }

    private void mergeSort(int[] nums, int left, int right, int[] temp) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(nums, left, mid, temp);
        mergeSort(nums, mid + 1, right, temp);
        if (nums[mid] <= nums[mid + 1]) return;
        merge(nums, left, mid, right, temp);
    }

    private void merge(int[] nums, int left, int mid, int right, int[] temp) {
        int i = left, j = mid + 1, k = left;
        System.arraycopy(nums, left, temp, left, right - left + 1);
        while (i <= mid && j <= right) {
            nums[k++] = temp[i] <= temp[j] ? temp[i++] : temp[j++];
        }
        while (i <= mid) {
            nums[k++] = temp[i++];
        }
        while (j <= right) {
            nums[k++] = temp[j++];
        }
    }

    // private void mergeSort(int[] nums) {
    //     int len = nums.length;
    //     if (len < 2)
    //         return;
        
    //     int[] temp = new int[len];
    //     mergeSort(nums, 0, len - 1, temp);
    // }

    // private void mergeSort(int[] nums, int left, int right, int[] temp) {
    //     if (left >= right) {
    //         return;
    //     }
    //     int mid = left + (right - left) / 2;
        
    //     mergeSort(nums, left, mid, temp);
    //     mergeSort(nums, mid + 1, right, temp);

    //     merge(nums, left, mid, right, temp);
    // }

    // // 输入中nums[left~mid]是有序的，nums[mid+1~right]也是有序的；
    // private void merge(int[] nums, int left, int mid, int right, int[] temp) {
    //     int i = left, j = mid + 1;
    //     System.arraycopy(nums, left, temp, left, right - left + 1);
    //     for (int k = left; k <= right; k++) {
    //         if (i == mid + 1) {
    //             nums[k] = temp[j++];
    //         }
    //         else if (j == right + 1) {
    //             nums[k] = temp[i++];
    //         }
    //         else if (temp[i] <= temp[j]) {
    //             nums[k] = temp[i++];
    //         } else {
    //             nums[k] = temp[j++];
    //         }
    //     }
    // }
}
// @lc code=end
