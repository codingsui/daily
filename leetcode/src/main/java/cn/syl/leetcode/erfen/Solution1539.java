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

        Assert.assertEquals(9, s.findKthPositive3(new int[]{2,3,4,7,11}, 5));
        Assert.assertEquals(6, s.findKthPositive3(new int[]{1,2,3,4}, 2));
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

    public int findKthPositive3(int[] arr, int k) {
        if (arr[0] > k) {
            return k;
        }
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int x = mid > arr.length ? Integer.MAX_VALUE : arr[mid] - mid - 1;
            if (x >= k) {
                right = mid;
            } else if (x < k) {
                left = mid + 1;
            }
        }
        return arr[left-1] + k - (arr[left-1] - (left - 1) - 1);
    }

    public int findKthPositive4(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int x = mid > arr.length ? Integer.MAX_VALUE : arr[mid] - mid - 1;
            if (x >= k) {
                right = mid - 1;
            } else if (x < k) {
                left = mid + 1;
            }
        }
        return k + left;
    }


}
