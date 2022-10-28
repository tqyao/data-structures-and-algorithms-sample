/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.tree;

import java.util.*;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2022/10/25 10:08
 */
public class ThreadTree02_01<T> {

    ThreadNode<T> root;


    /**
     * 后续线索化
     *
     * @author tqyao
     * @create 2022/10/27 19:45
     */
    public void createPostThread() {
        /*
            后续线索化最后一个左（右）孩子是空的节点如果不是最后输出节点，则说明早已经被线索化了；
            最后一个右子树时空的节点是最后输出的节点（根节点），说明是根节点右子树为空的情况，需要手动设置根节点线索化
         */
        ThreadNode<T> last = postThread(root, null);
        if (null == last.rchild)
            last.rtga = 1;
    }

    private ThreadNode<T> postThread(ThreadNode<T> cur, ThreadNode<T> pre) {
        if (null != root) {
            // 递归 左
            postThread(cur.lchild, cur);
            // 递归 右
            postThread(cur.rchild, cur);

            // 遍历 处理连接线索
            if (null == cur.lchild) {
                cur.lchild = pre;
                cur.ltag = 1;
            }

            if (null != pre && pre.rchild == null) {
                pre.rchild = cur;
                pre.rtga = 1;
            }
        }
        return cur;
    }


    /**
     * todo ：测试用例
     * 前序线索化
     *
     * @author tqyao
     * @create 2022/10/26 16:53
     */
    public void createPreThread() {
        ThreadNode<T> pre = null;
        pre = preThread(root, pre);
        pre.rtga = 1;
    }

    private ThreadNode<T> preThread(ThreadNode<T> curNode, ThreadNode<T> pre) {
        ThreadNode<T> lastPre = null;
        if (null != curNode) {
            if (null == curNode.lchild) {
                curNode.lchild = pre;
                curNode.ltag = 1;
            }
            if (null != pre && null == pre.rchild) {
                pre.rchild = curNode;
                pre.rtga = 1;
            }

            if (curNode.ltag != 1)
                preThread(curNode.lchild, curNode);
            lastPre = preThread(curNode.rchild, curNode);
        }
        return Optional.ofNullable(lastPre).orElse(pre);
    }

    /**
     * todo ：测试用例
     * <p>
     * 中序线索化二叉树
     *
     * @author tqyao
     * @create 2022/10/25 20:49
     */
    public void createInThread() {
        ThreadNode<T> pre = null;
        pre = inThread(root, pre);
        pre.rchild = null;
        pre.rtga = 1;
    }

    private ThreadNode<T> inThread(ThreadNode<T> curNode, ThreadNode<T> pre) {
        ThreadNode<T> lastPre = null;
        if (null != curNode) {
            inThread(curNode.lchild, curNode);
            if (null == curNode.lchild) {
                curNode.ltag = 1;
                curNode.lchild = pre;
            }
            if (null != pre && null == pre.rchild) {
                pre.rtga = 1;
                pre.rchild = curNode;
            }
            lastPre = inThread(curNode.rchild, curNode);
        }
        return Optional.ofNullable(lastPre).orElse(pre);
//        return lastPre;
    }


    /**
     * 递归创建完全二叉树
     *
     * @param ts
     * @param index
     * @author tqyao
     * @create 2022/10/26 16:30
     */
    public void createCompleteTreeRecursion(T[] ts, int index) {
        root = completeTreeRecursion(ts, index);
    }

    private ThreadNode<T> completeTreeRecursion(T[] ts, int index) {
        ThreadNode<T> curNode;
        if (index < ts.length) {
            curNode = new ThreadNode<>(ts[index]);
            ThreadNode<T> lchild = completeTreeRecursion(ts, index * 2 + 1);
            ThreadNode<T> rchild = completeTreeRecursion(ts, index * 2 + 2);
            curNode.lchild = lchild;
            curNode.rchild = rchild;
            return curNode;
        } else
            return null;
    }

    /**
     * 创建完全二叉树
     *
     * @author tqyao
     * @create 2022/10/25 19:03
     */
    public void createCompleteTree(T[] ts) {
        if (null == ts || ts.length == 0)
            return;

        List<ThreadNode<T>> nodeList = new ArrayList<>();
        for (T t : ts)
            nodeList.add(new ThreadNode<>(t));

        // 非叶子节点个数 n1 + n2
        int noLeaves = nodeList.size() / 2;
        for (int i = 0; i < noLeaves; i++) {
            if (i * 2 + 1 < nodeList.size())
                nodeList.get(i).lchild = nodeList.get(i * 2 + 1);
            if (i * 2 + 2 < nodeList.size())
                nodeList.get(i).rchild = nodeList.get(i * 2 + 2);
        }
        root = nodeList.get(0);
    }


    /**
     * 层序遍历
     *
     * @return java.util.List<java.util.List < T>>
     * @author tqyao
     * @create 2022/10/25 16:01
     */
    public List<List<T>> levelOrder() {
        List<List<T>> resList = new ArrayList<>();
        if (null == root)
            return resList;

        Queue<ThreadNode<T>> queue = new LinkedList<>();
        queue.add(root);

        int curLevelLens;
        ThreadNode<T> tempNode;
        List<T> curLevelList;
        while (!queue.isEmpty()) {
            curLevelLens = queue.size();
            curLevelList = new ArrayList<>();
            for (int i = 0; i < curLevelLens; i++) {
                tempNode = queue.poll();
                curLevelList.add(tempNode.data);

                Optional.ofNullable(tempNode.lchild).ifPresent(queue::add);
                Optional.ofNullable(tempNode.rchild).ifPresent(queue::add);
            }
            resList.add(curLevelList);
        }
        return resList;
    }

    /**
     * 创建二叉树（未关联线索）
     * todo:无法完按数组层序创建
     *
     * @param ts
     * @author tqyao
     * @create 2022/10/25 15:52
     */
    public void createBinaryTree(T[] ts) {
        if (null == ts || ts.length == 0)
            return;
        if (ts instanceof String[])
            root = createBinaryTreeRecursion(ts);
    }


    static int idx = 0;

    /**
     * 递归创建二叉树
     * todo:无法完按数组层序创建
     *
     * @param ts
     * @return fun.tqyao.tree.ThreadTree02_01.ThreadNode<T>
     * @author tqyao
     * @create 2022/10/25 15:54
     */
    private ThreadNode<T> createBinaryTreeRecursion(T[] ts) {
        if (idx < ts.length) {
            T data = ts[idx++];
            if ("0".equals(data))
                return null;
            else {
                ThreadNode<T> node = new ThreadNode<>(data);
                node.lchild = createBinaryTreeRecursion(ts);
                node.rchild = createBinaryTreeRecursion(ts);
                return node;
            }
        }
        return null;
    }

    static class ThreadNode<T> {
        T data;
        ThreadNode<T> lchild, rchild;
        int ltag, rtga;

        ThreadNode() {
        }

        ThreadNode(T data) {
            this.data = data;
        }
    }
}
