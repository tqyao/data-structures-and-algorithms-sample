package fun.mj.tree2;

import fun.mj.util.printer.BinaryTreeInfo;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCSTATE;

import java.awt.*;
import java.awt.event.HierarchyBoundsAdapter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


@SuppressWarnings("unchecked")
public class BinaryTree<E> implements BinaryTreeInfo {
    protected int size;
    protected Node<E> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 代码类似层序遍历，但是是栈
     *
     * @param visitor
     * @author tqyao
     * @create 2023/10/9 20:26
     */
    public void preorder300(Visitor<E> visitor) {
        if (visitor == null || root == null) return;
        Node<E> node;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            node = stack.pop();
            if (visitor.visit(node.element)) return;

            if (node.right != null) {
                stack.push(node.right);
            }

            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public void preorder(Visitor<E> visitor) {
        if (visitor == null || root == null) return;
        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();
        while (true) {
            if (node != null) {
                // 访问 node 节点
                if (visitor.visit(node.element)) return;
                // 右子节点入栈
                if (node.right != null) {
                    stack.push(node.right);
                }
                node = node.left;
            } else if (stack.isEmpty()) {
                return;
            } else {
                // 处理右边
                node = stack.pop();
            }
        }
    }

    public void preorder200(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;

        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                // 访问
                if (visitor.visit(node.element)) return;
                stack.push(node);
                // 一直向左走
                node = node.left;
            } else {
                // node 为空，检查是否存在右子树
                node = stack.pop().right;
            }
        }
    }

    public void inorder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;

        while (true) {
            if (node != null) {
                // 一路向左全部入栈
                stack.push(node);
                node = node.left;
            } else if (stack.isEmpty()) {
                return;
            } else { // 左子树尽头，不存在左子树了。处理右子树

                // 弹出栈顶访问
                node = stack.pop();
                if (visitor.visit(node.element)) return;
                node = node.right;
            }
        }
    }

    public void inorder200(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;

        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                if (visitor.visit(node.element)) return;
                node = node.right;
            }
        }
    }

    public void postorder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;
        Stack<Node<E>> stack = new Stack<>();
        Node<E> cur = root;
        Node<E> prev = null;

        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {//一路向左
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.peek();
                if (cur.right != null && prev != cur.right) {// 右子树存在&&没有访问过
                    cur = cur.right;
                } else {
                    //弹出该栈顶元素，直接访问当前节点
                    cur = stack.pop();
                    if (visitor.visit(cur.element)) return;
                    prev = cur;
                    // 迭代。再次查看栈顶
                    cur = null;
                }
            }
        }

    }

    public void levelOrder(Visitor<E> visitor) {
        if (root == null || visitor == null) return;

        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) return;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else { // 后面遍历的节点都必须是叶子节点
                leaf = true;
            }
        }

        return true;
    }

    public int height() {
        if (root == null) return 0;

        // 树的高度
        int height = 0;
        // 存储着每一层的元素数量
        int levelSize = 1;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }

            if (levelSize == 0) { // 意味着即将要访问下一层
                levelSize = queue.size();
                height++;
            }
        }

        return height;
    }

    public int height2() {
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    protected Node<E> predecessor(Node<E> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（left.right.right.right....）
        Node<E> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        // node.parent == null
        // node == node.parent.right
        return node.parent;
    }

    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    public static abstract class Visitor<E> {
        /**
         * @return 如果返回true，就代表停止遍历
         */
        public abstract boolean visit(E element);
    }

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return node;
    }
}
