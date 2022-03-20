package cn.syl.leetcode.竞赛.ltc281;

public class Ltc2181 {

    public ListNode mergeNodes(ListNode head) {
        ListNode preIndex = head;
        int sum = 0;
        ListNode newHead = null,current= null;
        while (head != null) {
            if (head.val == 0 && sum != 0) {
                if (newHead == null) {
                    current = newHead = new ListNode(sum);
                }else {
                    current.next = new ListNode(sum);
                    current = current.next;
                }
                sum = 0;
            } else {
                sum += head.val;
            }
            head = head.next;
        }
        return newHead;
    }


    public ListNode mergeNodes2(ListNode head) {
        ListNode slow = head, fast = head.next;
        int sum = 0;
        while (fast != null) {
            if (sum == 0) {
                slow = slow.next;
            }
            if (fast.val == 0){
                slow.val = sum;
                sum = 0;
            }else {
                sum += fast.val;
            }
            fast = fast.next;
        }
        return head.next;
    }

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
}
