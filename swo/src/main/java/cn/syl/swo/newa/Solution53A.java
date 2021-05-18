package cn.syl.swo.newa;

/**
 *
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。
 * 在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出这个数字。
 *
 *
 */
public class Solution53A {

    public int missingNumber(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        while (i <= j){
            int tmp = ( i + j) / 2;
            if (nums[tmp] == tmp){
                i = tmp + 1;
            }else {
                j = tmp - 1;
            }
        }
        return i;
    }



}
