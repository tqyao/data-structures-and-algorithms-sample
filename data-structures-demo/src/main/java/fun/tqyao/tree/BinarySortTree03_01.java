/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.tree;

import java.util.*;

/**
 * 二叉排序树
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/11/05 16:24
 */
public class BinarySortTree03_01 {

    BinarySortNode root;


    public void removeRecursion(int value) {
        Optional.ofNullable(searchNodeTraverse(value))
                .ifPresent(specify -> removeRecursion(root, specify));
    }


    /**
     * 递归删除节点
     *
     * @param root
     * @param specifyNode
     * @return fun.tqyao.tree.BinarySortTree03_01.BinarySortNode
     * @author tqyao
     * @create 2022/11/12 21:41
     */
    private BinarySortNode removeRecursion(BinarySortNode root, BinarySortNode specifyNode) {
        // 初始排序二叉树为空 或 删除节点不存在于排序树中
        if (root == null || specifyNode == null)
            return null;

        if (root.data > specifyNode.data)
            // 待删除节点值小于当前节点，带删除节点在"左子树"中
            root.lchild = removeRecursion(root.lchild, specifyNode);
        else if (root.data < specifyNode.data)
            // 待删除节点值大于当前节点，带删除节点在"右子树"中
            root.rchild = removeRecursion(root.rchild, specifyNode);
        else {  // 当前节点=待删除节点
            if (root.lchild != null && root.rchild != null) {
                /**
                 * 待删除节点的左右孩子都存在：
                 * 1. 找到右子树中最小节点，即中序直接后继
                 * 2. 用最小节点值替换当前待删除节点值，维护删除节点后的排序二叉树的 左< 根 < 右 性质
                 * 3. 删除最小节点
                 */
                BinarySortNode rightMinNode = root.rchild;
                while (rightMinNode.lchild != null)
                    rightMinNode = rightMinNode.lchild;
                root.data = rightMinNode.data;
                // 3. ⭐ ️递归删除最小节点
                root.rchild = removeRecursion(root.rchild, rightMinNode);
            } else
                // 待删除节点是叶子节点 或 只有一个孩子情况
                root = root.lchild != null ? root.lchild : root.rchild;
        }

        // 返回当前节点，维持父子节点关系
        return root;
    }


    /**
     * 中序 非递归
     *
     * @return java.util.List<java.lang.Integer>
     * @author tqyao
     * @create 2022/11/11 17:19
     */
    public List<Integer> inOrderTraverse() {
        List<Integer> orderList = new ArrayList<>();
        Stack<BinarySortNode> stack = new Stack<>();
        BinarySortNode temp = root;
        while (!stack.isEmpty() || temp != null) {
            if (temp != null) {
                stack.push(temp);
                temp = temp.lchild;
            } else {
                // 第二次处理该节点
                temp = stack.pop();
                orderList.add(temp.data);
                // 转向右子树
                temp = temp.rchild;
            }
        }
        return orderList;
    }

    /**
     * 中序递归遍历
     *
     * @param root
     * @param order
     * @return java.util.List<java.lang.Integer>
     * @author tqyao
     * @create 2022/11/11 16:56
     */
    public List<Integer> inOrderRecursion(BinarySortNode root, List<Integer> order) {
        if (order == null)
            order = new ArrayList<>();
        if (root == null)
            return order;
        inOrderRecursion(root.lchild, order);
        order.add(root.data);
        inOrderRecursion(root.rchild, order);
        return order;
    }


