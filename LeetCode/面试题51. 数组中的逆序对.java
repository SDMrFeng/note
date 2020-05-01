class Solution {
    /*
     * 解决方案：通过排序，确定逆序对（归并）
     *    逆序对反应了一个数组的有序程度
     *    逆序度(逆序对数目) = 满有序度 - 有序度
     *    满有序度 = n*(n-1)/2
     *    排序的过程就是减少逆序度、增加有序度，直至有序度 = 满有序度；
     * 
     * 暴力解法: 时间复杂度：O(N^2)，空间复杂度O(1),
     *        缺点：每次比较的结果，不管是逆序还是顺序，对之后的比较没有提供有用信息；
     *
     *
     * 时间复杂度：O()
     * 空间复杂度：O()
     */
    public int reversePairsBruteForce(int[] nums) {
        int ans = 0, len = nums.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (nums[i] > nums[j]) {
                    ans++;
                }
            }
        }

        return ans;
    }

    public int reversePairs(int[] nums) {
        if (nums.length < 2) // 数据元素少，不存在逆序对
            return 0;

        int[] temp = new int[nums.length]; // 归并辅助空间，一次申请，处处使用
        return reversePairs(nums, 0, nums.length - 1, temp);
    }

    private int reversePairs(int[] nums, int left, int right, int[] temp) {
        if (left >= right) // 递归终止条件
            return 0;
        
        int mid = left + (right - left) / 2;  // 左右侧分别归并处理
        int leftPairs = reversePairs(nums, left, mid, temp);
        int rightPairs = reversePairs(nums, mid + 1, right, temp);
        
        if (nums[mid] <= nums[mid + 1]) { // 左右侧整体已经有序时，及时停止
            return leftPairs + rightPairs;
        }

        int mergePairs = mergeAndCountPairs(nums, left, mid, right, temp); // 合并左右两侧
        return leftPairs + rightPairs + mergePairs;
    }

    /**
     * nums[left~mid]有序 nums[mid+1~right]有序
     */
    private int mergeAndCountPairs(int[] nums, int left, int mid, int right, int[] temp) {
        int i = left, j = mid + 1, k = left, rpCount = 0;
        System.arraycopy(nums, left, temp, left, right - left + 1);
        
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                nums[k++] = temp[i++];
            } else {
                nums[k++] = temp[j++];
                rpCount += (mid - i + 1);
            }
        }
        while (i <= mid) {
            nums[k++] = temp[i++];
        }
        while (j < right) {
            nums[k++] = temp[j++];
        }
        return rpCount;
    }
}