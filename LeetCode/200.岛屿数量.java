/*
 * @lc app=leetcode.cn id=200 lang=java
 *
 * [200] 岛屿数量
 *
 * https://leetcode-cn.com/problems/number-of-islands/description/
 *
 * algorithms
 * Medium (47.20%)
 * Likes:    417
 * Dislikes: 0
 * Total Accepted:    68K
 * Total Submissions: 142.7K
 * Testcase Example:  '[["1","1","1","1","0"],["1","1","0","1","0"],["1","1","0","0","0"],["0","0","0","0","0"]]'
 *
 * 给定一个由 '1'（陆地）和
 * '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
 * 
 * 示例 1:
 * 
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * 
 * 输出: 1
 * 
 * 
 * 示例 2:
 * 
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * 
 * 输出: 3
 * 
 * 
 */

// @lc code=start
import java.util.Queue;
import java.util.LinkedList;
class Solution {
    /*
     * 此题的注意点：
     *          1. 在遍历中如何表示访问过了，使用的是将‘1’变为‘0’
     */

    // 并查集解决方案
    // 时间复杂度：O(N*M) N和M分别是行数和列数，并查集使用路径压缩、排名结合==>并操作时间复杂度为常数时间
    // 空间复杂度：O(N*M) 并查集数据结构的空间
    public int numIslands1(char[][] grid) {
        if (null == grid || 0 == grid.length) {
            return 0;
        }

        int rowCount = grid.length;
        int colCount = grid[0].length;
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        UnionFind uf = new UnionFind(grid);
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                if ('1' == grid[r][c])
                {
                    grid[r][c] = '0';
                    for (int d = 0; d < dirs.length; d++) {
                        int dx = r + dirs[d][0];
                        int dy = c + dirs[d][1];
                        if (dx >= 0 && dx < rowCount && dy >= 0 && dy < colCount && '1' == grid[dx][dy]) {
                            uf.union(r * colCount + c, dx * colCount + dy);
                        }
                    }
                }
            }
        }
        return uf.getCount();
    }

    class UnionFind {
        int count;  // 集合数量
        int[] parent;  
        int[] rank; // 层次深度

        public UnionFind(char[][] grid) {
            count = 0;
            int rowCount = grid.length;
            int colCount = rowCount > 0 ? grid[0].length : 0;
            parent = new int[rowCount * colCount];
            rank = new int[rowCount * colCount];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    if ('1' == grid[i][j]) {
                        parent[i * colCount + j] = i * colCount + j;
                        count++;
                    }
                    rank[i * colCount + j] = 0;
                }
            }
        }

        public int find(int i) {
            if (i != parent[i]) // 路径压缩，方便之后的find
                parent[i] = find(parent[i]);
            return parent[i];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) { // 尽量降低合并后的层次深度
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                }
                else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                }
                else {
                    parent[rootY] = rootX;
                    rank[rootX] += 1;
                }
                --count;
            }
        }

        public int getCount() {
            return count;
        }
    }

    // 深度优先: 递归版
    // 时间复杂度：O(N*M) 遍历一遍
    // 空间复杂度：O(N*M) 最坏情况(全相连)下递归深度达M*N
    public int numIslands2(char[][] grid) {
        if (null == grid || 0 == grid.length) return 0;

        int rowCount = grid.length;
        int colCount = grid[0].length;
        int countIslands = 0;
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                if ('1' == grid[r][c]) {
                    countIslands++;
                    dfs(grid, r, c);
                }
            }
        }
        return countIslands;
    }

    int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private void dfs(char[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || '0' == grid[r][c]) return;

        grid[r][c] = '0';
        for (int[] dir : dirs) {
            dfs(grid, r + dir[0], c + dir[1]);
        }
    }

    // BFS
    // 时间复杂度：O(M*N)  遍历整个矩阵
    // 空间复杂度：O(min(M, N))  队列的空间，最坏为全为陆地时
    public int numIslands3(char[][] grid) {
        if (null == grid || 0 == grid.length) return 0;

        int countIslands = 0;
        int rowCount = grid.length;
        int colCount = grid[0].length;
        Queue<Integer> queue = new LinkedList<Integer>(); // 格式：r * rowCount + c
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                if ('1' == grid[r][c]) {
                    countIslands++;
                    grid[r][c] = '0';
                    queue.add(r * colCount + c);
                    while(!queue.isEmpty()) {
                        int id = queue.remove();
                        int row = id / colCount, col = id % colCount;
                        for (int[] dir : dirs) {
                            int dx = row + dir[0], dy = col + dir[1];
                            if (dx >= 0 && dx < rowCount && dy >= 0 && dy < colCount && '1' == grid[dx][dy]) {
                                queue.add(dx * colCount + dy);
                                grid[dx][dy] = '0';
                            }
                        }
                    }
                }
            }
        }

        return countIslands;
    }

    /*
     * 解决方案：深度优先遍历DFS
     *         PS: 代码参考国际站光头哥代码
     * 时间复杂度：O(N*M) 遍历一遍矩阵
     * 空间复杂度：O(N*M) 递归栈维护空间
     */
    public int numIslands4(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;

        int ans = 0;
        for(int r = 0; r < grid.length; r++) for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == '1')
                ans += dfsHelper(grid, r, c);
        }
        return ans;
    }

    private int dfsHelper(char[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] == '0') return 0;
        grid[r][c] = '0';
        dfsHelper(grid, r - 1, c);
        dfsHelper(grid, r + 1, c);
        dfsHelper(grid, r, c - 1);
        dfsHelper(grid, r, c + 1);
        return 1;
    }

    /*
     * 解决方案：广度优先遍历BFS
     * 
     * 时间复杂度：
     * 空间复杂度：
     */
    public int numIslands5(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        
        int ans = 0, rowCount = grid.length, colCount = grid[0].length;
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int r = 0; r < rowCount; r++) for (int c = 0; c < colCount; c++) {
            if (grid[r][c] == '1') {
                queue.offer(r * colCount + c);
                ans += 1;
                grid[r][c] = '0';
                while(!queue.isEmpty()) {
                    int position = queue.poll(), rp = position / colCount, cp = position % colCount;
                    for (int[] dir : dirs) {
                        int rd = rp + dir[0], cd = cp + dir[1];
                        if (rd >= 0 && rd < rowCount && cd >= 0 && cd < colCount && grid[rd][cd] == '1') {
                            grid[rd][cd] = '0';
                            queue.offer((rd) * colCount + cd);
                        }
                    }
                }
            }
        }
        return ans;
    }

    // 复习一遍并查集；
    public int numIslands(char[][] grid) {
        if (null == grid || 0 == grid.length) return 0;

        int rowCount = grid.length, colCount = grid[0].length;
        UnionFindNew uf = new UnionFindNew(grid);
        for (int r = 0; r < rowCount; r++) for (int c = 0; c < colCount; c++) {
            if (grid[r][c] == '1') {
                grid[r][c] = '0';
                for (int[] dir : dirs) {
                    int rd = r + dir[0], cd = c + dir[1];
                    if (rd >= 0 && rd < rowCount && cd >= 0 && cd < colCount && grid[rd][cd] == '1'){
                        uf.union(r * colCount + c, rd * colCount + cd);
                    }
                }
            }
        }

        return uf.getCount();
    }

    private class UnionFindNew {
        int ufCount = 0, parent[], rank[];

        public UnionFindNew(char[][] grid) {
            int rowCount = grid.length, colCount = grid[0].length;
            parent = new int[rowCount * colCount];
            rank = new int[rowCount * colCount];
            for (int r = 0; r < rowCount; r++) for (int c = 0; c < colCount; c++) {
                int rcID = r * colCount + c;
                rank[rcID] = 0;
                if (grid[r][c] == '1') {
                    parent[rcID] = rcID;
                    ufCount++;
                }
                    
            }
        }

        public int getCount() {
            return ufCount;
        }

        public void union(int i, int j) {
            int iRoot = find(i);
            int jRoot = find(j);
            if (iRoot != jRoot) {
                if (rank[iRoot] > rank[jRoot]) {
                    parent[jRoot] = iRoot;
                } else if (rank[jRoot] > rank[iRoot]) {
                    parent[iRoot] = jRoot;
                } else {
                    parent[iRoot] = jRoot;
                    rank[jRoot]++;
                }

                ufCount--;
            }
        }

        private int find(int i) {
            if (i != parent[i])
                parent[i] = find(parent[i]);
            return parent[i];
        }
        
    }
}
// @lc code=end

