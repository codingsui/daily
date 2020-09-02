package cn.syl.swo;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *剑指 Offer 47. 礼物的最大价值
 *'
 * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
 *
 *  
 *
 * 示例 1:
 *
 * 输入:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 12
 * 解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
 */
public class Solution47 {
    /**
     * 动态规划
     * f(i,j) = max(f(i-1,j),f(i,j-1)) + grid(i,j);
     * i = 0,j=0 起始点：grid[i][j]
     * i=0,j!=0 第一列
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        if (grid == null){
            return -1;
        }
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0  && j == 0){
                    dp[i][j] = grid[i][j];
                }else if (i == 0 && j != 0){
                    dp[i][j] = dp[i][j-1] + grid[i][j];
                }else if ( i != 0 && j == 0){
                    dp[i][j] = dp[i-1][j] + grid[i][j];
                }else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]) + grid[i][j];
                }
            }
        }
        return dp[grid.length-1][grid[0].length -1];
    }

    public static void main(String[] args) {

    }
}
