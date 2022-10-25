package fun.tqyao.test;

import fun.tqyao.tree.ThreadTree02_01;

import java.util.*;

public class CreateTree {

    static int i = 0;// 计数
    public static TreeNode createBinaryTree(int[] arr){
        if (arr == null || arr.length == 0) {
            return null;
        }
        TreeNode node = new TreeNode();
        if (i < arr.length) {// 如果二叉树数据空值正确，此判断可以省略
            node.data = arr[i++];
            if (node.data == 0) {
                return null;
            } else {
                node.leftChild = createBinaryTree(arr);
                node.rightChild = createBinaryTree(arr);
            }
        }
        return node;
    }

    /**
     * 层序遍历
     *
     * @return java.util.List<java.util.List < T>>
     * @author tqyao
     * @create 2022/10/25 16:01
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> resList = new ArrayList<>();
        if (null == root)
            return resList;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int curLevelLens;
        TreeNode tempNode;
        List<Integer> curLevelList;
        while (!queue.isEmpty()) {
            curLevelLens = queue.size();
            curLevelList = new ArrayList<>();
            for (int i = 0; i < curLevelLens; i++) {
                tempNode = queue.poll();
                curLevelList.add(tempNode.data);

                Optional.ofNullable(tempNode.leftChild).ifPresent(queue::add);
                Optional.ofNullable(tempNode.rightChild).ifPresent(queue::add);
            }
            resList.add(curLevelList);
        }
        return resList;
    }

    public static void main(String[] args) {
        // 二叉树的值保存在数组中，以0作为分隔，数字0表示空节点，数组
        int[] arr = new int[]{1, 2, 0, 3, 4, 0, 0, 0, 5, 6, 0, 0, 7, 8, 9, 0, 0, 0, 0};
        TreeNode binaryTree = createBinaryTree(arr);
        System.out.println(binaryTree);

        System.out.println(levelOrder(binaryTree));

    }

}
