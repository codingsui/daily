package cn.syl.swo.newa;

import java.util.HashMap;
import java.util.Map;

/**
 *剑指 Offer 35. 复杂链表的复制
 *请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，每个节点除了有一个 next 指针指向下一个节点，
 * 还有一个 random 指针指向链表中的任意节点或者 null。
 */
public class Solution35 {

    public Node copyRandomList(Node head) {
        if (head == null){
            return head;
        }
        Map<Node,Node> map = new HashMap<>();
        Node tmp = head;
        while (tmp != null){
            map.put(tmp,new Node(tmp.val));
            tmp = tmp.next;
        }
        tmp = head;
        while (tmp != null){
            map.get(tmp).next = map.get(tmp.next);
            map.get(tmp).random = map.get(tmp.random);
            tmp = tmp.next;
        }
        return map.get(head);
    }

    public Node copyRandomList2(Node head) {
        if (head == null){
            return head;
        }
        //绑定新节点
        Node tmp = head;
        while (tmp != null){
            Node newNode = new Node(tmp.val);
            newNode.next = tmp.next;
            tmp.next = newNode;
            tmp = newNode.next;
        }
        //给新节点绑定random节点
        tmp = head;
        while (tmp != null){
            if (tmp.random != null){
                tmp.next.random = tmp.random.next;
            }
            tmp = tmp.next.next;
        }
        //拆分链表
        Node newHead = head.next,newTmp = newHead;
        tmp = head;
        while (newTmp.next != null){
            tmp.next = tmp.next.next;
            newTmp.next = newTmp.next.next;
            tmp = tmp.next;
            newTmp = newTmp.next;
        }
        tmp.next = null;
        return newHead;
    }
}
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}