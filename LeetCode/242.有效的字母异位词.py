#
# @lc app=leetcode.cn id=242 lang=python3
#
# [242] 有效的字母异位词
#
# https://leetcode-cn.com/problems/valid-anagram/description/
#
# algorithms
# Easy (63.10%)
# Likes:    308
# Dislikes: 0
# Total Accepted:    181.6K
# Total Submissions: 287.4K
# Testcase Example:  '"anagram"\n"nagaram"'
#
# 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
# 
# 示例 1:
# 
# 输入: s = "anagram", t = "nagaram"
# 输出: true
# 
# 
# 示例 2:
# 
# 输入: s = "rat", t = "car"
# 输出: false
# 
# 说明:
# 你可以假设字符串只包含小写字母。
# 
# 进阶:
# 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
# 
#

# @lc code=start
class Solution:
    def isAnagram1(self, s: str, t: str) -> bool:
        """
        解决思路：
        【核心】对字符串所有字符排序，如果相同排之后应该完全一致
        【辅助】
        【尝试理解】
        
        时间复杂度：O(nlogn)  排序的时间复杂度
        空间复杂度：O(1)  排序用的栈空间
        """
        return sorted(s) == sorted(t)

    def isAnagram1(self, s:str, t:str) -> bool:
        """
        解决思路：
        【核心】统计第一个字符串中各字符个数，再遍历第二个字符串，消去对应字符数量，判断是否全部抵消
        【辅助】
        【尝试理解】
        【Tip】 python中某字母char的相对a的位置不能通过char - a得到，而是ord(char) - ord(a)
                ord(c)将字符转为整数，chr(i)将整数转为字符
        
        时间复杂度：O(n) 遍历
        空间复杂度：O(1) 26 常数
        """
        # letters = [0 for _ in range(26)]
        letters = [0] * 26
        for c in s:
            letters[ord(c) - ord('a')] += 1
        for c in t:
            letters[ord(c) - ord('a')] -= 1
        for cnt in letters:
            if cnt != 0:
                return False
        return True

    def isAnagram(self, s: str, t: str) -> bool:
        """
        解决思路：
        【核心】分辨用字典统计两个字符串中各字符个数，进行对比
        【辅助】
        【尝试理解】
        【Tip】 dict.get(key, default_value)
        
        时间复杂度：O(n) 遍历
        空间复杂度：O(1) 26常数
        """
        dicts, dictt = {}, {}
        for c in s:
            dicts[c] = dicts.get(c, 0) + 1
        for c in t:
            dictt[c] = dictt.get(c, 0) + 1
        return dicts == dictt
# @lc code=end

