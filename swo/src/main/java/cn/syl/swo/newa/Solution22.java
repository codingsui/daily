package cn.syl.swo.newa;

/**
 *
 *剑指 Offer 22. 链表中倒数第k个节点
    输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。例如，一个链表有6个节点，从头节点开始，它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个节点是值为4的节点。

    给定一个链表: 1->2->3->4->5, 和 k = 2.

    返回链表 4->5.
 */
public class Solution22 {

    /**
     * 先计算出链表长度
     * 再求正数第几个
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null){
            return head;
        }
        ListNode pre = head;
        int num = 0;
        while (pre != null){
            pre = pre.next;
            num++;
        }
        num=num-k-1;
        while (num >= 0){
            head = head.next;
            num--;
        }
        return head;
    }

    /**
     * 快慢指针
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd2(ListNode head, int k) {

        ListNode frontNode = head, behindNode = head;
        while (frontNode != null && k > 0) {

            frontNode = frontNode.next;
            k--;
        }

        while (frontNode != null) {

            frontNode = frontNode.next;
            behindNode = behindNode.next;
        }

        return behindNode;
    }
    public class ListNode {
         int val;
         ListNode next;
         ListNode(int x) { val = x; }
    }
}
