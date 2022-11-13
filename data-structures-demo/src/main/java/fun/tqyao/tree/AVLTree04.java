/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * 平衡二叉树
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/11/12 10:37
 */
public class AVLTree04<T extends Comparable<T>> {

    AVLTreeNode<T> root;


    class AVLTreeNode<T extends Comparable<T>> {
        T data;
        // 树高
        int height;
        AVLTreeNode<T> left, right;


        public AVLTreeNode(T data) {
            this(data, 0, null, null);
        }

        public AVLTreeNode(T data, int height, AVLTreeNode<T> left, AVLTreeNode<T> right) {
            this.data = data;
            this.height = height;
            this.left = left;
            this.right = right;
        }
    }


    public List<T> inOrderTraverse() {
        return inOrderTraverse(root);
    }

    /**
     * 中序遍历（非递归）
     *
     * @param tree
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/11/13 16:29
     */
    private List<T> inOrderTraverse(AVLTreeNode<T> tree) {
        List<T> resList = new ArrayList<>();
        if (tree == null)
            return resList;

        Stack<AVLTreeNode<T>> stack = new Stack<>();
        AVLTreeNode<T> temp = tree;
        while (!stack.isEmpty() || temp != null) {
            if (temp != null) {
                stack.push(temp);
                temp = temp.left;
            } else {
                temp = stack.pop();
                resList.add(temp.data);
                temp = temp.right;
            }
        }
        return resList;
    }


    public AVLTreeNode<T> searchRecursion(T value) {
        return searchRecursion(root, value);
    }

    /**
     * 递归搜索
     *
     * @param tree
     * @param value
     * @return fun.tqyao.tree.AVLTree<T>.AVLTreeNode<T>
     * @author tqyao
     * @create 2022/11/13 16:06
     */
    private AVLTreeNode<T> searchRecursion(AVLTreeNode<T> tree, T value) {
        if (tree == null)
            return null;
        int cmp = value.compareTo(tree.data);
        if (cmp < 0)
            return searchRecursion(tree.left, value);
        else if (cmp > 0)
            return searchRecursion(tree.right, value);
        else
            return tree;
    }

    public void remove(T data) {
        Optional.ofNullable(searchRecursion(data))
                .ifPresent(del -> root = remove(root, del));
    }

