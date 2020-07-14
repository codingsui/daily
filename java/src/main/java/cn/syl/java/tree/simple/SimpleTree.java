package cn.syl.java.tree.simple;

import java.util.*;

/**
 * 二叉树的遍历
 */
public class SimpleTree {

    /**
     * 前序遍历
     * @param tree
     */
    public static void pre(Tree tree){
        if (tree == null){
            return;
        }
        System.out.print(tree.getData() + " ");
        pre(tree.getLeft());
        pre(tree.getRight());
    }

    /**
     * 中序遍历
     * @param tree
     */
    public static void mid(Tree tree){
        if (tree == null){
            return;
        }
        mid(tree.getLeft());
        System.out.print(tree.getData() + " ");
        mid(tree.getRight());

    }

    public static void last(Tree tree){
        if (tree == null){
            return;
        }
        last(tree.getLeft());
        last(tree.getRight());
        System.out.print(tree.getData() + " ");
    }

    public static void pre2(Tree t){
        if (t == null){
            return;
        }
        Deque<Tree> stack = new ArrayDeque<>();
        Tree p = t;
        while (p!= null || !stack.isEmpty()){
            if (p != null){
                stack.push(p);
                System.out.print(p.getData() + " ");
                p = p.getLeft();
            }else {
                p = stack.pop();
                p = p.getRight();
            }
        }
    }

    /**
     * 中序遍历的非递归方法
     * @param t
     */
    public static void mid2(Tree t){
        if (t == null){
            return;
        }
        Stack<Tree> stack = new Stack<Tree>();
        Tree p = t;
        while (p != null || !stack.isEmpty()){
            while (p != null){
                stack.push(p);
                p = p.getLeft();
            }
            p = stack.pop();
            System.out.print(p.getData() + " ");
            p = p.getRight();
        }
    }
    public static void last2(Tree t){
        if (t == null){
            return;
        }
        Stack<Tree> stack = new Stack<>();
        Stack<Integer> out = new Stack<>();


        Tree p = t;
        while (p != null ||!stack.isEmpty()){
            if (p != null){
                stack.push(p);
                out.push(p.getData());
                p = p.getRight();
            }else {
                p = stack.pop();
                p = p.getLeft();
            }
        }
        while (!out.isEmpty()){
            System.out.print(out.pop() + " ");
        }
    }


    public static void levelOrder(Tree t){
        if (t == null){
            return;
        }
        Queue<Tree> queue = new ArrayDeque<>();
        queue.add(t);
        while (!queue.isEmpty()){
            Tree p = queue.poll();
            System.out.print(p.getData() + " ");
            if (p.getLeft() != null){
                queue.add(p.getLeft());
            }
            if (p.getRight() != null){
                queue.add(p.getRight());
            }
        }
    }


    /**
     *    1
     *   / \
     *  2   3
     * / \
     * 4  5
     * @param args
     */
    public static void main(String[] args) {
        Tree t = new Tree(1);
        Tree t1 = new Tree(2);
        t.setLeft(t1);
        Tree t2 = new Tree(3);
        t.setRight(t2);
        t1.setLeft(new Tree(4));
        t1.setRight(new Tree(5));
        System.out.println("前序遍历=======");
        pre(t);
        System.out.println();
        System.out.println("-------------");
        pre2(t);
        System.out.println();
        System.out.println("中序遍历=======");
        mid(t);
        System.out.println();
        System.out.println("-------------");
        mid2(t);
        System.out.println();
        System.out.println("后续遍历=======");
        last(t);
        System.out.println();
        System.out.println("-------------");
        last2(t);
        System.out.println();
        System.out.println("层次遍历=======");
        levelOrder(t);

    }
}
