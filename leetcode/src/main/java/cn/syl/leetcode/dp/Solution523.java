package cn.syl.leetcode.dp;

/**
 * 523. 连续的子数组和
 * 给定一个包含非负数的数组和一个目标整数 k，编写一个函数来判断该数组是否含有连续的子数组，其大小至少为 2，总和为 k 的倍数，即总和为 n*k，其中 n 也是一个整数。
 *  输入: [23,2,4,6,7], k = 6
 * 输出: True
 * 解释: [2,4] 是一个大小为 2 的子数组，并且和为 6。
 *
 * 输入: [23,2,6,4,7], k = 6
 * 输出: True
 * 解释: [23,2,6,4,7]是大小为 5 的子数组，并且和为 42。
 *
 */
public class Solution523 {
    public boolean checkSubarraySum(int[] nums, int k) {
        int[] dp = new int[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            dp[i+1] = dp[i] + nums[i];
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 2; j <= nums.length; j++) {
               int res = dp[j] - dp[i];
               if (k == 0){
                   if (res == 0){
                       return true;
                   }
               }else{
                   if (res % k == 0){
                       return true;
                   }
               }
            }
        }
        return false;
    }
}
