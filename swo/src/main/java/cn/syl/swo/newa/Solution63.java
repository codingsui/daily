package cn.syl.swo.newa;

/**
 *
 *
 */
public class Solution63 {

    public int maxProfit(int[] prices) {
        /*
            dp[i]表示第i天可以获取的最大收益
            dp[i] = Math.max(dp[i-1],今日价格-最小买入);
         */
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int price:prices) {
            min = Math.min(price,min);
            max = Math.max(max,price - min);
        }
        return max;
    }

}
