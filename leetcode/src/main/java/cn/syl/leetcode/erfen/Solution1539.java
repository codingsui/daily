package cn.syl.leetcode.erfen;

import org.junit.Assert;
import org.junit.Test;

public class Solution1539 {

    @Test
    public void test() {
        Solution1539 s = new Solution1539();
        Assert.assertEquals(9, s.findKthPositive(new int[]{2,3,4,7,11}, 5));
        Assert.assertEquals(6, s.findKthPositive(new int[]{1,2,3,4}, 2));

        Assert.assertEquals(9, s.findKthPositive2(new int[]{2,3,4,7,11}, 5));
        Assert.assertEquals(6, s.findKthPositive2(new int[]{1,2,3,4}, 2));
    }
    public int findKthPositive(int[] arr, int k) {
        int left = 0;
        int right = arr.length -1;
        int num = 1;
        while (left <= right && k != 0) {
            if (arr[left] == num) {
                left++;
            } else {
                k--;
            }
            num++;
        }
        while (k != 0) {
            num++;
            k--;
        }
        return num-1;
    }

    public int findKthPositive2(int[] arr, int k) {
        int lastMiss = -1;//最后缺失的数字
        for (int left = 0, current=1; k!=0; current++){
            if (current == arr[left]) {
                left = left < arr.length -1 ? left+1 : left;
            } else {
                k--;
                lastMiss = current;
            }
        }
        return lastMiss;
    }


}
