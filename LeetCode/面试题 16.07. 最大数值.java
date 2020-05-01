/*
 * 题目要求：编写一个方法，找出两个数字a和b中最大的那一个。不得使用if-else或其他比较运算符。
 */

class Solution {
    /*
     * 解决方案：同号相减永不溢出，异号相比无脑选正。
     */
    public int maximum1(int a, int b) {
        // 先考虑没有溢出时的情况，计算 b - a 的最高位，依照题目所给提示 keepA = 1 时 a > b，即 b - a 为负
        int keepA = b - a >>> 31;
        // 再考虑 a b 异号的情况，此时无脑选是正号的数字
        int aSign = a >>> 31, bSign = b >>> 31;
        // diffSign = 0 时同号，diffSign = 1 时异号
        int diffSign = aSign ^ bSign;
        // 在异号，即 diffSign = 1 时，(1)使之前算出的 keepA 无效，(2)只考虑两个数字的正负关系：b负号留a，b正号留b
        keepA = (keepA & (diffSign ^ 1)) | (bSign & diffSign);
        return a * keepA + b * (keepA ^ 1);
    }

    /*
     * 解决方案：（1）转化为long类型，（2）作差，判断符号位，（3）根据符号位取值 
     *
     * 首先这道题说了不能使用if-else和比较运算符。
     * 也就是说库函数调用到这两个的也不能用，想max，sort都不能用。然后还有三目运算符更加不能用。
     * 这题怎么做，其实题目的四个提示已经告诉你了。(你们可以自行去看。)
     * 问题其实还剩两个，一个是怎么取出int类型的符号位，另一个是超过int类型怎么办。
     * 后者可以用long来解决，那么前者呢？
     * 这里需要了解一下long或者是int类型了。符号位都是在第一位上的，也就是说long类型右移63位就可以拿到。
     * 其中有一点，正数右移高位补0，但是负数右移高位补1。
     * 这里我放上一个链接，介绍了左移和右移在正数与负数中的情况。
     * https://www.csdn.net/gather_2b/MtzaIg4sOTctYmxvZwO0O0OO0O0O.html
     */
    public int maximum2(int a, int b) {
        int k = (int)((((long)a - (long)b) >> 63) & 1);  // 取做差之后的符号位
        return b * k + a * (k ^ 1);
    }


    /*
     * 解决方案：本质是平均值法： max(a, b) = ((a + b) + abs(a - b)) / 2。
     * 
     *         abs(var) = (var ^ (var >> 63)) - (var >> 63)
     *   
     *  为了回避abs，利用位运算实现绝对值功能。
     *  以int8_t为例：分析运算：(var ^ (var >> 7)) - (var >> 7)
     *  var >= 0: var >> 7 => 0x00，即：(var ^ 0x00) - 0x00，异或结果为var
     *  var < 0: var >> 7 => 0xFF，即：(var ^ 0xFF) - 0xFF，var ^ 0xFF是在对var的全部位取反，-0xFF <=> +1, 
     *           对signed int取反加一就是取其相反数。
     *  举个栗子🌰：var = -3 <=> 0xFD，(var ^ 0xFF) - 0xFF= 0x02 - 0xff= 0x03
     */
    public int maximum(int a, int b) {
        long sum = (long)a + (long)b;
        long diff = (long)a - (long)b;
        long abs_diff = (diff ^ (diff >> 63)) - (diff >> 63);
        return (int)((sum + abs_diff) >> 1);
    }
}