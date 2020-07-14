package cn.syl.java.tree.simple;

public class Tree {
    private int data;

    private Tree left;

    private Tree right;

    private int height;

    public Tree() {
        this.height = 1;
    }

    public Tree(int data) {
        this();
        this.data = data;
    }

    public Tree(int data, Tree left, Tree right) {
        this();
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Tree getLeft() {
        return left;
    }

    public void setLeft(Tree left) {
        this.left = left;
    }

    public Tree getRight() {
        return right;
    }

    public void setRight(Tree right) {
        this.right = right;
    }
}