    /**
     * 删除节点
     *
     *
     * @param tree  指定的平衡二叉树根节点
     * @param toDel 要删除的节点
     * @return fun.tqyao.tree.AVLTree<T>.AVLTreeNode<T> 根节点
     * @author tqyao
     * @create 2022/11/13 11:16
     */
    private AVLTreeNode<T> remove(AVLTreeNode<T> tree, AVLTreeNode<T> toDel) {
        // 空的平衡二叉树 或 删除节点不存在于平衡二叉树中 直接返回空
        if (tree == null || toDel == null)
            return null;
        // 待删除节点与当前节点值比较
        int cmp = toDel.data.compareTo(tree.data);
        if (cmp < 0) {
            // 待删除节点值小于当前节点值，待删除节点在"左子树中"
            tree.left = remove(tree.left, toDel);
            // 判断删除节点后，是否失去平衡
            // 由于是左子树删除，所以是用 右子树-左子树高度差 == 2
            if (height(tree.right) - height(tree.left) == 2) {
                /*
                 判断是什么类型旋转
                 由于是左子树删除了一个节点导致失去平衡，这里可以看成是右子树多插入了一个节点导致的不平衡
                 ，现在就需要判断是右孩子的左孩子插入导致的不平衡（RL 情况）还是右孩子的右孩子插入导致不平衡（RR情况）
                 */
                if (toDel.data.compareTo(tree.right.data) < 0)
                    // 待删除节点是小于右孩子，属于"右孩子左子树（RL）"情况
                    tree = rightLeftRotation(tree);
                else
                    // 待删除节点是大于右孩子，属于"右孩子右子树（RR）"情况
                    tree = rightRightRotation(tree);
            }
        } else if (cmp > 0) {
            tree.right = remove(tree.right, toDel);
            if (height(tree.left) - height(tree.right) == 2) {
                if (toDel.data.compareTo(tree.left.data) > 0)
                    tree = leftRightRotation(tree);
                else
                    tree = leftLeftRotation(tree);
            }
        } else { //cmp = 0，当前节点就是待删除节点
            // 待删除节点的左右子树不为空的情况
            if (tree.left != null && tree.right != null) {
                if (height(tree.left) > height(tree.right)) {
                    // 左子树比右子树高，且有且仅高出1层
                    /**
                     * 找到左子树中最大节点，"替换当前待删除节点并删除该最大节点"：3步骤：
                     *  (01)找出tree的右子树中的最小节点
                     *  (02)将该最小节点的值赋值给tree。
                     *  (03)删除该最小节点。
                     * 采用这种方式的好处是: 删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                     */
                    /*
                    todo: 如果左子树中最大节点删除后导致，最小不平衡子树变成了该删除节点的父节点❓ 例如删除20
                    todo: 解决疑问!  由于是递归调用去删除"最大节点"，删除后失衡是会判断并处理的❗️
                     */
                    AVLTreeNode<T> maxNode = tree.left;
                    while (maxNode.right != null)
                        maxNode = maxNode.right;
                    tree.data = maxNode.data;
                    // ⭐️ 递归删除最大节点
                    tree.left = remove(tree.left, maxNode); // 返回值赋值给tree.left 以维持树的亲子关系
                } else {
                    // 左子树不比右子树高，即左子树比右子树少1层 或 等于右子树高度
                    // 找到右子树中最小节点，"替换当前待删除节点并删除该最小节点"
                    // todo: 同理
                    AVLTreeNode<T> minNode = tree.right;
                    while (minNode.left != null)
                        minNode = minNode.left;
                    tree.data = minNode.data;
                    // ⭐
                    tree.right = remove(tree.right, minNode);
                }
            } else
                // 当前待删除节点是叶子节点 或 只有一个孩子
                tree = tree.left != null ? tree.left : tree.right;
        }
        // 删除节点后，更新当前节点高度
        Optional.ofNullable(tree).ifPresent(curNode -> curNode.height =  max(height(curNode.left), height(curNode.right)) + 1);
//        tree.height = max(height(tree.left), height(tree.right)) + 1;
        return tree;
    }


    /**
     * 向根节点插入
     *
     * @param data
     * @author tqyao
     * @create 2022/11/12 20:20
     */
    public void insert(T data) {
        root = insert(root, data);
    }

    /**
     * 插入节点(递归)
     *
     * @param tree 要插入的AVL
     * @param data 插入节点值
     * @return fun.tqyao.tree.AVLTree<T>.AVLTreeNode<T> 根节点
     * @author tqyao
     * @create 2022/11/12 20:18
     */
    private AVLTreeNode<T> insert(AVLTreeNode<T> tree, T data) {
        // 递归最深处，此时没有就是新节点的插入位置，创建节点返回
        if (tree == null)
            return new AVLTreeNode<>(data);
        else {
            // 比较插入元素与当前节点大小，判断插入位置
            int cmp = data.compareTo(tree.data);
            // 插入元素小于当前节点元素大小，左子孩子插入
            if (cmp < 0) {
                tree.left = insert(tree.left, data);
                // 判断是插入后否失去平衡，由于是左插入，左子树高度必定大于右子树
                if (height(tree.left) - height(tree.right) == 2) {
                    // 插入后导致不平衡，调整平衡，当前节点为最小不平衡子树根节点
                    // 判断是当前节点的左孩子的左子树插入还是右子树插入导致的不平衡
                    /**
                     *  让 tree 等于 LL 或 LR 旋转后的根节点，最终执行到方法最后一行返回给上一层调用（维持新父子关系）
                     *  ，这里作用是：修正了最小不平衡子树根节点在旋转后，其父节点应该指向的是旋转后平衡子树的根节点
                     */
                    if (data.compareTo(tree.left.data) < 0)
                        //左孩子左子树插入，LL 旋转
                        tree = leftLeftRotation(tree);
                    else
                        // 左孩子右子树插入，LR 旋转
                        tree = leftRightRotation(tree);
                }
            } else if (cmp > 0) {
                tree.right = insert(tree.right, data);
                if (height(tree.right) - height(tree.left) == 2) {
                    if (data.compareTo(tree.right.data) > 0)
                        tree = rightRightRotation(tree);
                    else
                        tree = rightLeftRotation(tree);
                }
            } else { // cmp==0
                System.out.println("插入失败，不允许添加相同值的节点！");
            }
        }

        tree.height = max(height(tree.left),height(tree.right)) + 1;
        // 返回当前节点，维持树的父子关系
        return tree;
    }


