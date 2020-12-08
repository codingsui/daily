package cn.syl.swo.newa;

import java.util.*;

/**
 * 面试题03. 数组中重复的数字
 * 找出数组中重复的数字。
 *
 *
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，
 * 也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 */
public class Solution03 {

    public int findRepeatNumber(int[] nums) {
        if (nums == null) {
            return -1;
        }
        Set<Integer> set = new HashSet<>(nums.length);
        for (int i = 0;i<nums.length;i++){
            if (!set.add(nums[i])){
                return nums[i];
            }
        }
        return -1;
    }

    public int findRepeatNumber2(int[] nums) {
        if (nums == null) {
            return -1;
        }
        Arrays.sort(nums);
        for (int i = 1;i<nums.length;i++){
            if (nums[i] == nums[i-1]){
                return nums[i];
            }
        }
        return -1;
    }

    public int findRepeatNumber3(int[] nums) {
        if (nums == null) {
            return -1;
        }
        int[] array = new int[nums.length];
        for (int i = 0; i < nums.length;i++) {
            array[nums[i]]++;
            if (array[nums[i]] > 1){
                return nums[i];
            }
        }
        return -1;
    }

    public int findRepeatNumber4(int[] nums) {
        if (nums == null) {
            return -1;
        }

        for (int i = 0; i < nums.length;i++) {
            if (i == nums[i]){
                continue;
            }
            if (nums[i] == nums[nums[i]]){
                return nums[i];
            }
            int tmp = nums[nums[i]];
            nums[nums[i]] = nums[i];
            nums[i] = tmp;
            i--;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 0, 2, 5, 3};
        Solution03 s = new Solution03();
        System.out.println(s.findRepeatNumber(nums));
        System.out.println(s.findRepeatNumber2(nums));
        System.out.println(s.findRepeatNumber3(nums));
        System.out.println(s.findRepeatNumber4(nums));
    }
}
