package cn.syl.swo.newa;

/**
 *实现函数double Power(double base, int exponent)，求base的exponent次方。不得使用库函数，同时不需要考虑大数问题。
 *
 *
 *
 */
public class Solution16 {

    /**
     * 当n % 2 == 0 pow(3,8) = pow(8,4) = pow(64,2) = (64*64,1)
     * 当 n % 2 == 1 pow(3,9) = 3 * pow(3,8)
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n == 0 || x == 1){
            return 1;
        }
        double mux = 1;
        long b = n;
        if (b < 0){
            x = 1/x;
            b = -b;
        }
        while (b != 0){
            if ((b & 1) == 1){
                mux *= x;
            }
            x *= x;
            b >>= 1;
        }
        return mux;
    }

    public static void main(String[] args) {
        System.out.println(new Solution16().myPow(2,8));
    }
}
