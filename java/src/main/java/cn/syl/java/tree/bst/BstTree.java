package cn.syl.java.tree.bst;

import cn.syl.java.tree.simple.SimpleTree;
import cn.syl.java.tree.simple.Tree;

public class BstTree {
    private Tree root;

    public void initBstTree(Tree root){
        this.root = root;
    }


    public void initBstTree(int[] data){
        if (data.length < 1){
            return;
        }
        root = new Tree(data[0],null,null);
        for (int i = 1; i < data.length; i++) {
            insert(this.root,data[i]);
        }
    }

    public void insert(Tree t,int data){
        if (data > t.getData()){
            if (t.getRight() == null){
                t.setRight(new Tree(data,null,null));
            }else {
                insert(t.getRight(),data);
            }
        }else if (data < t.getData()){
            if (t.getLeft() == null){
                t.setLeft(new Tree(data,null,null));
            }else {
                insert(t.getLeft(),data);
            }
        }else {
            //不调整
        }
    }

   public boolean search(Tree t,int data){
        if (t == null){
            return false;
        }
       if (data > t.getData()){
           return search(t.getRight(),data);
       }else if (data < t.getData()){
           return search(t.getLeft(),data);
       }else {
           return true;
       }
   }

    public boolean delete(Tree t,int data) {
        if (t == null){
            return false;
        }
        if (t.getData() < data){
            return delete(t.getRight(),data);
        }else if (t.getData()> data){
            return delete(t.getLeft(),data);
        }else {
            return delete(t);
        }
    }
    public boolean delete(Tree t){
        if ( t == null){
            return false;
        }
        if (t.getLeft() == null){
            t = t.getRight();
        }else if (t.getRight() == null){
            t = t.getLeft();
        }else {
            Tree leftMax = t.getLeft();
            Tree tmp = t;
            while (leftMax.getRight() != null){
                t = leftMax;
                leftMax = leftMax.getRight();
            }
            t.setData(leftMax.getData());
            if (t == tmp){
                tmp.setLeft(leftMax.getLeft());
            }else {
                tmp.setRight(leftMax.getLeft());
            }
        }
        return true;
    }

    public Tree getRoot() {
        return root;
    }

    public void setRoot(Tree root) {
        this.root = root;
    }

    public static void main(String[] args) {
        int[] datas = {62, 88, 58, 47, 35, 73, 51, 99, 37, 93};

        BstTree b = new BstTree();
        b.initBstTree(datas);

        SimpleTree.mid(b.getRoot());
        System.out.println();
        b.insert(b.root, 1);
        SimpleTree.mid(b.root);
        System.out.println();
        System.out.println(b.delete(b.root,62));
        SimpleTree.mid(b.root);
        System.out.println();
    }

}
