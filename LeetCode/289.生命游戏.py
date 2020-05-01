#
# @lc app=leetcode.cn id=289 lang=python3
#
# [289] 生命游戏
#
# https://leetcode-cn.com/problems/game-of-life/description/
#
# algorithms
# Medium (69.42%)
# Likes:    168
# Dislikes: 0
# Total Accepted:    27.1K
# Total Submissions: 36.3K
# Testcase Example:  '[[0,1,0],[0,0,1],[1,1,1],[0,0,0]]'
#
# 根据 百度百科 ，生命游戏，简称为生命，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
# 
# 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1 即为活细胞（live），或 0
# 即为死细胞（dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
# 
# 
# 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
# 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
# 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
# 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
# 
# 
# 
# 根据当前状态，写一个函数来计算面板上所有细胞的下一个（一次更新后的）状态。下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
# 
# 
# 
# 示例：
# 
# 输入： 
# [
# [0,1,0],
# [0,0,1],
# [1,1,1],
# [0,0,0]
# ]
# 输出：
# [
# [0,0,0],
# [1,0,1],
# [0,1,1],
# [0,1,0]
# ]
# 
# 
# 
# 进阶：
# 
# 
# 你可以使用原地算法解决本题吗？请注意，面板上所有格子需要同时被更新：你不能先更新某些格子，然后使用它们的更新后的值再更新其他格子。
# 本题中，我们使用二维数组来表示面板。原则上，面板是无限的，但当活细胞侵占了面板边界时会造成问题。你将如何解决这些问题？
# 
# 
#

# @lc code=start
class Solution:
    # 解题思路：卷积
    #         需要做zero padding，进行2维卷积
    # 时间复杂度：O(n*m)
    # 空间复杂度：O(n*m)
    def gameOfLife1(self, board: List[List[int]]) -> None:
        """
        Do not return anything, modify board in-place instead.
        """
        import numpy as np
        rn, cn = len(board), len(board[0])
        # 下面两行做zero padding
        board_exp = np.array([[0 for _ in range(cn + 2)] for _ in range(rn + 2)])
        board_exp[1 : 1 + rn, 1 : 1 + cn] = np.array(board)
        # 设置卷积核
        kernel = np.array([[1, 1, 1], [1, 0, 1], [1, 1, 1]])
        # 开始卷积
        for r in range(1, rn + 1):
            for c in range(1, cn + 1):
                # 统计细胞周围8个位置的状态
                temp_sum = np.sum(kernel * board_exp[r - 1 : r + 2, c - 1 : c + 2])
                # 按照题目规则进行判断
                if board_exp[r, c] == 1:
                    if temp_sum < 2 or temp_sum > 3:
                        board[r - 1][c - 1] = 0
                else:
                    if temp_sum == 3:
                        board[r - 1][c - 1] = 1

    # 解题思路：解题思路：(1)复制出原面板，保持一份数据值不受污染；(2)计算每个细胞周围活细胞数量，按照四个规则去更新；
    # 时间复杂度：O(n*m)
    # 空间复杂度：O(n*m)
    def gameOfLife2(self, board: List[List[int]]) -> None:
        """
        Do not return anything, modify board in-place instead.
        """
        dirs = [(-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1)]
        rn, cn = len(board), len(board[0])

        # 从原数组复制一份到 copy_board
        copy_board = [[board[r][c] for c in range(cn)] for r in range(rn)]

        # 遍历面板每一个格子里的细胞
        for r in range(rn):
            for c in range(cn):
                # 对于每个细胞统计其8个邻居位置里的活细胞数量
                live_neighbors = 0
                for dir in dirs:
                    rr = r + dir[0]
                    cc = c + dir[1]

                    # 邻居活细胞数量
                    if (rr < rn and rr >= 0) and (cc < cn and cc >= 0) and (copy_board[rr][cc] == 1):
                        live_neighbors += 1
                
                # 规则1 或 规则3
                if copy_board[r][c] == 1 and (live_neighbors < 2 or live_neighbors > 3):
                    board[r][c] = 0
                # 规则4
                if copy_board[r][c] == 0 and live_neighbors == 3:
                        board[r][c] = 1
     
    # 解题思路：
    #        小技巧：int有32bit，输入数据“0或1”只用了最后一个bit，利用倒数第2个bit位记录变化后的状态；全部完成后之后统一更新
    # 时间复杂度：O(n*m)
    # 空间复杂度：O(1)
    def gameOfLife(self, board: List[List[int]]) -> None:
        """
        Do not return anything, modify board in-place instead.
        """
        if not board or not board[0]:
            return
        
        m, n = len(board), len(board[0])
        for i in range(m):
            for j in range(n):
                cnt = self.count_alive(board, i, j)
                if board[i][j] and cnt in [2, 3]:
                    board[i][j] = 3
                if not board[i][j] and cnt == 3:
                    board[i][j] = 2
        
        for i in range(m):
            for j in range(n):
                board[i][j] >>= 1
        
    def count_alive(self, board, i, j):
        m, n = len(board), len(board[0])
        cnt = 0
        directions = [(0, 1), (0, -1), (-1, 0), (1, 0),
                    (1, 1), (1, -1), (-1, 1), (-1, -1)]
        
        for dx, dy in directions:
            x, y = i + dx, j + dy
            if x < 0 or y < 0 or x == m or y == n:
                continue
            cnt += board[x][y] & 1
        
        return cnt

        
# @lc code=end

