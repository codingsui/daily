package cn.syl.java.tree.avl;

import cn.syl.java.tree.simple.Tree;

public class AvlTree {


    private Tree root;

    public int getDeep(Tree node){
        if (node == null){
            return 0;
        }
        int left = getDeep(node.getLeft()) + 1;
        int right = getDeep(node.getRight()) + 1;
        return Math.max(left,right);
    }






    public void init(int[] data){

    }

    public void insert(int data){

    }

    public boolean search(int data){
        return false;
    }


    public boolean delete(int data){
        return false;
    }
}
