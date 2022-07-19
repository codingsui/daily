package cn.syl.leetcode.erfen;

import org.junit.Test;

import java.util.Arrays;

public class Solution53 {
    @Test
    public void test() {

    }


    public int missingNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (i != nums[i]) {
                return i;
            }
        }
        return nums.length;
    }

    public int missingNumber2(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > mid) {
                right = mid - 1;
            } else if (nums[mid] <= mid) {
                left = mid + 1;
            }
        }
        return left;
    }

    public int missingNumber3(int[] nums) {
        int i = 0, j = nums.length - 1;
        while(i <= j) {
            int m = (i + j) / 2;
            if(nums[m] == m) i = m + 1;
            else j = m - 1;
        }
        return i;
    }


}
