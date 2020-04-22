package cn.syl.leetcode.dp;

/**
 * 53最大子序和
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *
 * 1.确定状态
 * f(i)表示已i结尾的最大连续子序列和的值
 * 2.状态转移方程
 * f(i)=max{array[i],f(i-1) + array[i]}
 * 3.初始状态
 *
 */
public class Solution2 {

    public int maxSubArray(int[] nums) {
        if ( nums == null || nums.length == 0){
            return 0;
        }

        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = nums[0];
        int max = dp[0];

        for (int i = 1; i < len; i++) {
            dp[i] = Math.max(nums[i],dp[i-1] + nums[i]);
            max = Math.max(max,dp[i]);
        }

        return max;
    }
}
