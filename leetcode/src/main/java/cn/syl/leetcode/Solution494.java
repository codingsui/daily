package cn.syl.leetcode;

public class A {
    public static void main(String[] args) {

    }

    public ListNode swapPairs(ListNode head) {
        ListNode pre = new ListNode(-1) ;
        pre.next = head;
        ListNode tmp = pre;
        while (tmp.next != null && tmp.next.next != null){
            ListNode a = tmp.next;
            ListNode b = tmp.next.next;
            tmp.next = b;
            a.next = b.next;
            b.next = a;
            tmp = a;

        }
        return pre.next;
    }

    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
    }
}
