package cn.syl.leetcode;

import java.util.Stack;

/**
 *
 *
 */
public class Solution24 {

    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }


    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null){
            return head;
        }
        Stack<ListNode> stack = new Stack<>();
        ListNode pre = new ListNode(-1); //前置节点
        ListNode h = pre;//做返回使用
        ListNode tmp = head;//当前节点
        int c = 0;
        while (tmp != null){
            if (c == k){
                c = 0;
                while (!stack.empty()){
                    pre.next = stack.pop();
                    pre = pre.next;
                }
                pre.next = tmp;
            }else{
                stack.push(tmp);
                tmp = tmp.next;
                c++;
            }

        }
        //剔除栈中的值，根据题意需要在栈len！=k时不做处理
        if (!stack.isEmpty() && k == stack.size()){
            while (!stack.isEmpty()){
                pre.next = stack.pop();
                pre = pre.next;
            }
            pre.next = null;
        }

        return h.next;
    }


    public ListNode reverseKGroup2(ListNode head, int k) {
        if (head == null){
            return head;
        }
        ListNode tmp = null;
        int c = 0;
        int z = 0;
        ListNode p = null;
        ListNode s = head;
        ListNode e = head;
        while (s != null){
            if (c == k){
                tmp = e.next;
                ListNode t = revers(s,e);
                if (t != null){
                    t.next = tmp;
                }
                s = tmp;
                z++;
                if (z == 1){
                    p = e;
                }
            }else{
                c++;
                e = s.next;
            }
        }


        return p;
    }

    private ListNode revers(ListNode s,ListNode e){
        if (s == e){
            return s;
        }
        e.next = revers(s.next,e);
        return s;
    }


    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        a.next = new ListNode(2);
        a.next.next = new ListNode(3);
        a.next.next.next = new ListNode(4);
//        a.next.next.next.next = new ListNode(5);
        ListNode b = new Solution24().reverseKGroup2(a,2);
        while (b!=null){
            System.out.println(b.val);
            b = b.next;
        }
    }
}
