package cn.syl.swo;

import java.util.*;

/**
 * 剑指 Offer 09. 用两个栈实现队列
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
 * 分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 *
 *
 * 示例 1：
 *
 * 输入：
 * ["CQueue","appendTail","deleteHead","deleteHead"]
 * [[],[3],[],[]]
 * 输出：[null,null,3,-1]
 *
 * 示例 2：
 *
 * 输入：
 * ["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
 * [[],[],[5],[2],[],[]]
 * 输出：[null,-1,null,null,5,2]
 *
 */
public class Solution09 {
    public static void main(String[] args) {
        CQueue c = new CQueue();
        System.out.println(c.deleteHead());
        c.appendTail(1);
        c.appendTail(2);
        c.appendTail(3);
        System.out.println(c.deleteHead());
        System.out.println(c.deleteHead());
        System.out.println(c.deleteHead());
    }
}
class CQueue{
    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();
    public CQueue() {

    }

    public void appendTail(int value) {
        stack.push(value);
    }

    public int deleteHead() {
        if (stack2.isEmpty()){
            while (!stack.isEmpty()){
                stack2.push(stack.pop());
            }
        }
        return stack2.isEmpty() ? -1 : stack2.pop();
    }


}