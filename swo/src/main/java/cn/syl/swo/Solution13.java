package cn.syl.swo;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 剑指 Offer 13. 机器人的运动范围
 *
 * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，
 * 它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。
 * 例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。
 * 请问该机器人能够到达多少个格子？
 *
 *
 * 示例 1：
 *
 * 输入：m = 2, n = 3, k = 1
 * 输出：3
 * 示例 2：
 *
 * 输入：m = 3, n = 1, k = 0
 * 输出：1
 *
 */
public class Solution13 {

    /**
     * 深度优先搜索 DFS
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount(int m, int n, int k) {
        boolean[][] visted = new boolean[m][n];
        return dfs(0,0,k,visted);
    }

    private int dfs(int i, int j, int k, boolean[][] visted) {
        if (i < 0 || j < 0 || i>=visted.length || j>= visted[0].length || k<getNum(i) + getNum(j)||visted[i][j]){
            return 0;
        }
        visted[i][j]=true;
        return dfs(i+1,j,k,visted) + 1  + dfs(i,j+1,k,visted);
    }


    private int getNum(int a){
        int sum = a % 10;
        int tmp = a / 10;
        while (tmp > 0){
            sum += tmp % 10;
            tmp /= 10;
        }
        return sum;
    }

    /**
     * 广度优先搜索 BFS
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount2(int m, int n, int k) {
        boolean[][] visted = new boolean[m][n];
        int res = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0,0});
        while (!queue.isEmpty()){
            int[] t = queue.poll();
            int i = t[0],j = t[1];
            if (i < 0 || j < 0 || i>=m || j >=n || k < getNum(i) + getNum(j) || visted[i][j]) {
                continue;
            }
            visted[i][j] = true;
            res++;
            queue.add(new int[]{i+1,j});
            queue.add(new int[]{i,j+1});
        }
        return res;
    }
}
