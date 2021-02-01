package cn.syl.swo.newa;

/**
 *剑指 Offer 25. 合并两个排序的链表
 *输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。
 */
public class Solution25 {

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null){
            return l2;
        }else if (l2 == null){
            return l1;
        }
        ListNode a = l1;
        ListNode b = l2;
        ListNode newHead = null;
        ListNode tmp = null;
        while (a != null && b!=null){
            if (a.val < b.val){
                if (newHead == null){
                    newHead = a;
                    tmp = newHead;
                }else {
                    tmp.next = a;
                    tmp = tmp.next;
                }
                a = a.next;
            }else{
                if (newHead == null){
                    newHead = b;
                    tmp = newHead;
                }else {
                    tmp.next = b;
                    tmp = tmp.next;
                }
                b = b.next;
            }
        }
        while (a != null){
            tmp.next = a;
            a = a.next;
            tmp = tmp.next;
        }
        while (b != null){
            tmp.next = b;
            b = b.next;
            tmp = tmp.next;
        }
        return newHead;
    }
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(-1), pre = dummyHead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                pre.next = l1;
                pre = pre.next;
                l1 = l1.next;
            } else {
                pre.next = l2;
                pre = pre.next;
                l2 = l2.next;
            }
        }
        if (l1 != null) {
            pre.next = l1;
        }
        if (l2 != null) {
            pre.next = l2;
        }
        return dummyHead.next;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        a.next = new ListNode(2);
        a.next.next = new ListNode(4);
        ListNode b = new ListNode(1);
        b.next = new ListNode(3);
        b.next.next = new ListNode(4);
        ListNode z = new Solution25().mergeTwoLists(a,b);
        System.out.println();

    }
}
