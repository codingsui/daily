package cn.syl.leetcode;

import org.junit.Assert;
import org.junit.Test;

public class Solution69 {

    @Test
    public void test() {
        Solution69 s = new Solution69();
        Assert.assertEquals(2, s.mySqrt(4));
        Assert.assertEquals(2, s.mySqrt(5));
        Assert.assertEquals(46339, s.mySqrt(2147395599));
    }

    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }
        int left = 1;
        int right = x / 2;
        int idx = 1;
        while (left <= right) {
            int tmp = left + (right - left) /2;
            long mid = (long)tmp * (long)tmp;
            if (mid < x) {
                idx = tmp;
                left = tmp + 1;
            } else if (mid > x) {
                right = tmp - 1;
            } else {
                return tmp;
            }
        }
        return idx;
    }

    public int mySqrt2(int x) {
        int l = 0, r = x, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }


}
