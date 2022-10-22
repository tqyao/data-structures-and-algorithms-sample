/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package leetcode;

import java.util.*;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2022/10/18 16:50
 */
public class TreeNodeSample {

    public static void main(String[] args) {
        TreeNode t2 = new TreeNode(2, null, null);
        TreeNode t3 = new TreeNode(3, null, null);
        TreeNode root = new TreeNode(1, t2, t3);
        System.out.println(levelOrder(root));
    }

    public static List<List<Integer>> levelOrder(TreeNode root) {

        if (null == root) return Collections.emptyList();

        // 层次遍历最终的二维层次数组
        List<List<Integer>> levelOrder = new ArrayList<>();
        //每一层数组包含元素
        List<Integer> ladders;

        // 所有层队列
        Queue<Queue<TreeNode>> q1 = new LinkedList<>();
        // 层待处理队列
        Queue<TreeNode> q2 = new LinkedList<>();


        q2.add(root);
        q1.add(q2);

        TreeNode tempNode;
        Queue<TreeNode> tempQ;
        while (!q1.isEmpty()) {
            q2 = q1.poll();
            // 每次处理完一层节点必须新创带处理队列，以区分每一层
            tempQ = new LinkedList<>();
            ladders = new ArrayList<>();
            while (!q2.isEmpty()) {
                /*
                1. 队列不为空代表该层有不是空的二叉树节点，弹出该节点并把节点数据加入到层容器中
                2. 如果节点有左孩子和右孩子节点，把节点加入到层待处理队列中
                 */
                tempNode = q2.poll();
                ladders.add(tempNode.val);
                if (null != tempNode.left)
                    tempQ.add(tempNode.left);
                if (null != tempNode.right)
                    tempQ.add(tempNode.right);

            }
            // 3. 该层待处理队列加入到总队列中
//            q1.add(tempQ);
            if (tempQ.size() > 0) q1.add(tempQ);
            levelOrder.add(ladders);
        }

        return levelOrder;


    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
