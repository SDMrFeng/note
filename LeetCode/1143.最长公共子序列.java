/*
 * @lc app=leetcode.cn id=1143 lang=java
 *
 * [1143] 最长公共子序列
 *
 * https://leetcode-cn.com/problems/longest-common-subsequence/description/
 *
 * algorithms
 * Medium (58.53%)
 * Likes:    64
 * Dislikes: 0
 * Total Accepted:    10.2K
 * Total Submissions: 17.4K
 * Testcase Example:  '"abcde"\n"ace"'
 *
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列。
 * 
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde"
 * 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
 * 
 * 若这两个字符串没有公共子序列，则返回 0。
 * 
 * 
 * 
 * 示例 1:
 * 
 * 输入：text1 = "abcde", text2 = "ace" 
 * 输出：3  
 * 解释：最长公共子序列是 "ace"，它的长度为 3。
 * 
 * 
 * 示例 2:
 * 
 * 输入：text1 = "abc", text2 = "abc"
 * 输出：3
 * 解释：最长公共子序列是 "abc"，它的长度为 3。
 * 
 * 
 * 示例 3:
 * 
 * 输入：text1 = "abc", text2 = "def"
 * 输出：0
 * 解释：两个字符串没有公共子序列，返回 0。
 * 
 * 
 * 
 * 
 * 提示:
 * 
 * 
 * 1 <= text1.length <= 1000
 * 1 <= text2.length <= 1000
 * 输入的字符串只含有小写英文字符。
 * 
 * 
 */

// @lc code=start
class Solution {

    public int longestCommonSubsequence(String text1, String text2) {
        if (null == text1 || null == text2 || text1.length() == 0 || text2.length() == 0)  return 0;
        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();

        // return LCSRecursive(chars1, chars2, chars1.length - 1, chars2.length - 1);

        // int[][] dp = new int[chars1.length][chars2.length];
        // return LCSRecursiveMem(chars1, chars2, chars1.length - 1, chars2.length - 1, dp);

        // return LCSDP1(chars1, chars2);

        return LCSDP2(chars1, chars2);
    }
    
    // 递归解法：容易想到，但递归层次可能很深，而且存在较多重复计算
    private int LCSRecursive(char[] chars1, char[] chars2, int pos1, int pos2) {
        if (pos1 < 0 || pos2 < 0) return 0;
        if (chars1[pos1] == chars2[pos2])
            return 1 + LCSRecursive(chars1, chars2, pos1 - 1, pos2 - 1);
        else 
            return Math.max(LCSRecursive(chars1, chars2, pos1, pos2 - 1),
            LCSRecursive(chars1, chars2, pos1 - 1, pos2));
    }
    
    // 递归 + 备忘录
    private int LCSRecursiveMem(char[] chars1, char[] chars2, int pos1, int pos2, int[][] dp) {
        if (pos1 < 0 || pos2 < 0) return 0;
        if (dp[pos1][pos2] != 0) return dp[pos1][pos2];

        if (chars1[pos1] == chars2[pos2]) 
            return 1 + LCSRecursiveMem(chars1, chars2, pos1 - 1, pos2 - 1, dp);
        else
            return Math.max(LCSRecursiveMem(chars1, chars2, pos1 - 1, pos2, dp), 
            LCSRecursiveMem(chars1, chars2, pos1, pos2 -1, dp));
    }

    // 动态规划，n * m的备忘录
    private int LCSDP1(char[] chars1, char[] chars2) {
        int length1 = chars1.length;
        int length2 = chars2.length;
        int[][] dp = new int[length1 + 1][length2 + 1]; // 第0行和0列处理边界
        for (int i = 0; i <= length1; i++) {
            for (int j = 0; j <= length2; j++) {
                if (0 == i || 0 == j) dp[i][j] = 0;
                else if (chars1[i - 1] == chars2[j - 1])  // 注意数组的索引，避免越界
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[length1][length2];
    }

    // 动态规划，长度为n的备忘录
    private int LCSDP2(char[] chars1, char[] chars2) {
        int length1 = chars1.length;
        int length2 = chars2.length;
        int[] dp = new int[length2 + 1];
        
        for (int i = 1; i <= length1; i++) {
            int preLinePreCellDPValue = 0;  // DPcell[i - 1][j -1]
            for (int j = 1; j <= length2; j++) {
                int preLineCellDPValue = dp[j]; // DPcell[i][j - 1]
                if (chars1[i - 1] == chars2[j - 1]) {
                    dp[j] = preLinePreCellDPValue + 1;
                }
                else {
                    dp[j] = Math.max(dp[j - 1], preLineCellDPValue);
                }
                preLinePreCellDPValue = preLineCellDPValue;  // i - 1行的j增加，移位
            }
        }

        return dp[length2];
    }
}
// @lc code=end

