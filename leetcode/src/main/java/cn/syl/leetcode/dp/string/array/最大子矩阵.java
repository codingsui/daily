package cn.syl.leetcode.dp.string.array;

public class 最大子矩阵 {

    public int[] getMaxMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] b = new int[n];
        int sum = 0;
        int[] location = new int[4];
        int lx = 0,ly = 0;
        int max = matrix[0][0];
        //矩阵的从上到下开始遍历
        for (int i = 0; i < m; i++) {
            //矩阵的顶变了那么就要重新初始化一维数组
            for (int j = 0; j < n; j++) {
                b[j] = 0;
            }
            //开始对列进行合并到一维数组 将二维数组问题转换为一维数组问题，这样就类比最大子序列和求解
            //从矩阵顶开始不断遍历子矩阵的下边，从i到N-1，不断增加子矩阵的高
            for (int j = i; j < m; j++) {
                sum = 0;
                //转化为求一维数组b的最大和
                for (int k = 0; k < n; k++) {
                    b[k] += matrix[j][k];
                    //继续累增
                    if (sum > 0){
                        sum += b[k];
                    }
                    //重新开始标记位置
                    else {
                        lx = i;
                        ly = k;
                        sum = b[k];
                    }
                    if (max < sum){
                        max = sum;
                        location[0] = lx;
                        location[1] = ly;
                        location[2] = j;
                        location[3] = k;
                    }

                }
            }
        }
        return location;
    }

    public static void main(String[] args) {
        System.out.println(new 最大子矩阵().getMaxMatrix(new int[][]{{1}, {-7}}));
    }
}
