package cn.syl.swo.newa;

/**
 *
 *
 */
public class Solution52 {

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null){
            return null;
        }
        ListNode a = headA;
        ListNode b = headB;
        while (a != b){
            a = a != null ? a.next : headB;
            b = b != null ? b.next : headA;
        }
        return a;
    }
}
