/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.tree;

import org.junit.Test;

import java.util.*;

/**
 * 二叉链树
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/10/17 16:07
 */
public class LinkBiTreeSample01_01 {


//    @Test
//    public void testInit() {
//        // todo:?
//        BitNode<String> b1 = new BitNode<>();
//        System.out.println(preorderTraversal(b1));
//
//        BitNode b2 = new BitNode<>();
//        System.out.println(preorderTraversal(b2));
//    }


//    public static BitNode createBinaryTree() {
//        Scanner scanner = new Scanner(System.in);
//        BitNode<String> root = new BitNode<>();
//        System.out.print("请输入树的节点(从上到下从左到右，0代表空节点，以空格分隔，回车以结束)>>");
//        String[] inp = scanner.nextLine().split(" ");
//        System.out.println(Arrays.toString(inp));
//
//
//        return null;
//    }


//    public static <T> BitNode<T> createCompleteBinaryTree(T[] nodeDatas) {
//        return null;
//    }


    /**
     * 递归 创建完全二叉树 by 数组
     *
     * @param nodeDatas
     * @return fun.tqyao.tree.LinkBiTreeSample01_01.BitNode<T>
     * @author tqyao
     * @create 2022/11/2 16:40
     */
    public static <T> BitNode<T> createCompleteBinaryTreeRecursion(T[] nodeDatas, int idx) {
        BitNode<T> tBitNode = null;
        if (idx < nodeDatas.length) {
            tBitNode = new BitNode<>(nodeDatas[idx]);
            // 当前节点存在左孩子
            if (idx * 2 + 1 < nodeDatas.length)
                tBitNode.lchild = createCompleteBinaryTreeRecursion(nodeDatas, idx * 2 + 1);
            // 当前节点存在右孩子
            if (idx * 2 + 2 < nodeDatas.length)
                tBitNode.rchild = createCompleteBinaryTreeRecursion(nodeDatas, idx * 2 + 2);
        }
        return tBitNode;
    }


//    /**
//     * 测试容器作为容器元素的储存原理
//     * 结论：引用传递
//     *
//     * @author tqyao
//     * @create 2022/10/18 14:06
//     */
//    @Test
//    public void testReferencePass() {
//        List<List<String>> list = new ArrayList<>();
//        List<String> els = new ArrayList<>();
//        els.add("a");
//        els.add("b");
//        els.add("c");
//        list.add(els);
//
//        els.clear();
//        els.add("1");
//        els.add("2");
//        els.add("3");
//        list.add(els);
//
//        System.out.println(list);
//    }

    /**
     * todo：层序遍历
     *
     * @param root
     * @return java.util.List<java.util.List < T>>
     * @author tqyao
     * @create 2022/11/2 16:09
     */
    public static <T> List<List<T>> levelOrder(BitNode<T> root) {
//        List<List<T>> levelOrder = new ArrayList<>();
////        List<T> ladders = new ArrayList<>();
//        Queue<BitNode<T>> queue = new LinkedList<>();
//        queue.add(root);
//        while (!queue.isEmpty()) {
//            BitNode<T> node = queue.poll();
//            List<T> ladders = new ArrayList<>();
//            Optional.ofNullable(node).ifPresent(tBitNode -> {
//                ladders.add(tBitNode.data);
//                Optional.ofNullable(tBitNode.lchild).ifPresent(queue::add);
//                Optional.ofNullable(tBitNode.rchild).ifPresent(queue::add);
//            });
//        }

        if (null == root) return Collections.emptyList();

        // 层次遍历最终的二维层次数组
        List<List<T>> levelOrder = new ArrayList<>();
        //每一层数组包含元素
        List<T> ladders;

        // 所有层队列
        Queue<Queue<BitNode<T>>> q1 = new LinkedList<>();
        // 层待处理队列
        Queue<BitNode<T>> q2 = new LinkedList<>();


        q2.add(root);
        q1.add(q2);

        BitNode<T> tempNode;
        Queue<BitNode<T>> tempQ;
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
                ladders.add(tempNode.data);
                if (null != tempNode.lchild)
                    tempQ.add(tempNode.lchild);
                if (null != tempNode.rchild)
                    tempQ.add(tempNode.rchild);

/*                List<T> finalLadders = ladders;
                Queue<BitNode<T>> finalTempQ = tempQ;
                Optional.of(q2.poll()).ifPresent(tBitNode -> {
                    finalLadders.add(tBitNode.data);
                    finalTempQ.add(tBitNode.lchild);
                    finalTempQ.add(tBitNode.rchild);
                });*/

        /*        Optional.ofNullable(tempNode.lchild).ifPresent(tempQ::add);
                Optional.ofNullable(tempNode.rchild).ifPresent(tempQ::add);*/

            }
            // 3. 该层待处理队列加入到总队列中
            q1.add(tempQ);
            levelOrder.add(ladders);
        }

        return levelOrder;
    }


    //    @Test
