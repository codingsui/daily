package cn.syl.swo.newa;

import java.util.ArrayList;
import java.util.List;

/**
 * 剑指 Offer 36. 二叉搜索树与双向链表
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
 */
public class Solution36 {
    public Node treeToDoublyList2(Node root) {
        if (root == null){
            return root;
        }
        List<Node> container = new ArrayList<>();
        dfs2(root,container);
        Node head = container.get(0);
        Node last = container.get(container.size() - 1);
        for (int i = 1; i < container.size(); i++) {
            container.get(i-1).right = container.get(i);
            container.get(i).left = container.get(i-1);
        }
        head.left = last;
        last.right = head;
        return head;
    }

    private void dfs2(Node root, List<Node> container) {
        if (root == null){
            return;
        }
        dfs2(root.left,container);
        container.add(root);
        dfs2(root.right,container);
    }

    private Node pre,head;
    public Node treeToDoublyList(Node root) {
        if (root == null){
            return root;
        }
        dfs(root);
        head.left = pre;
        pre.right = head;
        return head;
    }

    private void dfs(Node cur) {
        if (cur == null){
            return;
        }
        dfs(cur.left);

        if (pre == null){
            head = cur;
        }else {
            pre.right = cur;
        }
        cur.left = pre;
        pre = cur;
        dfs(cur.right);
    }

    private class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }
}

