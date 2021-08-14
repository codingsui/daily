package cn.syl.swo.newa;

/**
 *
 *
 */
public class Solution65 {

    public int add(int a, int b) {
        while (b != 0){
            //计算进位
            int c = (a & b) << 1 ;
            a ^= b;
            b = c;
        }
        return a;
    }
}
