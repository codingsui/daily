package cn.syl.swo.newa;

import java.util.HashMap;
import java.util.Map;

/**
 *剑指 Offer 39. 数组中出现次数超过一半的数字
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 *
 */
public class Solution39 {

    /**
     * hash法 时间和空间都是o(n)
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        for (Integer item:nums) {
            if (map.containsKey(item)){
                map.put(item,map.get(item) + 1);
            }else {
                map.put(item,1);
            }
            if (map.get(item) > nums.length/2){
                return item;
            }
        }
        return -1;
    }

    /**
     * 摩尔投票法 时间都是o(n) 空间o(1)
     * @param nums
     * @return
     */
    public int majorityElement2(int[] nums) {
        int x=0,votes =0;
        for (int num:nums) {
            if (votes ==  0){
                x = num;
            }
            votes += num == x ? 1 : -1;
        }
        return x;
    }

        public static void main(String[] args) {
        int[] a = {1,2,3,2,2,2,5,4,2};
        System.out.println(new Solution39().majorityElement(a));
    }
}
