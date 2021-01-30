package cn.syl.swo.newa;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 *
 * 剑指 Offer 21. 调整数组顺序使奇数位于偶数前面
 *输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
 *
 *  
 *
 * 示例：
 *
 * 输入：nums = [1,2,3,4]
 * 输出：[1,3,2,4]
 * 注：[3,1,2,4] 也是正确的答案之一。
 *
 *
 */
public class Solution21 {

    public int[] exchange(int[] nums) {
        if (nums == null || nums.length == 0){
            return nums;
        }
        int i=0,j=nums.length-1;
        while (i<j){
            if ((nums[i] & 1) == 1){
                i++;
            }else{
                if ((nums[j]&1)==1){
                   int tmp = nums[i];
                   nums[i] = nums[j];
                   nums[j] = tmp;
                   j--;
                   i++;
                }else{
                    j--;
                }
            }
        }
        return nums;
    }
    public int[] exchange2(int[] nums) {
        int p = 0;
        int len = nums.length;
        for(int i = 0; i < len; i ++){
            if((nums[i]&1)==1){
                int tmp = nums[i];
                nums[i] = nums[p];
                nums[p++] = tmp;
            }
        }
        return nums;
    }


    public int[] exchange3(int[] nums) {
        int len=nums.length;
        if(len==1)
            return nums;
        int i=0;
        int j=0;
        int temp=0;
        for(i=0;i<len;i++){//i is on the right,j is on the left
            if((nums[i]%2!=0)){
                temp=nums[i];
                nums[i]=nums[j];
                nums[j]=temp;
                j++;
            }
        }

        return nums;
    }




    public static void main(String[] args) {
        int[] a = {1,2,3,4};
        Arrays.stream(new Solution21().exchange(a)).forEach(item->System.out.println(item));
    }

}
