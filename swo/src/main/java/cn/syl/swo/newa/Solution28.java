package cn.syl.swo.newa;

import java.util.concurrent.Callable;

/**
 *
 *剑指 Offer 28. 对称的二叉树
 *
 * 请实现一个函数，用来判断一棵二叉树是不是对称的。如果一棵二叉树和它的镜像一样，那么它是对称的。
 */
public class Solution28 {

    public boolean isSymmetric(TreeNode root) {

        return root == null ? true : isSub(root.left,root.right);
    }

    public boolean isSub(TreeNode l,TreeNode r){
        if (l == null && r == null){
            return true;
        }
        if (l == null || r == null || l.val != r.val){
            return false;
        }
        return isSub(l.left,r.right) && isSub(l.right,r.left);
    }


}
