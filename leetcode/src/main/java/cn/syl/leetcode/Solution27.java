package cn.syl.leetcode;

import org.junit.Assert;
import org.junit.Test;

public class Solution27 {

    @Test
    public void test() {
        Assert.assertEquals(2, new Solution27().removeElement(new int[]{3,2,2,3}, 3));
        Assert.assertEquals(2, new Solution27().removeElement2(new int[]{3,2,2,3}, 3));
    }

    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int size = nums.length;
        for (int i = 0; i < size; i++) {
            if (nums[i] == val) {
                for (int j = i; j < size-1; j++) {
                    nums[j] = nums[j+1];
                }
                i--;
                size--;
            }
        }
        return size;
    }

    /**
     * 双指针，最少移动元素
     * @param nums
     * @param val
     * @return
     */
    public int removeElement2(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int right = nums.length-1;
        int left = 0;
        while (left <= right) {
            if (nums[left] == val) {
                nums[left] = nums[right];
                right--;
            } else {
                left++;
            }
        }
        return left;
    }

    public int removeElement3(int[] nums, int val) {

        int slow = 0;
        for (int fast = 0;fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow++] = nums[fast];
            }
        }
        return slow;
    }
}
