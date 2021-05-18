package cn.syl.swo.newa;

import java.util.Arrays;

/**
 *输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。
 *
 */
public class Solution57 {
    public static void main(String[] args) {
        int[] a= {2,7,11,15};
        System.out.println(Arrays.toString(new Solution57().twoSum(a,13)));
    }

    public int[] twoSum(int[] nums, int target) {

        int[] res = new int[2];
        int i = 0;
        int j = nums.length-1;
        while (i < j){
            int sum = nums[i] + nums[j];
            if (sum > target){
                j--;
            }else if (sum < target){
                i++;
            }else {
                res[0] = nums[i];
                res[1] = nums[j];
                return res;
            }
        }
        return new int[2];
    }


}
