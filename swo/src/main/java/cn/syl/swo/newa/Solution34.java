package cn.syl.swo.newa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *剑指 Offer 34. 二叉树中和为某一值的路径
 * 输入一棵二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶节点所经过的节点形成一条路径。
 */
public class Solution34 {

    private List<List<Integer>> res;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        res = new ArrayList<>();
        backTrak(root,sum,new ArrayList<Integer>());
        return res;
    }

    private void backTrak(TreeNode root, int sum, ArrayList<Integer> path) {
        if (root == null){
            return;
        }
        path.add(root.val);
        sum -= root.val;
        if (sum == 0 && root.left == null && root.right == null){
            res.add(new ArrayList<>(path));
        }
        backTrak(root.left,sum,path);
        backTrak(root.right,sum,path);
        path.remove(path.size()-1);
    }
}
