package cn.syl.swo.newa;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 *剑指 Offer 45. 把数组排成最小的数
 * 输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。
 */
public class Solution45 {
    public static void main(String[] args) {
        System.out.println(new Solution45().minNumber(new int[]{1,2,3,1}));
    }

    public String minNumber2(int[] nums) {
        Queue<String> queue = new PriorityQueue<>((x,y)->{
            return (x + y).compareTo(y + x);
        });

        for (int i = 0; i < nums.length; i++) {
            queue.add(String.valueOf(nums[i]));
        }

        StringBuffer sb = new StringBuffer();
        while (!queue.isEmpty()) {
            sb.append(queue.poll());
        }
        return sb.toString();
    }
    public String minNumber(int[] nums) {
        if (nums == null || nums.length == 0){
            return null;
        }
        String[] res = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i] = nums[i] + "";
        }
//        quickSort(res,0,nums.length-1);
        Arrays.sort(res, (x, y) -> (x + y).compareTo(y + x));

        StringBuffer sb = new StringBuffer();
        for (String item:res) {
            sb.append(item);
        }
        return sb.toString();
    }

    private void quickSort(String[] res, int l, int r) {
        if (l >= r){
            return;
        }
        int low = l,high = r;
        String base = res[l];
        while (l < r) {
            while (l < r && (res[r] + base).compareTo(base + res[r]) >= 0){
                r--;
            }
            res[l] = res[r];
            while (l < r && (res[l] + base).compareTo(base + res[l]) <= 0){
                l++;
            }
            res[r] = res[l];
            res[l] = base;
        }
        quickSort(res,low,l-1);
        quickSort(res,r+1,high);

    }

}
