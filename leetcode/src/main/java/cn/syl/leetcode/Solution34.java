package cn.syl.leetcode;

import org.junit.Assert;
import org.junit.Test;

public class Solution34 {

    @Test
    public void test() {
        Solution34 s = new Solution34();
        int[] nums1 = {1,2,3,4,5,6,7};
        int[] res = s.searchRange(nums1, 8);
        Assert.assertEquals(-1, res[0]);
        Assert.assertEquals(-1, res[1]);

        res = s.searchRange(nums1, 7);
        Assert.assertEquals(6, res[0]);
        Assert.assertEquals(6, res[1]);
    }

    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1,-1};
        if (nums == null || nums.length == 0) {
            return res;
        }
        int left = getLeft(nums, target);
        int right = getRight(nums, target);
        return new int[]{left, right};
    }

    public int getLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] >= target) {
                right = mid - 1;
            }
        }
        return (left >= nums.length || nums[left] != target) ? -1 : left;
    }

    public int getRight(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }
        }
        return (right < 0 || nums[right] != target) ? -1 : right;
    }
}
