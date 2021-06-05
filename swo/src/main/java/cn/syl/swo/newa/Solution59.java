package cn.syl.swo.newa;

import java.util.Deque;
import java.util.LinkedList;

/**
 *给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。
 *
 */
public class Solution59 {

    /**
     * 滑动窗口
     * 1. 队列窗口是单调递减的
     * 2. 队列满的情况下
     *  2.1 出队列的元素是队头元素，那么删除队头即可
     *  2.2 入队元素必须大于所有队内元素，队内元素出队
     *  2.3
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || k == 0){
            return new int[0];
        }
        int[] res = new int[nums.length - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && deque.peekLast() < nums[i]){
                deque.removeLast();
            }
            deque.addLast(nums[i]);
        }
        res[0] = deque.peekFirst();
        for (int i = k; i < nums.length ; i++) {
            //nums[i-k]为滑动窗口左侧相邻的第一个元素，判断的目的是为了判别，如果滑动窗口向游移动，队头的最大元素是该值的话，那么新进来的元素较小还是取的已经出滑动窗口的值，因此需要判断该情况
            if (nums[i - k] == deque.peekFirst()){
                deque.removeFirst();
            }
            while (!deque.isEmpty() && deque.peekLast() < nums[i]){
                deque.removeLast();
            }
            deque.addLast(nums[i]);
            res[i-k+1] = deque.peekFirst();
        }
        return res;
    }

}
