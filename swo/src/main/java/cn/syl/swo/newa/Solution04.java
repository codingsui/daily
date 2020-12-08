package cn.syl.swo.newa;

/**
 * 剑指 Offer 04. 二维数组中的查找
 *
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，
 * 每一列都按照从上到下递增的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 *
 * [
 *   [1,   4,  7, 11, 15],
 *   [2,   5,  8, 12, 19],
 *   [3,   6,  9, 16, 22],
 *   [10, 13, 14, 17, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 *  给定 target = 5，返回 true。
 *
 *  给定 target = 20，返回 false。
 *
 */
public class Solution04 {

    /**
     * 将矩阵逆时针旋转45度，则发现类似与二叉搜索树
     * https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof/solution/mian-shi-ti-04-er-wei-shu-zu-zhong-de-cha-zhao-zuo/
     * @param matrix
     * @param target
     * @return
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return false;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int i = cols - 1;
        int j = 0;
        while (i >= 0 & j < rows){
            int tmp = matrix[i][j];
            if (target < tmp){
                i--;
            }else if (target > tmp){
                j++;
            }else {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution04 s = new Solution04();
        int[][] m = new int[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = i+j;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(m.length);
        System.out.println(m[0].length);
        System.out.println(m.toString());
    }

}
