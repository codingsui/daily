package cn.syl.swo.newa;

/**
 *  剑指 Offer 68 - II. 二叉树的最近公共祖先
 *
 *
 */
public class Solution68 {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || p == root || q == root){
            return root;
        }
        TreeNode left = lowestCommonAncestor(root.left,p,q);
        TreeNode right = lowestCommonAncestor(root.right,p,q);
        if (left == null && right == null){
            return null;
        }else if (left == null){
            return right;
        }else if (right == null){
            return left;
        }else {
            return root;
        }
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(3);
        TreeNode a1 = new TreeNode(5);
        TreeNode a2 = new TreeNode(1);
        TreeNode a3 = new TreeNode(6);
        TreeNode a4 = new TreeNode(2);
        TreeNode a5 = new TreeNode(0);
        TreeNode a6 = new TreeNode(8);
        TreeNode a7 = new TreeNode(7);
        TreeNode a8 = new TreeNode(4);
        TreeNode a9 = new TreeNode(22);
        treeNode.left = a1;
        treeNode.right = a2;
        a1.left = a3;
        a1.right = a4;
        a4.left = a7;
        a4.right = a8;
        a2.left = a5;
        a2.right = a6;
        System.out.println(new Solution68().lowestCommonAncestor(treeNode,a7,a9));

    }
}
