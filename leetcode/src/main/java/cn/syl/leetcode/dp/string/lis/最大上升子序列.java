package cn.syl.leetcode.dp.string.lis;

public class One {



    public int lengthOfLIS(int[] nums) {
        if (nums == null||nums.length == 0){
            return -1;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = Integer.MIN_VALUE;
        //dp[i] = max(dp[j]) + 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]){
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
                max = Math.max(max,dp[i]);
            }
        }
        return max;
    }
}




