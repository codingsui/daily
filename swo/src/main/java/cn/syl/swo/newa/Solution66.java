package cn.syl.swo.newa;

/**
 * 表格
 *
 */
public class Solution66 {

    public int[] constructArr(int[] a) {
        if (a == null || a.length == 0){
            return new int[0];
        }
        int[] b = new int[a.length];
        int tmp = 1;
        b[0] = 1;
        //下三角
        for (int i = 1; i < a.length; i++) {
            b[i] = b[i-1] * a[i-1];
        }
        //上三角
        for (int i = a.length - 2; i >=0 ; i--) {
            tmp *= a[i+1];
            b[i] *= tmp;
        }
        return b;
    }
}
