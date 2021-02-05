package cn.syl.swo.newa;

import java.util.Stack;

/**
 * 剑指 Offer 27. 二叉树的镜像
 *
 * 请完成一个函数，输入一个二叉树，该函数输出它的镜像。
 *
 * 例如输入：
 */
public class Solution27 {

    public TreeNode mirrorTree(TreeNode root) {
        if (root == null){
            return root;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        mirrorTree(root.left);
        mirrorTree(root.right);
        return root;
    }

    public TreeNode mirrorTree2(TreeNode root) {
        if (root == null){
            return root;
        }
        Stack<TreeNode> stack = new Stack();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode tmp = stack.pop();
            if (tmp.left != null){
                stack.push(tmp.left);
            }
            if (tmp.right != null){
                stack.push(tmp.right);
            }
            TreeNode t = tmp.left;
            tmp.left = tmp.right;
            tmp.right = t;
        }
        return root;
    }
}
