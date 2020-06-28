package cn.syl.swo;

/**
 * 面试题07. 重建二叉树
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。
 * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 *
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 *      3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 */
public class Solution07 {

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0){
            return null;
        }
        TreeNode t;
        while (true){
//            preorder
            break;
        }

        return null;
    }

}
   class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
