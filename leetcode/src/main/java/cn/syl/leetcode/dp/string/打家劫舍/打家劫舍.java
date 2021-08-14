package cn.syl.leetcode.dp.string.打家劫舍;

public class 打家劫舍 {
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (i == 1) {
                dp[i] = Math.max(dp[0], nums[1]);
            } else {
                dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
            }
        }
        return dp[nums.length - 1];
    }
}
