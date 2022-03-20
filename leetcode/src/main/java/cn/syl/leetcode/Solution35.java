package cn.syl.leetcode;

/**
 *35. 搜索插入位置
 *给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *
 * 请必须使用时间复杂度为 O(log n) 的算法。
 *
 *
 */
public class Solution35 {

    public int searchInsert(int[] nums, int target) {
        if (nums == null){
            return -1;
        }
        int l = 0;
        int r = nums.length -1;
        while (l < r){
            int mid = l + (r - l) /2;
            if (nums[mid] > target){
                l = mid + 1;
            }else if (nums[mid] < target){
                r = mid - 1;
            }else {
                return mid;
            }
        }
        return l;
    }
}
