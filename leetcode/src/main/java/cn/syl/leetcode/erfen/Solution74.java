package cn.syl.leetcode.erfen;

import org.junit.Assert;
import org.junit.Test;

public class Solution74 {

    @Test
    public void test() {
        int[][] array = new int[][]{{1,3,5,7},{10,11,16,20},{23,30,34,60}};
        Solution74 s = new Solution74();
        Assert.assertTrue(s.searchMatrix(array, 11));
        int[][] array1 = new int[][]{{1}};
        Assert.assertFalse(s.searchMatrix(array1, 2));

    }

    /**
     * 两次二分
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = findFistColumn(matrix, target);
        if (row < 0) {
            return false;
        }
        return findRes(matrix[row], target);
    }

    private int findFistColumn(int[][] matrix, int target) {
        int left = 0;
        int right = matrix.length-1;
        int row = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (matrix[mid][0] > target) {
                right = mid - 1;
            } else {
                row = mid;
                left = mid + 1;
            }
        }
        return row;
    }

    private boolean findRes(int[] array, int target) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] > target) {
                right = mid - 1;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 一次二分
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int r = mid / n;
            int c = mid % n;
            if (matrix[r][c] == target) {
                return true;
            } else if (matrix[r][c] < target) {
                left = mid + 1;
            } else {
                right = mid -1;
            }
        }
        return false;
    }

    /**
     * 矩阵坐标
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix3(int[][] matrix, int target) {
        int r = 0;
        int c = matrix[0].length - 1;
        while (r < matrix.length && c >= 0) {
            if (matrix[r][c] < target) {
                r++;
            } else if (matrix[r][c] > target) {
                c--;
            } else {
                return true;
            }
        }
        return false;
    }
}
