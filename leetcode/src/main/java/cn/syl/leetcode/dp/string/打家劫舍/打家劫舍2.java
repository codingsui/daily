package cn.syl.leetcode.dp.string.打家劫舍;

import java.util.Arrays;

public class 打家劫舍2 {

    public static void main(String[] args) {
        System.out.println(new 打家劫舍2().rob(new int[]{8,9,8,6,1,1}));
    }
    public int rob(int[] nums) {
        if (nums.length == 1){
            return nums[0];
        }else if (nums.length == 2){
            return Math.max(nums[0],nums[1]);
        }
        return Math.max(myRob(nums,0,nums.length-2),myRob(nums,1,nums.length-1));
    }

    public int myRob(int[] nums,int start,int end){
        int first = nums[start];
        int sencond = Math.max(nums[start],nums[start + 1]);
        for (int i = start + 2; i <= end; i++) {
            int temp = sencond;
            sencond = Math.max(first + nums[i],sencond);
            first = temp;
        }
        return sencond;
    }
}
