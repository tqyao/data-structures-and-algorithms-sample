/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.tree;

/**
 * 二叉排序树
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/11/05 16:24
 */
public class BinarySortTree_04 {

    BinarySortNode root;

    /**
     * 插入节点（遍历）
     *
     * @param value
     * @author tqyao
     * @create 2022/11/9 16:59
     */
    public boolean insertNodeTraverse(int value) {
        BinarySortNode pre, cur;
        // 排序遍历的上一个节点
        pre = null;
        // 当前节点
        cur = root;
        // 当前节点不为空，按照大于右子树，小于左子树规则查找。当前节点为空，说明找到要插入的位置
        while (cur != null) {
            // 每次记录遍历过的前一个节点
            pre = cur;
            if (value < cur.data)
                cur = cur.lchild;
            else
                cur = cur.rchild;
        }
        BinarySortNode newNode = new BinarySortNode(value);
        // pre 为空，说明是空树（cur = root），构造插入节点给根节点
        if (pre == null)
            root = newNode;
            // 上面循环已经找到要插入的节点的上一个节点，此处判断插入左还是右孩子
        else if (value < pre.data)
            pre.lchild = newNode;
        else if (value > pre.data)
            pre.rchild = newNode;
            // 如果 插入节点值与pre相等，则插入失败
        else
            return false;
        return true;
    }

    /**
     * 插入节点（递归）
     *
     * @param cur
     * @param value
     * @return fun.tqyao.tree.BinarySortTree_04.BinarySortNode
     * @author tqyao
     * @create 2022/11/9 16:39
     */
    public BinarySortNode insertNodeRecursion(BinarySortNode cur, int value) {
        /*
        当前节点为空 a. 空树，直接返回插入节点
        b. 递归到最深层，构造插入节点返回
         */
        if (cur == null)
            return new BinarySortNode(value);
        // 插入值与排序二叉树节点值相等，返回空，不进行插入
        if (value == cur.data)
            return null;
        //插入值小于当前节点值，插入到左子树，否则插入到右子树。
        if (value < cur.data)
            cur.lchild = insertNodeRecursion(cur.lchild, value);
        else
            cur.rchild = insertNodeRecursion(cur.rchild, value);
        // 返回当前节点，保持树的父子关系
        return cur;
    }

    /**
     * 排序二叉树查找（递归）
     *
     * @param cur
     * @param value
     * @return fun.tqyao.tree.BinarySortTree_04.BinarySortNode
     * @author tqyao
     * @create 2022/11/9 11:14
     */
    public BinarySortNode searchNodeRecursion(BinarySortNode cur, int value) {
        // 当前节点为空，返回空
        if (null == cur)
            return null;
        // 当前节点值等于查找值，返回当前节点
        if (cur.data == value)
            return cur;
        // 当前节点值小于查找值，在右子树查找，否则左子树
        if (value < cur.data)
            // 接收下层找到的（没找到的）节点，并且返回给上次
            return searchNodeRecursion(cur.lchild, value);
        else
            return searchNodeRecursion(cur.rchild, value);
    }

    /**
     * 排序二叉树查找（遍历）
     *
     * @param value
     * @return fun.tqyao.tree.BinarySortTree_04.BinarySortNode
     * @author tqyao
     * @create 2022/11/9 11:41
     */
    public BinarySortNode searchNodeTraverse(int value) {
        BinarySortNode temp = root;
        while (null != temp) {
            if (value == temp.data)
                break;
            if (value < temp.data)
                temp = temp.lchild;
            else
                temp = temp.rchild;
        }
        return temp;
    }


    static class BinarySortNode {
        int data;
        BinarySortNode lchild, rchild;

        public BinarySortNode(int data) {
            this(data, null, null);
        }

        public BinarySortNode(BinarySortNode lchild, BinarySortNode rchild) {
            this(0, lchild, rchild);
        }

        public BinarySortNode(int data, BinarySortNode lchild, BinarySortNode rchild) {
            this.data = data;
            this.lchild = lchild;
            this.rchild = rchild;
        }
    }
}
