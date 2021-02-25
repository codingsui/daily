package cn.syl.swo.newa;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 *剑指 Offer 37. 序列化二叉树
 *请实现两个函数，分别用来序列化和反序列化二叉树。
 */
public class Solution37 {
    public String serialize(TreeNode root) {
        if (root == null){
            return "[]";
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        while (!queue.isEmpty()){
            TreeNode tmp = queue.poll();
            if (tmp != null){
                queue.add(tmp.left);
                queue.add(tmp.right);
                sb.append(tmp.val + ",");
            }else {
                sb.append("null,");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if(data.equals("[]")) return null;
        String[] vals = data.substring(1, data.length() - 1).split(",");
        TreeNode root = new TreeNode(Integer.parseInt(vals[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int i=1;
        while (!queue.isEmpty()){
            TreeNode tmp = queue.poll();
            if (!vals[i].equals("null")){
                TreeNode t = new TreeNode(Integer.parseInt(vals[i]));
                tmp.left = t;
                queue.add(t);
            }
            i++;
            if (!vals[i].equals("null")){
                TreeNode t = new TreeNode(Integer.parseInt(vals[i]));
                tmp.right = t;
                queue.add(t);
            }
            i++;
        }
        return root;
    }

}
