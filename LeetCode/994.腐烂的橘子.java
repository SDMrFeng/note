/*
 * @lc app=leetcode.cn id=994 lang=java
 *
 * [994] 腐烂的橘子
 *
 * https://leetcode-cn.com/problems/rotting-oranges/description/
 *
 * algorithms
 * Easy (49.82%)
 * Likes:    147
 * Dislikes: 0
 * Total Accepted:    17.2K
 * Total Submissions: 34.3K
 * Testcase Example:  '[[2,1,1],[1,1,0],[0,1,1]]'
 *
 * 在给定的网格中，每个单元格可以有以下三个值之一：
 * 
 * 
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 
 * 
 * 每分钟，任何与腐烂的橘子（在 4 个正方向上）相邻的新鲜橘子都会腐烂。
 * 
 * 返回直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1。
 * 
 * 
 * 
 * 示例 1：
 * 
 * 
 * 
 * 输入：[[2,1,1],[1,1,0],[0,1,1]]
 * 输出：4
 * 
 * 
 * 示例 2：
 * 
 * 输入：[[2,1,1],[0,1,1],[1,0,1]]
 * 输出：-1
 * 解释：左下角的橘子（第 2 行， 第 0 列）永远不会腐烂，因为腐烂只会发生在 4 个正向上。
 * 
 * 
 * 示例 3：
 * 
 * 输入：[[0,2]]
 * 输出：0
 * 解释：因为 0 分钟时已经没有新鲜橘子了，所以答案就是 0 。
 * 
 * 
 * 
 * 
 * 提示：
 * 
 * 
 * 1 <= grid.length <= 10
 * 1 <= grid[0].length <= 10
 * grid[i][j] 仅为 0、1 或 2
 * 
 * 
 */

// @lc code=start
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.HashMap;
class Solution {
    // 广度遍历解决
    public int orangesRotting1(int[][] grid) {
        int R = grid.length, C = grid[0].length;
        int[][] dirs = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        int minite = 0;

        // 先找到所有腐败的橘子
        Queue<Integer> queue = new ArrayDeque();
        Map<Integer, Integer> depthMap = new HashMap<Integer, Integer>();
        for(int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (2 == grid[r][c]) {
                    int code = r * C + c;
                    queue.add(code);
                    depthMap.put(code, minite);
                }
            }
        }

        // 广度优先遍历
        while (!queue.isEmpty()) {
            int code = queue.remove();
            int r = code / C, c = code % C;
            for (int k = 0; k < 4; k++) {
                int nr = r + dirs[k][0];
                int nc = c + dirs[k][1];
                if (nr >= 0 && nr < R && nc >= 0 && nc <C && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    int nextCode = nr * C + nc;
                    queue.add(nextCode);
                    minite = depthMap.get(code) + 1;
                    depthMap.put(nextCode, minite);
                }
            }
        }

        // 检查是否还存在新鲜的橘子
        for (int[] row : grid) 
            for (int v : row)
                if (1 == v)
                    return -1;

        return minite;
    }
    public int orangesRotting(int[][] grid) {
        int row = grid.length, col = grid[0].length; // 行、列数
        int[][] dirs = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // 腐败的方向
        int minite = 0; // 持续的分钟数

        // 统计最开始腐败的橘子
        Queue<Integer> queueRotten = new ArrayDeque(); // 橘子的坐标x * row + y
        Map<Integer, Integer> depthMap = new HashMap<Integer, Integer>();
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (2 == grid[r][c]) {
                    int pos = r * col + c;
                    queueRotten.add(pos);
                    depthMap.put(pos, minite);
                }
            }
        }

        // 开始广度遍历进行腐败
        while (!queueRotten.isEmpty()) {
            int pos = queueRotten.remove();
            int r = pos / col, c = pos % col;
            for (int i = 0; i < 4; i++) {
                int nr = r + dirs[i][0];
                int nc = c + dirs[i][1];
                if (0 <= nr && nr < row && 0 <= nc && nc < col && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    int nextPos = nr * col + nc;
                    minite = depthMap.get(pos) + 1;
                    queueRotten.add(nextPos);
                    depthMap.put(nextPos, minite);
                }
            }
        }

        // 判断是否还有没腐败的橘子
        for (int[] rowT : grid)
            for (int v : rowT)
                if (1 == v)
                    return -1;

        return minite;
    }
}
// @lc code=end

