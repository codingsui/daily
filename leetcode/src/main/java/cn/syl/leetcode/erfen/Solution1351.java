package cn.syl.leetcode.erfen;

import org.junit.Test;

public class Solution1351 {

    @Test
    public void test() {

    }

    /**
     * 暴力
     * @param grid
     * @return
     */
    public int countNegatives(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] < 0) {
                    count++;
                }
            }
        }
        return count;
    }


    /**
     * 二分
     * @param grid
     * @return
     */
    public int countNegatives2(int[][] grid) {
        int m = grid.length;
        int count = 0;
        for (int i = 0; i < m; i++) {
            count+=getErIndex(grid[i]);
        }
        return count;
    }

    private int getErIndex(int[] array) {
        int left = 0;
        int right = array.length -1 ;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] >= 0) {
                left = mid + 1;
            } else if (array[mid] < 0) {
                right = mid - 1;
            }
        }
        return array.length-left;
    }

    /**
     * 矩阵
     * @param grid
     * @return
     */
    public int countNegatives3(int[][] grid) {
        int row = grid.length;
        int column = grid[0].length;
        int m = 0;
        int n = column-1;
        int count = 0;
        while (m < row && n >= 0) {
            if (grid[m][n] < 0) {
                count += row - m;
                n--;
            } else {
                m++;
            }
        }
        return count;
    }

}
