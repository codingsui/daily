package cn.syl.leetcode.dp.string.array;

public class 环形子数组的最大和 {
    public int maxSubarraySumCircular(int[] nums) {
        if (nums.length==1){
            return nums[0];
        }

        int max = nums[0];
        int min = nums[0];
        int sum = nums[0];
        int dp = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum+=nums[i];
            dp = nums[i] + Math.max(dp,0);
            max = Math.max(max ,dp);
        }
        for (int i = 1; i < nums.length-1 ; i++) {
            dp = nums[i] + Math.min(dp,0);
            min = Math.min(min,dp);
        }
        return Math.max(max,sum-min);
    }
}
