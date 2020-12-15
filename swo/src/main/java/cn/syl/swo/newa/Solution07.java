package cn.syl.swo.newa;

import java.util.HashMap;
import java.util.Map;

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
 *    核心思想：从先序遍历中可以确定根结点，
 *            然后在中序遍历中根据根结点去分割该结点相关的左右子树
 *            然后递归实现左右子树的遍历
 *
 */
public class Solution07 {


    private Map<Integer,Integer> map = new HashMap<>();
    private int[] preorder;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length == 0 || inorder.length == 0){
            return null;
        }
        this.preorder = preorder;
        for (int i = 0; i < preorder.length; i++) {
            map.put(inorder[i],i);
        }

        return recuror(0,0,preorder.length-1);
    }

    /**
     *
     * @param preIndex 先序遍历根结点中的索引位置
     * @param leftIndex 中序遍历左边界
     * @param rightIndex 中序遍历右边界
     * @return
     */
    private TreeNode recuror(int preIndex, int leftIndex, int rightIndex) {
        if (leftIndex > rightIndex){
            return null;
        }
        TreeNode root = new TreeNode(preorder[preIndex]);
        int idx = map.get(preorder[preIndex]);
        root.left = recuror(preIndex+1,leftIndex,idx -1);
        root.right = recuror(preIndex + (idx - 1 - leftIndex + 1) + 1,idx + 1,rightIndex);
        return root;
    }

}

class TreeNode {
   int val;
   TreeNode left;
   TreeNode right;
   TreeNode(int x) { val = x; }
}
