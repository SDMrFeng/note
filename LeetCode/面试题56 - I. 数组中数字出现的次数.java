class Solution {
    /***********************************************************
     * 此题与260 只出现一次的数字  完全一致
     * 
     **********************************************************/
    public int[] singleNumbers1(int[] nums) {
        int xorSum = 0;
        int[] ans = new int[]{0, 0};
        for (int x : nums)
            xorSum ^= x;
        
        int lastBit1 = xorSum & (-xorSum);
        for (int x : nums)
            ans[(x & lastBit1) > 0 ? 0 : 1] ^= x;

        return ans;
    }

    /**
    * 解决方案：
    * 1. 遍历nums，进行异或操作得到所有数字的异或值xorAll = 两个单独出现数字的异或值xorTwoSingle
    * 2. 两个single数字不同，则二进制表示中至少会有一位不同，使用x&(-x)得到xorAll中的最低位的1；
    *  2.1 使用这个最低位的1来区分两个single数字
    *  2.2 使用这个最低位的1来将nums分成两组，每组含一个single数字，其他均为成对数字
    * 3. 对两组数字分别进行异或，两组数字异或的结果分别对应两个single数字
    */
    public int[] singleNumbers(int[] nums) {
        int ans[] = new int[2], xorAll = 0;
        for (int num : nums) 
            xorAll ^= num;
        
        int lastBit1 = (xorAll & (-xorAll));

        for (int num : nums) {
            ans[(num & lastBit1) > 0 ? 1 : 0] ^= num; // 一行替代下面三行
            // if ((num & lastBit1) == 0)
            //     ans[0] ^= num;
            // else
            //     ans[1] ^= num;
        }
        return ans;
    }

    public int[] singleNumber(int[] nums) {
        int bitMask = 0, singleA = 0;
        for (int num : nums)
            bitMask ^= num;
        int diff = bitMask & (-bitMask);
        for (int num : nums)
            if ((diff & num) != 0)
                singleA ^= num;
        return new int[]{singleA, singleA^bitMask};
    }
}