package cn.syl.swo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 面试题04. 二维数组中的查找
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
 *
 *
 */
public class Solution04 {

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return false;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        if (matrix[row-1][col -1] < target){
            return false;
        }
        for (int i = row -1 ; i >= 0 ; i--) {
            int n = getIndex(matrix[i],target);
            if (n != -1){
                return true;
            }
        }
        return false;
    }

    private int getIndex(int[] nums,int target){
        int st = 0;
        int ed = nums.length - 1;
        int mid;
        while (st <= ed){
            mid = (st + ed) / 2;
            if (nums[mid] < target){
                st = mid + 1;
            }else if (nums[mid] > target){
                ed = mid -1;
            }else {
                return mid;
            }
        }
        return -1;
    }


    /**
     * 将矩阵逆时针旋转45度，则发现类似与二叉搜索树
     * https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/solution/mian-shi-ti-04-er-wei-shu-zu-zhong-de-cha-zhao-zuo/
     * @param matrix
     * @param target
     * @return
     */
    public boolean findNumberIn2DArray2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return false;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int r = 0;
        int c = col - 1;
        while (c >= 0 && r <row){
            if (matrix[r][c] == target){
                return true;
            }if (matrix[r][c] > target){
                c--;
            }else {
                r++;
            }
        }
        return false;
    }

}
