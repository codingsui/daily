package cn.syl.swo.newa;

/**
 *统计一个数字在排序数组中出现的次数。
 *
 */
public class Solution53 {

    public static void main(String[] args) {
        System.out.println(new Solution53().search(new int[]{5},5));
    }
    public int search(int[] nums, int target) {

        int l = 0;
        int r = nums.length-1;
        int index = -1;
        while (l <= r){
            int mid = (l + r) / 2;
            if (nums[mid] > target){
                r = mid -1;
            }else if (nums[mid] < target){
                l = mid + 1;
            }else {
                index = mid;
                break;
            }
        }
        if (index == -1){
            return 0;
        }
        int count = 1;
        int a = index;
        while (--a >= 0){
            if (nums[a] == target){
                count++;
            }
        }
        a = index;
        while (++a < nums.length){
            if (nums[a] == target){
                count++;
            }
        }
        return count;
    }

    public int search2(int[] nums, int target) {
        return helper(nums, target) - helper(nums, target - 1);
    }
    int helper(int[] nums, int tar) {
        int i = 0, j = nums.length - 1;
        while(i <= j) {
            int m = (i + j) / 2;
            if(nums[m] <= tar) i = m + 1;
            else j = m - 1;
        }
        return i;
    }


}
