package cn.syl.swo.newa;

/**
 *
 *一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
 */
public class Solution56 {

    public int[] singleNumbers(int[] nums) {
        int x = 0,y = 0 ,m = 1,n=0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            n = nums[i] ^ n;
        }
        while ((m & n) == 0){
            m = m << 1;
        }
        for (int i = 0; i < len; i++) {
            if ((nums[i] & m) != 0){
                x = x ^ nums[i];
            }else {
                y = y ^ nums[i];
            }
        }
        return new int[]{x,y};
    }

}
