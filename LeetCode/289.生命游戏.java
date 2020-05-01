/*
 * @lc app=leetcode.cn id=289 lang=java
 *
 * [289] 生命游戏
 *
 * https://leetcode-cn.com/problems/game-of-life/description/
 *
 * algorithms
 * Medium (69.42%)
 * Likes:    168
 * Dislikes: 0
 * Total Accepted:    27.1K
 * Total Submissions: 36.3K
 * Testcase Example:  '[[0,1,0],[0,0,1],[1,1,1],[0,0,0]]'
 *
 * 根据 百度百科 ，生命游戏，简称为生命，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
 * 
 * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1 即为活细胞（live），或 0
 * 即为死细胞（dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
 * 
 * 
 * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
 * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
 * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
 * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
 * 
 * 
 * 
 * 根据当前状态，写一个函数来计算面板上所有细胞的下一个（一次更新后的）状态。下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
 * 
 * 
 * 
 * 示例：
 * 
 * 输入： 
 * [
 * [0,1,0],
 * [0,0,1],
 * [1,1,1],
 * [0,0,0]
 * ]
 * 输出：
 * [
 * [0,0,0],
 * [1,0,1],
 * [0,1,1],
 * [0,1,0]
 * ]
 * 
 * 
 * 
 * 进阶：
 * 
 * 
 * 你可以使用原地算法解决本题吗？请注意，面板上所有格子需要同时被更新：你不能先更新某些格子，然后使用它们的更新后的值再更新其他格子。
 * 本题中，我们使用二维数组来表示面板。原则上，面板是无限的，但当活细胞侵占了面板边界时会造成问题。你将如何解决这些问题？
 * 
 * 
 */

// @lc code=start
class Solution {
    // 解题思路：(1)复制出原面板，保持一份数据值不受污染；(2)计算每个细胞周围活细胞数量，按照四个规则去更新；
    // 时间复杂度：O(n*m)
    // 空间复杂度：O(n*m)
    public void gameOfLife1(int[][] board) {
        int rn = board.length;
        int cn = board[0].length;

        // 创建复制数组 copyBoard: 记录原始数据，不做修改
        int[][] copyBoard = new int[rn][cn];
        for (int i = 0; i < rn; i++)
            System.arraycopy(board[i], 0, copyBoard[i], 0, cn);
        
        // 8个相邻方位
        int[][] dirs = new int[][]{{-1, -1}, { -1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int row = 0; row < rn; row++){
            for (int col = 0; col < cn; col++){
                int liveNeighbors = 0;
                for (int[] dir : dirs) {
                    int r = row + dir[0], c = col + dir[1];
                    if (r >= 0 && r < rn && c >= 0 && c < cn && copyBoard[r][c] == 1)
                        liveNeighbors++;
                }
                
                if (1 == copyBoard[row][col]) {
                    // 规则1 或 规则3
                    if (liveNeighbors < 2 || liveNeighbors > 3)
                        board[row][col] = 0;
                    // 规则2 保持原装
                    // board[row][col] = 1;
                } 
                else if (3 == liveNeighbors) { // copyBoard[row][col] = 0
                    // 规则4
                    board[row][col] = 1;
                }
            }
        }
    }

    // 解题思路：
    //        小技巧：int有32bit，输入数据“0或1”只用了最后一个bit，利用倒数第2个bit位记录变化后的状态；全部完成后之后统一更新
    // 时间复杂度：O(n*m)
    // 空间复杂度：O(1)
    public void gameOfLife(int[][] board) {
        int rn = board.length;
        int cn = board[0].length;

        // 8个相邻方位
        int[][] dirs = new int[][]{{-1, -1}, { -1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        
        for (int row = 0; row < rn; row++){
            for (int col = 0; col < cn; col++){
                int liveNeighbors = 0;
                for (int[] dir : dirs) {
                    int r = row + dir[0], c = col + dir[1];
                    if (r >= 0 && r < rn && c >= 0 && c < cn &&  1 == (board[r][c] & 1) )
                        liveNeighbors++;
                }
                
                if (1 == (board[row][col] & 1)) {
                    // 规则1 或 规则3
                    // if (liveNeighbors < 2 || liveNeighbors > 3)
                    //     board[row][col] |= 0;
                    // 规则2 保持原状
                    if (2 == liveNeighbors || 3 == liveNeighbors)
                        board[row][col] |= 2;
                }
                else if (3 == liveNeighbors) { // copyBoard[row][col] = 0
                    // 规则4
                    board[row][col] |= 2;
                }
            }
        }
        // 统一更新
        for (int row = 0; row < rn; row++)
            for (int col = 0; col < cn; col++)
                board[row][col] >>= 1; // 右移一位，用第2位bit覆盖最后1bit位
    }
}
// @lc code=end

