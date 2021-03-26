package cn.syl.swo.newa;

import java.util.PriorityQueue;

/**
 *
 *剑指 Offer 41. 数据流中的中位数
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。
 * 如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 *
 *
 */
public class Solution41 {

    private PriorityQueue<Integer> a;
    private PriorityQueue<Integer> b;

    /** initialize your data structure here. */
    public Solution41() {
        a = new PriorityQueue<>();
        b = new PriorityQueue<>((x,y)->(y-x));

    }

    public void addNum(int num) {
        if (a.size() != b.size()){
            a.add(num);
            b.add(a.poll());
        }else {
            b.add(num);
            a.add(b.poll());
        }

    }

    public double findMedian() {
        return a.size() != b.size() ? a.peek():(a.peek() + b.peek())/2 ;
    }
}
