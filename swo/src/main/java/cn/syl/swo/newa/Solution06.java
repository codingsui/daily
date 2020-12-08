package cn.syl.swo.newa;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 面试题05. 从尾到头打印链表
 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

 输入：head = [1,3,2]
 输出：[2,3,1]

**/
public class Solution06 {
    public int[] reversePrint(ListNode head) {

        Stack<Integer> stack = new Stack<>();
        ListNode h = head;
        int size = 0;
        while (h != null){
            stack.push(h.val);
            h = h.next;
            size++;
        }
        int[] tmp = new int[size];
        for (int i = 0; i < size; i++) {
            tmp[i] = stack.pop();
        }
        return tmp;
    }

    public int[] reversePrint2(ListNode head) {
        ListNode node = head;
        int count = 0;
        while (node != null) {
            ++count;
            node = node.next;
        }
        int[] nums = new int[count];
        node = head;
        for (int i = count - 1; i >= 0; --i) {
            nums[i] = node.val;
            node = node.next;
        }
        return nums;
    }
}

class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
 }