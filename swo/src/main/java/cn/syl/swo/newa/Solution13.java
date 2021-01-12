package cn.syl.swo.newa;

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
        boolean[][] visited = new boolean[m][n];
        return df(0,0,visited,k);
    }

    private int df(int i, int j, boolean[][] visited, int k) {
        if (i >= visited.length || j >= visited[0].length || i<0 || j < 0 || visited[i][j] || cols(i,j) > k){
            return 0;
        }
        visited[i][j] = true;
        return df(i+1,j,visited,k) + df(i,j+1,visited,k) + 1;
    }

    private int cols(int m,int n){
        int sum = 0;
        while (m != 0){
            sum += m % 10;
            m /= 10;
        }
        while (n != 0){
            sum += n % 10;
            n /= 10;
        }
        return sum;
    }


    /**
     * bfs
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount2(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0,0});
        int res=0;
        while (!queue.isEmpty()){
            int[] tmp = queue.poll();
            int i = tmp[0];
            int j = tmp[1];
            if (i>=m||j>=n||visited[i][j]|| cols(i,j)>k){
                continue;
            }
            visited[i][j] = true;
            res++;
            queue.add(new int[]{i+1,j});
            queue.add(new int[]{i,j+1});
        }
        return res;
    }
    public static void main(String[] args) {
        System.out.println(new Solution13().movingCount2(1,2,1));
    }

}
