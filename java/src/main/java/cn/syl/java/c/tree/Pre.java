package cn.syl.java.c.tree;

import java.util.LinkedList;

public class Pre {

    public static void pre(Tree tree){
        if (tree == null){
            return;
        }
        System.out.println(tree.getData());
        pre(tree.getLeft());
        pre(tree.getRight());
    }

    public static void mid(Tree tree){
        if (tree == null){
            return;
        }
        mid(tree.getLeft());
        System.out.println(tree.getData());
        mid(tree.getRight());
    }

    public static void mid2(Tree t){
        LinkedList<Tree> stack = new LinkedList<Tree>();

        Tree p = t;
        while (p != null || !stack.isEmpty()){
            if (p != null){
                stack.push(p);
                p = p.getLeft();
            }else{
                p = stack.pop();
                System.out.println(p.getData());
                p = p.getRight();
            }
        }
    }


    public static void main(String[] args) {
        Tree t = new Tree(1);
        Tree t1 = new Tree(2);
        t.setLeft(t1);
        Tree t2 = new Tree(3);
        t.setRight(t2);
        t1.setLeft(new Tree(4));
        t1.setRight(new Tree(5));
        pre(t);
        System.out.println("=======");
        mid(t);
        System.out.println("=======");
        mid2(t);
    }
}
