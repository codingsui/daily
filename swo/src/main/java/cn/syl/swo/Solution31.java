package cn.syl.swo;

/**
 * 输入一个整数n，求从1到n这n个整数的十进制表示中1出现的次数。
 * 例如输入12，从1到12这些整数中包含1的数字有1，10，11和12，1一共出现了5次。
 */
public class Solution31 {

    public int solution(int n){
        int[] dp = new int[n+1];
        dp[0]=0;
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i-1] + getCount(i);
        }
        return dp[n];
    }

    private int getCount(int n) {
        String str = String.valueOf(n);
        return str.length() - str.replaceAll("1","").length();
    }
}
