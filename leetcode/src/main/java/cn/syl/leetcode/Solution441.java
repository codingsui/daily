package cn.syl.leetcode;

import org.junit.Assert;
import org.junit.Test;

public class Solution441 {

    @Test
    public void test() {
        new Solution441().arrangeCoins(5);
    }

    public int arrangeCoins(int n) {
        int left = 1;
        int right = n;
        while (left <= right) {
            int mid = (right - left)/2 + left;
            long m = (long) mid * (mid + 1) / 2;
            if (m < n) {
                left = mid + 1;
            } else if (m > n) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return right;
    }

}
