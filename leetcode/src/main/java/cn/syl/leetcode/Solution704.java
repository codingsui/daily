package cn.syl.leetcode;

import org.junit.Assert;

import java.util.HashMap;

/**
 *二分查找
 *给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 *
 *
 */
public class Solution704 {


    public int search(int[] nums, int target) {
        if(nums == null || nums.length == 0){
            return -1;
        }
        int i = 0;
        int j = nums.length - 1;
        while (i<=j){
            int index = (i + j) / 2;
            if (nums[index] > target){
                j = index-1;
            }else if (nums[index] < target){
                i = index+1;
            }else {
                return index;
            }
        }
        return -1;
    }



    public static void main(String[] args) {
        Assert.assertEquals(new Solution704().search(new int[]{-1,0,3,5,9,12},9),4);
        System.out.println(3 / 2);
    }
}
