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


    @Test
    public void testInit() {
        // todo:?
        BitNode<String> b1 = new BitNode<>();
        System.out.println(preorderTraversal(b1));

        BitNode b2 = new BitNode<>();
        System.out.println(preorderTraversal(b2));
    }


    public static BitNode createBiTree() {
        Scanner scanner = new Scanner(System.in);
        BitNode<String> root = new BitNode<>();
        System.out.print("请输入树的节点(从上到下从左到右，0代表空节点，以空格分隔，回车以结束)>>");
        String[] inp = scanner.nextLine().split(" ");
        System.out.println(Arrays.toString(inp));



        return null;
    }


    /**
     * 测试容器作为容器元素的储存原理
     * 结论：引用传递
     *
     * @author tqyao
     * @create 2022/10/18 14:06
     */
    @Test
    public void testReferencePass() {
        List<List<String>> list = new ArrayList<>();
        List<String> els = new ArrayList<>();
        els.add("a");
        els.add("b");
        els.add("c");
        list.add(els);

        els.clear();
        els.add("1");
        els.add("2");
        els.add("3");
        list.add(els);

        System.out.println(list);
    }

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


    @Test
    public void testPreOrder() {

    }

    /**
     * 递归前序遍历
     *
     * @param root
     * @return java.util.List<java.lang.Integer>
     * @author tqyao
     * @create 2022/10/17 16:54
     */
    public static <T> List<T> preorderTraversal(BitNode<T> root) {
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
