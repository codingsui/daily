package cn.syl.swo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 面试题49 丑数
 * 找出数组中重复的数字。
 *
 *
 * 我们把只包含因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。
 *
 * 输入: n = 10
 * 输出: 12
 * 解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。
 *
 * 思路1：
 * 动态规划
 * 1.确定状态
 * 如果一个树是丑数，那么它必然有某一个丑数*2|3|5组成
 * 假设f(n)为第n个丑数
 * 2.状态转移方程
 * f(n) = Min{f(x)*2,f(y)*3,f(z)*5}
 * 3.初始条件和边界值
 * f(0)=1;
 *
 */
public class Solution49 {

    public int nthUglyNumber(int n) {
        if (n < 1){
            return n;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        int x = 0;
        int y = 0;
        int z = 0;
        int n2 ,n3,n5;
        for (int i = 1; i < n; i++) {
            n2 = dp[x] *2;
            n3 = dp[y] *3;
            n5 = dp[z] *5;
            dp[i] = Math.min(Math.max(n2,n3),n5);
            if (dp[i] == n2){
                x++;
            }
            if (dp[i] == n3){
                y++;
            }
            if (dp[i] == n5){
                z++;
            }
        }
        return dp[n-1];
    }
}
