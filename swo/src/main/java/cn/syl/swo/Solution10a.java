package cn.syl.swo;

/**
 * 剑指 Offer 10- I. 斐波那契数列
 *
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 *
 * 示例 1：
 *
 * 输入：n = 2
 * 输出：2
 * 示例 2：
 *
 * 输入：n = 7
 * 输出：21
 * 提示：
 *
 */
public class Solution10a {

    public int fib(int n) {
        if (n == 0 || n == 1){
            return 1;
        }
        int a = 1;
        int b = 2;

        for (int i = 3; i <= n; i++) {
            int tem = b;
            b = (a + b) % 1000000007;
            a = tem;
        }
        return b ;
    }

    public static void main(String[] args) {
        Solution10a s = new Solution10a();
        System.out.println(s.fib(7));
    }
}