//    public void testPreOrder() {
//
//    }

    /**
     * 非递归后序遍历
     *
     * @param root
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/11/3 08:18
     */
    public static <T> List<T> postOrderTraverse(BitNode<T> root) {
        List<T> resList = new ArrayList<>();
        if (root == null)
            return resList;

        Stack<BitNode<T>> stack = new Stack<>();
        BitNode<T> cur = root;
        BitNode<T> pre = null;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.lchild;
            } else {
                cur = stack.peek();
                // 如果是从不右子树回来，则右子树还未遍历过，则转向右子树
                if (pre != null && pre != cur.rchild)
                    cur = cur.rchild;
                else {
                    cur = stack.pop();
                    resList.add(cur.data);
                    // 记录输出节点
                    pre = cur;
                    // 当前以cur为根子树全部输出完毕，
                    cur = null;
                }
            }
        }
        return resList;
    }

    /**
     * 非递归中序遍历
     *
     * @param root
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/11/3 07:50
     */
    public static <T> List<T> inOrderTraverse(BitNode<T> root) {
        List<T> resList = new ArrayList<>();
        if (root == null)
            return resList;

        Stack<BitNode<T>> stack = new Stack<>();
        BitNode<T> cur = root;
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.lchild;
            } else {
                cur = stack.pop();
                resList.add(cur.data);
                cur = cur.rchild;
            }
        }
        return resList;
    }

    /**
     * 非递归前序遍历
     *
     * @param root
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/11/2 22:28
     */
    public static <T> List<T> preOrderTraverse(BitNode<T> root) {
        List<T> resList = new ArrayList<>();
        if (root == null)
            return resList;

        Stack<BitNode<T>> stack = new Stack<>();
        BitNode<T> cur = root;

        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                resList.add(cur.data);
                stack.push(cur);
                cur = cur.lchild;
            } else {
                cur = stack.pop();
                cur = cur.rchild;
            }
        }

        return resList;
    }


    /**
     * 递归前序遍历二叉树
     *
     * @param root
     * @param orderList
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/11/2 16:16
     */
    public static <T> List<T> preOrderRecursion2(BitNode<T> root, List<T> orderList) {
        if (orderList == null)
            orderList = new ArrayList<>();
        if (root == null)
            return orderList;
        orderList.add(root.data);

        preOrderRecursion2(root.lchild, orderList);
        preOrderRecursion2(root.rchild, orderList);
        return orderList;
    }


    /**
     * 中序遍历 递归
     *
     * @param root
     * @param orderList
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/11/2 17:50
     */
    public static <T> List<T> inOrderRecursion2(BitNode<T> root, List<T> orderList) {
        if (orderList == null)
            orderList = new ArrayList<>();
        if (root == null)
            return orderList;

        inOrderRecursion2(root.lchild, orderList);
        orderList.add(root.data);
        inOrderRecursion2(root.rchild, orderList);
        return orderList;
    }

    /**
     * 后续遍历 递归
     *
     * @param root
     * @param orderList
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/11/2 17:50
     */
    public static <T> List<T> postOrderRecursion2(BitNode<T> root, List<T> orderList) {
        if (orderList == null)
            orderList = new ArrayList<>();
        if (root == null)
            return orderList;

        inOrderRecursion2(root.lchild, orderList);
        inOrderRecursion2(root.rchild, orderList);
        orderList.add(root.data);
        return orderList;
    }

    /**
     * 递归前序遍历
     *
     * @param root
     * @return java.util.List<java.lang.Integer>
     * @author tqyao
     * @create 2022/10/17 16:54
     */
    public static <T> List<T> preOrderRecursion(BitNode<T> root) {
        List<T> list = new ArrayList<>();
        preOrder(root, list);
        return list;
    }

    public static <T> void preOrder(BitNode<T> root, List<T> li) {
        if (null == li)
            li = new ArrayList<T>();

        if (null != root) {
            li.add(root.data);
            preOrder(root.lchild, li);
            preOrder(root.rchild, li);
        }
    }

    static class BitNode<T> {
        T data;
        BitNode<T> lchild, rchild;

        public BitNode() {
        }

        public BitNode(T data) {
            this(null, null, data);
        }

        public BitNode(BitNode lchild, BitNode rchild, T data) {
            this.lchild = lchild;
            this.rchild = rchild;
            this.data = data;
        }
    }
}

