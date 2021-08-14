package cn.syl.leetcode.dp.string.array;

public class 乘积最大子组数 {
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0){
            return -1;
        }
        if (nums.length == 1){
            return nums[0];
        }
        int[] dpMin = new int[nums.length];
        int[] dpMax = new int[nums.length];
        int max ;
        max = dpMin[0] = dpMax[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dpMin[i] = Math.min(nums[i],Math.min(nums[i] * dpMin[i-1],nums[i]*dpMax[i-1]));
            dpMax[i] = Math.max(nums[i],Math.max(nums[i] * dpMin[i-1],nums[i]*dpMax[i-1]));
            max = Math.max(max,dpMax[i]);
        }
        return max;
    }

    public int maxProduct2(int[] nums) {
        if (nums == null || nums.length == 0){
            return -1;
        }
        if (nums.length == 1){
            return nums[0];
        }
        int dpMin =nums[0],dpMax =nums[0] , max=nums[0] ;
        for (int i = 1; i < nums.length; i++) {
            int min = dpMin;
            dpMin = Math.min(nums[i],Math.min(nums[i] * dpMin,nums[i]*dpMax));
            dpMax = Math.max(nums[i],Math.max(nums[i] * min,nums[i]*dpMax));
            max = Math.max(max,dpMax);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new 乘积最大子组数().maxProduct(new int[]{2,3,-2,4}));
    }
}