    /**
     * 删除节点
     *
     * @param value
     * @return boolean
     * @author tqyao
     * @create 2022/11/11 20:24
     */
    public boolean remove(int value) {
        // 指定值是否存在对应节点在排序树中
        BinarySortNode delNode = searchNodeTraverse(value);
        // 不存在返回删除失败
        if (delNode == null)
            return false;

        BinarySortNode parent = searchParent(root, delNode);
        // 1. 删除的是叶子节点
        if (delNode.lchild == null && delNode.rchild == null) {
            // 叶子节点就是根节点
            if (parent == null)
                root = null;
            else if (parent.lchild == delNode)
                // 删除节点是其父节点的左孩子
                parent.lchild = null;
            else
                // 否则删除节点是其父节点的右孩子
                parent.rchild = null;
            return true;
        }

        // 2. 删除节点有且只有一个孩子
        // 2.1 删除节点有左孩子没有有孩子
        if (delNode.lchild != null && delNode.rchild == null) {
            // 要删除的是只有左子树的根节点
            if (parent == null)
                root = delNode.lchild;
            else if (parent.lchild == delNode)
                // 删除节点是其父节点的左孩子
                parent.lchild = delNode.lchild;
            else
                // 否则删除节点是其父节点的右孩子
                parent.rchild = delNode.lchild;
            return true;
        }

        // 2.2 删除节点无左孩子有右孩子
        if (delNode.lchild == null && delNode.rchild != null) {
            if (parent == null)
                root = delNode.rchild;
            else if (parent.lchild == delNode)
                parent.lchild = delNode.rchild;
            else
                parent.rchild = delNode.rchild;
            return true;
        }

        // 3. 删除节点有左孩子和右孩子
        // a. 找寻右子树的最小子节点（待删除节点的直接中序后继），记录该节点值用户替换待删除节点值
        // b. 删除中序后继节点，该中序后继一定满足 2.
        // 注意最小子节点就是待删除节点的右孩子的情况
        if (delNode.lchild != null && delNode.rchild != null) {
            BinarySortNode minNode = delNode.rchild;
            // 查询中序直接后继
            while (minNode.lchild != null)
                minNode = minNode.lchild;
            int temp = minNode.data;
            BinarySortNode minParent = searchParent(delNode.rchild, minNode);
            if (minParent == null)
                // 最小子节点就是待删除节点的右孩子，根据中序遍历规则，此时最小子节点的左子树一定为空，这里删除最小子节点相当于2.2
                delNode.rchild = minNode.rchild;
            else
                // 删除最小子节点，此时情况一定是属于 2.2 里else if分支
                minParent.lchild = minNode.rchild;
            delNode.data = temp;
            return true;
        }

        return false;
    }


    /**
     * 删除节点
     *
     * @param value
     * @return boolean
     * @author tqyao
     * @create 2022/11/11 16:22
     */
    public boolean delNode(int value) {
        BinarySortNode delNode = searchNodeTraverse(value);
        if (null == delNode)
            return false;

        // 1. 如果是待删除节点是叶子节点
        BinarySortNode parent = searchParent(delNode);
//        if (delNode.lchild == null && delNode.rchild == null) {
//            if (null == parent)
//                root = null;
//            else if (parent.lchild != null && parent.lchild == delNode)
//                parent.lchild = null;
//            else
//                parent.rchild = null;
//            return true;
//        }


//        // 2.1 如果待删除节的左孩子存在且右孩子不存在
//        if (delNode.lchild != null && delNode.rchild == null) {
//            if (parent == null)
//                root = delNode.lchild;
//            else if (parent.rchild != null && parent.rchild == delNode)
//                parent.rchild = delNode.lchild;
//            else
//                parent.lchild = delNode.lchild;
//            return true;
//        }
//
//        // 2.2 若果待删除节点的左孩子不存在且右孩子存在
//        if (delNode.lchild == null && delNode.rchild != null) {
//            if (parent == null)
//                root = delNode.rchild;
//            else if (parent.lchild != null && parent.lchild == delNode)
//                parent.lchild = delNode.rchild;
//            else
//                parent.rchild = delNode.rchild;
//        }

        if (!delLeafNode(parent, delNode))
            if (!delLonelyNode(parent, delNode))
                /*
                 * 3. 如果待删除节点的左或右孩子都存在
                 * a. 用待删除节点的中序直接后继（或直接前驱）替换掉待删除节点
                 * b. 此时中序直接后，且情况一定属于步骤1.继（前驱）成了待删除节点 或者2.
                 * */
                if (delNode.lchild != null && delNode.rchild != null) {
                    // delNode中序遍历的第一个后继
                    BinarySortNode firstSubInOrder = getSubOfInOrder(delNode.rchild);
                    // 后继的父节点
                    BinarySortNode subParent = searchParent(firstSubInOrder);
                    // b.
                    if (!delLeafNode(subParent, firstSubInOrder))
                        delLonelyNode(subParent, firstSubInOrder);
                    // a.
                    if (delNode.lchild != null)
                        firstSubInOrder.lchild = delNode.lchild;
                    if (delNode.rchild != null)
                        firstSubInOrder.rchild = delNode.rchild;
                }

        return true;
    }

    /**
     * 获取指定节点的第一个中序遍历节点
     *
     * @param specifyNode
     * @return fun.tqyao.tree.BinarySortTree_04.BinarySortNode
     * @author tqyao
     * @create 2022/11/10 23:19
     */
    private BinarySortNode getSubOfInOrder(BinarySortNode specifyNode) {
        if (specifyNode == null)
            return null;
        while (specifyNode.lchild != null)
            specifyNode = specifyNode.lchild;
        return specifyNode;
    }