    /**
     * RL 右孩子的左子树插入节点导致不平衡的平衡调整
     *
     * @param k1
     * @return fun.tqyao.tree.AVLTree<T>.AVLTreeNode<T> 旋转后的根节点
     * @author tqyao
     * @create 2022/11/12 14:10
     */
    private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> k1) {
        k1.right = leftLeftRotation(k1.right);
        return rightRightRotation(k1);
    }


    /**
     * LR 左孩子的左子树插入节点导致不平衡的平衡调整
     *
     * @param k3 最小不平衡子树根节点
     * @return fun.tqyao.tree.AVLTree<T>.AVLTreeNode<T> 旋转后的根节点
     * @author tqyao
     * @create 2022/11/12 13:49
     */
    private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> k3) {
        // 先对左子树右孩子只能左上旋（属于RR情况），返回值是上旋后的根节点
        // ，此时最小不平衡子树的根节点（k3）左孩子任然是指向未旋转时的左孩子
        // ，需要令最小不平衡根节点指向上旋后的根节点
        k3.left = rightRightRotation(k3.left);
        // 左孩子只能右上旋（属于LL情况），返回值是上旋后的根节点
        return leftLeftRotation(k3);
    }

    /**
     * LL 左左情况（单旋）
     *
     * @param k2
     * @return fun.tqyao.tree.AVLTree<T>.AVLTreeNode<T> 旋转后的根节点
     * @author tqyao
     * @create 2022/11/12 11:04
     */
    private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1;

        // 令 k1 为最小不平衡子树的左孩子
        k1 = k2.left;
        /*
        当k1向右上"旋转"，此时根据平衡二叉树性质
        ，即"旋转"后任要保持左子树<根节点<右子树
        ，即 k1L < k1 < k1R < k2 < k2R
        ，k2 成为 k1 的右孩子节点，k1 的右孩子成为 k2 的左孩子
         */
        k2.left = k1.right;
        k1.right = k2;

        k2.height = max(height(k2.right), height(k2.left)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /**
     * RR 右孩子的右子树插入节点导致不平衡的平衡调整
     *
     * @param k2
     * @return fun.tqyao.tree.AVLTree<T>.AVLTreeNode<T> 旋转后的根节点
     * @author tqyao
     * @create 2022/11/12 11:42
     */
    private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1;

        k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;

        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(k2.height, height(k1.right)) + 1;
        return k1;
    }


    /**
     * 比较两个值大小
     *
     * @return int 返回较大值
     * @author tqyao
     * @create 2022/11/12 10:57
     */
    private int max(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * 树高
     *
     * @param node
     * @return int
     * @author tqyao
     * @create 2022/11/12 10:56
     */
    private int height(AVLTreeNode<T> node) {
        if (node == null)
            return 0;
        return node.height;
    }

    public int height() {
        return height(root);
    }
}
