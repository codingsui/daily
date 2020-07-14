package cn.syl.java.tree.avl;

import java.util.ArrayDeque;
import java.util.Queue;

public class Avl {

    private class Node{
        Node left;
        Node right;
        int data;
        int height;

        public Node(int data) {
            this.data = data;
            this.height = 0;
        }
    }

    private Node root;

    private static final int BLANCE = 1;

    public int getHeight(Node root){

        return root == null ? -1 : root.height;
    }



    public void init(int[] data){
        if (data == null){
            return;
        }
        for (int i = 0; i < data.length; i++) {
            this.root = insert(this.root,data[i]);
        }
    }

    public Node insert(Node t,int data){
        if (t == null){
            return new Node(data);
        }
        if (t.data > data){
            t.left = insert(t.left,data);
        }else if (t.data < data){
            t.right = insert(t.right,data);
        }else {
            System.out.println("节点重复");
        }
        return balance(t);
    }

    private Node balance(Node t) {
        if (t == null){
            return null;
        }
        //左子树失衡
        if (getHeight(t.left) - getHeight(t.right) > BLANCE){
            //开始判断失衡的类型
            //a.左（L）子树的左（L）结点插入失衡，右旋转
            if (getHeight(t.left.left) >= getHeight(t.left.right)){
                return roteWithRight(t);
            }
            //b.左（L）子树的右（R）结点插入失衡，先左旋转然后右旋转
            else {
                return roteWitLeftRight(t);
            }
        }
        //右子树失衡
        else if (getHeight(t.right) - getHeight(t.left) > BLANCE){
            //右（R）子树的左（L）结点插入失衡
            if (getHeight(t.right.left) > getHeight(t.right.right)){
                return roteWithRightLeft(t);
            }else{
                return rotateWithLeft(t);
            }
        }
        t.height = Math.max(getHeight(t.left), getHeight(t.right)) + 1;
        return t;
    }

    /**
     * RR 左旋转
     * 5
     *   4
     *     3
     * @param t
     * @return
     */
    private Node rotateWithLeft(Node t){
        if (t == null){
            return t;
        }
        Node tmp = t.right;
        t.right = tmp.left;
        tmp.left = t;
        t.height = Math.max(getHeight(t.left),getHeight(t.right)) + 1;
        tmp.height = Math.max(getHeight(t.right),t.height) +1;
        return tmp;
    }

    /**
     * LL 右旋转
     *       1
     *    2
     * 3
     * @param t
     * @return
     */
    private Node roteWithRight(Node t){
        if (t == null){
            return t;
        }
        Node tmp = t.left;
        t.left = tmp.right;
        tmp.right = t;

        t.height = Math.max(getHeight(t.left),getHeight(t.right)) +1;
        tmp.height = Math.max(getHeight(tmp.left),t.height) + 1;
        return tmp;
    }

    /**
     * LR 先左旋转，然后右旋转
     *   5     5     4
     * 3      4    3   5
     *   4   3
     * @param t
     * @return
     */
    private Node roteWitLeftRight(Node t){
        if ( t == null){
            return t;
        }
        t.left = rotateWithLeft(t.left);
        return roteWithRight(t);
    }

    /**
     * RL 先右旋转然后左旋转
     *   5       5              6
     *     7       6          5   7
     *   6           7
     *
     * @param t
     * @return
     */
    private Node roteWithRightLeft(Node t){
        if (t == null){
            return t;
        }
        t.right = roteWithRight(t.right);
        return rotateWithLeft(t);
    }



    public Node delete(Node t,int data){

        if (t == null){
            return t;
        }
        //进左
        if (t.data > data){
            t.left = delete(t.left,data);
            if (getHeight(t.right) - getHeight(t.left) > BLANCE){
                if (getHeight(t.right.right) >= getHeight(t.right.left)){
                    t = rotateWithLeft(t);
                }else {
                    t = roteWithRightLeft(t);
                }
            }
            balance(t);
        }//进右
        else if (t.data < data){
            t.right = delete(t.right,data);
            if (getHeight(t.left) - getHeight(t.right) > BLANCE){
                if (getHeight(t.left.left) >= getHeight(t.left.right)){
                    t = roteWithRight(t);
                }else {
                    t = roteWitLeftRight(t);
                }
            }
            balance(t);
        }else{
            if (t.left == null){
                t = t.right;
            } else if (t.right == null){
                t = t.left;
            }else{
                Node tmp = t;
                Node leftMax = t.left;
                while (leftMax.right != null){
                    tmp = leftMax;
                    leftMax = leftMax.right;
                }
                t.data = leftMax.data;
                tmp.right = leftMax.left;
                t.height = Math.max(getHeight(t.left), getHeight(t.right)) + 1;
                balance(t);
            }
        }
        return t;
    }

    public static void c(Node root){
        if (root == null){
            return;
        }
        System.out.println("层次遍历");
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node t = queue.poll();
            System.out.print(t.data + " ");
            if (t.left != null){
                queue.add(t.left);
            }
            if (t.right != null){
                queue.add(t.right);
            }
        }

        System.out.println();
    }






    public static void main(String[] args) {
        int[] data = {0, 1, 4, 3, 8, 9, 2, 5, 7, 6, -10};
        Avl avl = new Avl();
//        for (int i=0; i<5; i++){
//            avl.root = avl.insert( avl.root,i);
//        }
        avl.init(data);
        c(avl.root);
        //     7
        //   6   10
        // 1    8  20
        avl.root = avl.delete(avl.root,3);
        c(avl.root);
        avl.root = avl.delete(avl.root,8);
        c(avl.root);
    }

}