    /**
     * 删除只有单一孩子的节点
     *
     * @param parent
     * @param delNode
     * @return boolean
     * @author tqyao
     * @create 2022/11/10 23:09
     */
    private boolean delLonelyNode(BinarySortNode parent, BinarySortNode delNode) {
        // 2.1 如果待删除节的左孩子存在且右孩子不存在
        if (delNode.lchild != null && delNode.rchild == null) {
            if (parent == null)
                root = delNode.lchild;
            else if (parent.rchild != null && parent.rchild == delNode)
                parent.rchild = delNode.lchild;
            else
                parent.lchild = delNode.lchild;
            return true;
        }

        // 2.2 若果待删除节点的左孩子不存在且右孩子存在
        if (delNode.lchild == null && delNode.rchild != null) {
            if (parent == null)
                root = delNode.rchild;
            else if (parent.lchild != null && parent.lchild == delNode)
                parent.lchild = delNode.rchild;
            else
                parent.rchild = delNode.rchild;
            return true;
        }
        return false;
    }

    /**
     * 删除叶子节点
     *
     * @param parent
     * @param delNode
     * @return boolean
     * @author tqyao
     * @create 2022/11/10 20:49
     */
    private boolean delLeafNode(BinarySortNode parent, BinarySortNode delNode) {
        // 1. 如果是待删除节点是叶子节点
        if (delNode.lchild == null && delNode.rchild == null) {
            if (null == parent)
                root = null;
            else if (parent.lchild != null && parent.lchild == delNode)
                parent.lchild = null;
            else
                parent.rchild = null;
            return true;
        }
        return false;
    }


    /**
     * 在指定的排序二叉树中查找指定节点的父节点
     *
     * @param root
     * @param specifyNode
     * @return fun.tqyao.tree.BinarySortTree_04.BinarySortNode
     * @author tqyao
     * @create 2022/11/11 20:30
     */
    private BinarySortNode searchParent(BinarySortNode root, BinarySortNode specifyNode) {
        if (root == null || specifyNode == null)
            return null;
        BinarySortNode parent = null;
        while (root != null) {
            if (root.data == specifyNode.data)
                return parent;
            // 记录当前节点作为下次迭代的父节点
            parent = root;
            // 指定节点值小于当前节点值，左子树迭代查找
            if (root.data > specifyNode.data)
                root = root.lchild;
            else
                root = root.rchild;
        }
        // 指定节点在排序树中不存在
        return null;
    }

    /**
     * 在跟节点中查找指定节点的父节点
     * error : 如果指定节点不存在，会返回叶子节点作为父节点
     *
     * @param specifyNode
     * @return fun.tqyao.tree.BinarySortTree_04.BinarySortNode
     * @author tqyao
     * @create 2022/11/10 18:13
     */
    @Deprecated
    private BinarySortNode searchParent(BinarySortNode specifyNode) {
        BinarySortNode cur, parent;
        parent = null;
        cur = root;
        while (cur != null) {
            parent = cur;
            if (cur.data < specifyNode.data)
                cur = cur.lchild;
            else if (cur.data > specifyNode.data)
                cur = cur.rchild;
            else
                break;
        }
        return parent;
    }


    /**
     * 创建排序二叉树 by Traverse
     *
     * @param nodeVals
     * @author tqyao
     * @create 2022/11/10 08:55
     */
    public void createBSTByTraverse(int[] nodeVals) {
        Arrays.stream(nodeVals).forEach(this::insertNodeTraverse);
    }

    /**
     * 创建排序二叉树 by Traverse
     *
     * @param nodeVals
     * @author tqyao
     * @create 2022/11/10 08:55
     */
    public void createBSTByRecursion(int[] nodeVals) {
//        for (int i : nodeVals)
//            insertNodeRecursion(root, i);
        Arrays.stream(nodeVals).forEach(vals -> root = insertNodeRecursion(root, vals));
    }


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
            return cur;
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

    public BinarySortNode searchNodeTraverse(int value) {
        return searchNodeTraverse(root, value);
    }

    /**
     * 排序二叉树查找（遍历）
     *
     * @param value
     * @return fun.tqyao.tree.BinarySortTree_04.BinarySortNode
     * @author tqyao
     * @create 2022/11/9 11:41
     */
    private BinarySortNode searchNodeTraverse(BinarySortNode root, int value) {
        while (null != root) {
            if (value == root.data)
                break;
            if (value < root.data)
                root = root.lchild;
            else
                root = root.rchild;
        }
        return root;
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

//        @Override
//        public String toString() {
//            return "BinarySortNode{" +
//                    "data=" + data +
//                    ", lchild.data=" + lchild != null ? String.valueOf(lchild.data) : "0" +
//                    ", rchild.data=" + rchild != null ? String.valueOf(rchild.data) : "0" +
//                    '}';
//        }

        @Override
        public String toString() {
            Integer ldata, rdata;
            ldata = lchild != null ? lchild.data : 0;
            rdata = rchild != null ? rchild.data : 0;
            return "BinarySortNode{" +
                    "data=" + data +
                    ", lchild.data=" + ldata +
                    ", rchild.data=" + rdata +
                    '}';
        }
    }
}
