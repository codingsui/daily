package cn.syl.swo.newa;

import java.util.Stack;

/**
 *
 *定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 *
 */
public class Solution24 {

    public ListNode reverseList3(ListNode head) {
        if (head == null || head.next == null){
            return head;
        }
        ListNode tmp = reverseList3(head.next);
        head.next.next = head;
        head.next = null;
        return tmp;
    }

    public ListNode reverseList2(ListNode head) {
        if (head == null){
            return head;
        }
        ListNode newHead = null;
        while (head != null){
            ListNode tmp = head.next;
            head.next = newHead;
            newHead = head;
            head = tmp;
        }
        return newHead;
    }
    public ListNode reverseList(ListNode head) {
        if (head == null){
            return head;
        }
        Stack<ListNode> stack = new Stack<>();
        while (head != null){
            stack.push(head);
            head = head.next;
        }
        ListNode tmp = stack.pop();
        ListNode t = tmp;

        while (!stack.isEmpty()){
            ListNode i = stack.pop();
            t.next = i;
            t = t.next;
        }
        t.next = null;
        return tmp;
    }
    public static class ListNode {
      int val;
     ListNode next;
     ListNode(int x) { val = x; }}

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        ListNode a = new Solution24().reverseList3(head);

        System.out.println();
    }
}
