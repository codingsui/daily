package cn.syl.leetcode.erfen;

import org.junit.Test;

import java.util.Arrays;

public class Solution1608 {

    @Test
    public void test() {

    }

    public int specialArray(int[] nums) {
        Arrays.sort(nums);
        int l = 0;
        int r = nums.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            int target = getNum(mid, nums);
            if (mid < target) {
                l = mid + 1;
            } else if (mid > target) {
                r = mid;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public int getNum(int i, int[] nums) {
        int count = 0;
        for (int t : nums) {
            if (t >= i){
                count++;
            }
        }
        return count;
    }
}
