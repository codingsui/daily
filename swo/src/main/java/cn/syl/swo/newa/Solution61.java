package cn.syl.swo.newa;

import java.util.HashSet;

/**
 *
 * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，
 * A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
 *
 */
public class Solution61 {

    public boolean isStraight(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        int max = 0;
        int min = 14;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0){
                continue;
            }
            max = Math.max(max,nums[i]);
            min = Math.min(min,nums[i]);
            if (set.contains(nums[i])){
                return false;
            }
            set.add(nums[i]);
        }
        return max - min < 5;
    }

    public static void main(String[] args) {
        System.out.println(new Solution61().isStraight(new int[]{0,0,2,2,5}));
    }
}
