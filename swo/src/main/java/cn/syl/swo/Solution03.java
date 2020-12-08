package cn.syl.swo;

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

    /**
     * 空间复杂度n
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        if (nums == null || nums.length == 0){
            return -1;
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])){
                return nums[i];
            }else {
                set.add(nums[i]);
            }
        }
        return -1;
    }

    /**
     * 空间复杂度1
     * 也可以在排序的过程中判断
     * @param nums
     * @return
     */
    public int findRepeatNumber2(int[] nums) {
        if (nums == null || nums.length == 0){
            return -1;
        }
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i-1]){
                return i;
            }
        }
        return -1;
    }


    /**
     * 临时数组
     * @param nums
     * @return
     */
    public int findRepeatNumber3(int[] nums) {
        if (nums == null || nums.length == 0){
            return -1;
        }
        int[] temps = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            temps[nums[i]]++;
            if (temps[nums[i]] > 1){
                return nums[i];
            }
        }
        return -1;
    }

    /**
     * 原地交换
     * 时间o(n),空间0(1)
     * 为什么时间是o(n)
     * 可以设想把交换数字、查找数字解耦，分开看。在此方法下，每访问数组的一个单元格 nums[i] ，
     * 就可以把一个数字填到属于它的位置；因此， 将 n 个数字填到正确的位置最多需要 N 次操作，
     * 复杂度为 O(N) 。而搜索重复数字的条件是 nums[nums[i]] == nums[i] ，找到这样的数字只需要遍历一遍数组，复杂度为 O(N) 。 以上两个操作加起来是 O(2N) ，还是 O(N) 级别~ 可以结合你提出的示例的执行流程看：
     * @param nums
     * @return
     */
    public int findRepeatNumber4(int[] nums) {
        if (nums == null || nums.length == 0){
            return -1;
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i){
                continue;
            }
            if (nums[i] == nums[nums[i]]){
                return nums[i];
            }
            int temp = nums[nums[i]];
            nums[nums[i]] = nums[i];
            nums[i] = temp;
            i--;
        }
        return -1;
    }
}
