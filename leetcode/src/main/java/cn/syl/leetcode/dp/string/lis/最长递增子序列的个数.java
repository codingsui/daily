package cn.syl.leetcode.dp.string.lis;

public class 最长递增子序列的个数 {

    public int findNumberOfLIS(int[] nums) {
        if (nums == null || nums.length == 0){
            return -1;
        }
        int len = nums.length ;
        int[] dp = new int[len];
        int[] count = new int[len];
        int max = 1;
        dp[0] = count[0] = 1;
        for (int i = 1; i < len; i++) {
            dp[i] = count[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]){
                    //如果dp[j] + 1 > dp[i] = true 则代表我们第一次找到这个组合，
                    if (dp[j] + 1 > dp[i]){
                        dp[i] = dp[j] + 1;
                        count[i] = count[j];
                    }
                    //如果dp[j] + 1 = dp[i] = true 则代表我们再次找到这个组合，
                    else if (dp[j] + 1 == dp[i]){
                        count[i] += count[j];
                    }
                }
            }
            max = Math.max(max,dp[i]);
        }
        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            if (dp[i] == max){
                res+=count[i];
            }
        }
        return res;
    }
}
