package cn.syl.leetcode.dp;


import org.junit.Assert;
import org.junit.Test;

public class Solution523Test {

    @Test
    public void checkSubarraySum() {
        Solution523 s = new Solution523();
        int[] nums = {23,2,6,4,7};
        int k = 6;
        Assert.assertTrue(s.checkSubarraySum(nums,k));
    }
}
