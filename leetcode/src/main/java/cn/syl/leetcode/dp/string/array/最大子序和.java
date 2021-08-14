package cn.syl.leetcode.dp.string.array;

public class 最大子序和 {
    public int maxSubArray(int[] nums) {
        if (nums.length == 1){
            return nums[0];
        }
        int[] dp = new int[nums.length];
        int max = nums[0];
        dp[0] = nums[0];
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(dp[i-1],0) + nums[i];
            max = Math.max(dp[i],max);
        }
        return max;
    }
}
