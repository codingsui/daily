package cn.syl.swo.newa;

/**
 *
 *给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，
 * 1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。
 * 请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 *
 *
 */
public class Solution46 {

    public int translateNum(int nums) {
        String str = String.valueOf(nums);
        int[] dp = new int[str.length()];
        dp[0] = dp[1] = 1;
        for (int i = 2; i <= str.length(); i++) {
            String tmp = str.substring(i-2,i);
            if (tmp.compareTo("10") >= 0 && tmp.compareTo("25") <= 0){
                dp[i] = dp[i-1] + dp[i-2];
            }else {
                dp[i] = dp[i-1];
            }
        }
        return dp[nums];
    }
    public int translateNum2(int nums) {
        String str = String.valueOf(nums);
        int a = 1,b = 1;
        for (int i = 2; i <= str.length(); i++) {
            String tmp = str.substring(i-2,i);
            int c = tmp.compareTo("10") >= 0 && tmp.compareTo("25") <= 0 ? a + b : a;
            b = a;
            a = c;
        }
        return a;
    }
}
