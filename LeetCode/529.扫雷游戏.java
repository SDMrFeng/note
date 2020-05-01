import java.util.LinkedList;
import java.util.Queue;

/*
 * @lc app=leetcode.cn id=529 lang=java
 *
 * [529] 扫雷游戏
 *
 * https://leetcode-cn.com/problems/minesweeper/description/
 *
 * algorithms
 * Medium (58.88%)
 * Likes:    49
 * Dislikes: 0
 * Total Accepted:    4.5K
 * Total Submissions: 7.7K
 * Testcase Example:  '[["E","E","E","E","E"],["E","E","M","E","E"],["E","E","E","E","E"],["E","E","E","E","E"]]\n' +
  '[3,0]'
 *
 * 让我们一起来玩扫雷游戏！
 * 
 * 给定一个代表游戏板的二维字符矩阵。 'M' 代表一个未挖出的地雷，'E' 代表一个未挖出的空方块，'B'
 * 代表没有相邻（上，下，左，右，和所有4个对角线）地雷的已挖出的空白方块，数字（'1' 到 '8'）表示有多少地雷与这块已挖出的方块相邻，'X'
 * 则表示一个已挖出的地雷。
 * 
 * 现在给出在所有未挖出的方块中（'M'或者'E'）的下一个点击位置（行和列索引），根据以下规则，返回相应位置被点击后对应的面板：
 * 
 * 
 * 如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 'X'。
 * 如果一个没有相邻地雷的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的方块都应该被递归地揭露。
 * 如果一个至少与一个地雷相邻的空方块（'E'）被挖出，修改它为数字（'1'到'8'），表示相邻地雷的数量。
 * 如果在此次点击中，若无更多方块可被揭露，则返回面板。
 * 
 * 
 * 
 * 
 * 示例 1：
 * 
 * 输入: 
 * 
 * [['E', 'E', 'E', 'E', 'E'],
 * ⁠['E', 'E', 'M', 'E', 'E'],
 * ⁠['E', 'E', 'E', 'E', 'E'],
 * ⁠['E', 'E', 'E', 'E', 'E']]
 * 
 * Click : [3,0]
 * 
 * 输出: 
 * 
 * [['B', '1', 'E', '1', 'B'],
 * ⁠['B', '1', 'M', '1', 'B'],
 * ⁠['B', '1', '1', '1', 'B'],
 * ⁠['B', 'B', 'B', 'B', 'B']]
 * 
 * 解释:
 * 
 * 
 * 
 * 示例 2：
 * 
 * 输入: 
 * 
 * [['B', '1', 'E', '1', 'B'],
 * ⁠['B', '1', 'M', '1', 'B'],
 * ⁠['B', '1', '1', '1', 'B'],
 * ⁠['B', 'B', 'B', 'B', 'B']]
 * 
 * Click : [1,2]
 * 
 * 输出: 
 * 
 * [['B', '1', 'E', '1', 'B'],
 * ⁠['B', '1', 'X', '1', 'B'],
 * ⁠['B', '1', '1', '1', 'B'],
 * ⁠['B', 'B', 'B', 'B', 'B']]
 * 
 * 解释:
 * 
 * 
 * 
 * 
 * 
 * 注意：
 * 
 * 
 * 输入矩阵的宽和高的范围为 [1,50]。
 * 点击的位置只能是未被挖出的方块 ('M' 或者 'E')，这也意味着面板至少包含一个可点击的方块。
 * 输入面板不会是游戏结束的状态（即有地雷已被挖出）。
 * 简单起见，未提及的规则在这个问题中可被忽略。例如，当游戏结束时你不需要挖出所有地雷，考虑所有你可能赢得游戏或标记方块的情况。
 * 
 */

// @lc code=start
class Solution {
    public char[][] updateBoard1(char[][] board, int[] click) {
        int rowN = board.length, colN = board[0].length;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.add(click);

        while(!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0], col = cell[1];

            if ('M' == board[row][col]){
                board[row][col] = 'X';
                return board;
            }
            else {
                // 计算周围地雷个数
                int count = 0;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (0 == i && 0 == j) continue; // 原位置
                        int nr = row + i, nc = col + j;
                        if (nr < 0 || nr >= rowN || nc < 0 || nc >= colN) continue;
                        if (board[nr][nc] == 'M' || board[nr][nc] == 'X') count++;
                    }
                }

                if (count > 0)
                    board[row][col] = (char)(count + '0');
                else {
                    board[row][col] = 'B';
                    for (int i = -1; i < 2; i++) {
                        for (int j = -1; j < 2; j++) {
                            if (0 == i && 0 == j) continue; // 原位置
                            int nr = row + i, nc = col + j;
                            if (nr < 0 || nr >= rowN || nc < 0 || nc >= colN) continue;
                            if (board[nr][nc] == 'E') updateBoard(board, new int[]{nr, nc});
                        }
                    } 
                }
                
            }
        }

        return board;
    }

    int[][] dirs = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1},{1, 0}, {1, 1}}; // 周围八个扩展方向

    public char[][] updateBoard(char[][] board, int[] click) {
        int height = board.length, width = board[0].length; // 行、列数
        
        // 广度遍历进行扩展；
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.add(click);
        while(!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0], col = cell[1];
            if ('M' == board[row][col]) {
                board[row][col] = 'X';
                return board;
            }
            else {
                // 计算周围雷的数量
                int mCount = 0;
                for (int i = 0; i < 8; i++) {
                    int next_row = row + dirs[i][0], next_col = col + dirs[i][1];
                    System.out.println("i=" + i + "  next_row=" + next_row + "next_col = " + next_col);
                    if (0 <= next_row && next_row < height && 0 <= next_col && next_col < width && ('M' == board[next_row][next_col] || 'X' == board[next_row][next_col])) mCount++;
                } 

                if (mCount > 0) {
                    board[row][col] = (char)('0' + mCount);
                }
                else {
                    board[row][col] = 'B';
                    for (int i = 0; i < 8; i++) {
                        int next_row = row + dirs[i][0], next_col = col + dirs[i][1];
                        if (0 <= next_row && next_row < height && 0 <= next_col && next_col < width && 'E' == board[next_row][next_col]) updateBoard(board, new int[]{next_row, next_col});
                    }                
                }
            }
        }

        return board;
    }
}
// @lc code=end

