package cn.syl.swo.newa;

/**
 *剑指 Offer 54. 二叉搜索树的第k大节点
 *
 *给定一棵二叉搜索树，请找出其中第k大的节点。
 */
public class Solution54 {

    int max;
    int i = 0;
    public int kthLargest(TreeNode root, int k) {
        a(root,k);
        return max;
    }

    private void a(TreeNode root,int k) {
        if (root == null){
            return;
        }
        a(root.right,k);
        i++;
        if (i == k){
            max = root.val;
            return;
        }
        a(root.left,k);
    }

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(3);
        treeNode.left = new TreeNode(1);
        treeNode.right = new TreeNode(4);
        treeNode.left.right = new TreeNode(2);
        System.out.println(new Solution54().kthLargest(treeNode,1));
    }
}
