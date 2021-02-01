package cn.syl.swo.newa;

import sun.misc.Unsafe;

/**
 * 剑指 Offer 26. 树的子结构
 * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
 *
 * B是A的子结构， 即 A中有出现和B相同的结构和节点值。
 */
public class Solution26 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null || A.val != B.val){
            return false;
        }

        return isSubStructure(A.left,B.left) && isSubStructure(A.right,B.right);
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
