/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.tree;

import java.util.*;

/**
 * 二叉链树+junit测试
 * @author tqyao
 * @version 1.0.0
 * @create 2022/10/22 11:24
 */
public class LinkBiTree01_03<T> {


    BitNode<T> root;


    /**
     * 非递归前序遍历：根左右
     *
     * @return java.util.List<java.lang.Integer>
     * @author tqyao
     * @create 2022/10/19 13:36
     */
    public List<T> preOrder() {
        List<T> res = new ArrayList<>();

        if (null == root)
            return res;

        // 利用栈的后进先出特性 前序遍历树
        Stack<BitNode<T>> stack = new Stack<>();
        // 先序遍历先输出树的根节点
        res.add(root.data);
        // 根节点入栈
        stack.push(root);

        BitNode<T> tempNode = null;
        while (null != tempNode || !stack.isEmpty()) {
            if (null != tempNode) {
                res.add(tempNode.data);
                stack.push(tempNode);
                tempNode = tempNode.lchild;
            } else {
                tempNode = stack.pop();
                tempNode = tempNode.rchild;
            }
        }
        return res;
    }

    /**
     * 非递归中序遍历：左根右
     * 依次处理每个节点：
     * 1.
     * 2.
     * 3.
     *
     * @return java.util.List<java.lang.Integer>
     * @author tqyao
     * @create 2022/10/19 16:15
     */
    public List<T> inOrder() {
        // 中序遍历结果
        List<T> res = new ArrayList<>();
        // 根节点为空直接返回空数组
        if (null == root)
            return res;

        // 借助栈的后进先出特性中序遍历二叉树
        Stack<BitNode<T>> stack = new Stack<>();
        stack.push(root);

        // 遍历指针
        BitNode<T> tempNode = null;
            /*
            [null != tempNode || ] 条件的必要性：
           如果二叉树没有左子树，栈最大非空长度为1，所以当进入else分支弹出一个节点后
           ，栈此时为空，假设此时还有1个右孩子没有输出，所以tempNode = tempNode.rchild;是非空节点
           ，所以此时如果仅仅靠判断栈是否为空还不能输出右孩子节点
             */
        while (null != tempNode || !stack.isEmpty()) {
            if (null != tempNode) {
                // 节点非空，说明还有左孩子，一路向左
                stack.push(tempNode);
                tempNode = tempNode.lchild;
            } else {
                // 节点为空，说明左孩子走到尽头，弹出栈顶节点并加入到数组
                //，让指针指向右孩子，下次循环可判断该节点是否有又孩子
                tempNode = stack.pop();
                res.add(tempNode.data);
                tempNode = tempNode.rchild;
            }
        }
        return res;
    }


    /**
     * 递归前序遍历
     *
     * @param bitNode
     * @param list
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/10/19 13:36
     */
    public List<T> preOrderRecursion(BitNode<T> bitNode, List<T> list) {
        if (null == list)
            list = new ArrayList<>();
        if (null != bitNode) {
            list.add(bitNode.data);
            preOrderRecursion(bitNode.lchild, list);
            preOrderRecursion(bitNode.rchild, list);
        }
        return list;
    }

    /**
     * 递归中序遍历
     *
     * @param bitNode
     * @param list
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/10/19 14:20
     */
    public List<T> inOrderRecursion(BitNode<T> bitNode, List<T> list) {
        if (null == list)
            list = new ArrayList<>();
        if (null != bitNode) {
            inOrderRecursion(bitNode.lchild, list);
            list.add(bitNode.data);
            inOrderRecursion(bitNode.rchild, list);
        }
        return list;
    }

    /**
     * 递归后序遍历
     *
     * @param bitNode
     * @param list
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/10/19 14:20
     */
    public List<T> postOrderRecursion(BitNode<T> bitNode, List<T> list) {
        if (null == list)
            list = new ArrayList<>();
        if (null != bitNode) {
            postOrderRecursion(bitNode.lchild, list);
            postOrderRecursion(bitNode.rchild, list);
            list.add(bitNode.data);
        }
        return list;
    }


    /**
     * 层序遍历二叉树
     *
     * @return java.util.List<java.util.List < T>>
     * @author tqyao
     * @create 2022/10/18 21:06
     */
    public List<List<T>> levelOrder() {
        // 根节点空返回数组
        if (null == root)
            return Collections.emptyList();

        // 层序遍历结果集
        List<List<T>> res = new ArrayList<>();
        Queue<BitNode<T>> queue = new LinkedList<>();

        queue.add(root);

        List<T> subList;
        BitNode<T> tempNope;
        int curSize;
        while (!queue.isEmpty()) {
            // 当层节点数，该层有多少节点，内层循环就需要循环多少次
            curSize = queue.size();
            // 当层遍历结果集
            subList = new ArrayList<>();
            for (int i = 0; i < curSize; i++) {
                // 出队
                tempNope = queue.poll();
                // 加入当层遍历结果集
                subList.add(tempNope.data);
                // 如果出队节点还有左右孩子节点则入队
                if (null != tempNope.lchild)
                    queue.add(tempNope.lchild);
                if (null != tempNope.rchild)
                    queue.add(tempNope.rchild);
            }
            // 当层遍历结果加入到总结果集
            res.add(subList);
        }
        return res;
    }


    /***
     * 创建完全二叉树
     * @author tqyao
     * @create 2022/10/18 20:48
     * @param t
     */
    public void createCompleteBiTree(T[] t) {
        List<BitNode<T>> nodes = new ArrayList<>();
        for (T tt : t)
            nodes.add(new BitNode<>(tt));

        // 设置根节点
        root = nodes.get(0);
        // 非叶子节点个数
        int notLeaves = (int) Math.floor(t.length / 2.0);
        for (int i = 0; i < notLeaves; i++) {
            if (i * 2 + 1 < nodes.size())
                nodes.get(i).lchild = nodes.get(i * 2 + 1);
            if (i * 2 + 2 < nodes.size())
                nodes.get(i).rchild = nodes.get(i * 2 + 2);
        }
    }


    public void createCompleteBiTreeByRecursion(T[] t) {

    }


    static class BitNode<T> {
        T data;
        BitNode<T> lchild, rchild;

        BitNode() {
        }

        BitNode(T data) {
            this(data, null, null);
        }

        BitNode(T data, BitNode<T> lchild, BitNode<T> rchild) {
            this.data = data;
            this.lchild = lchild;
            this.rchild = rchild;
        }
    }
}
