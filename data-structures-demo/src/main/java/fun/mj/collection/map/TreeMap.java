/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.collection.map;

import fun.mj.collection.Map;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 利用"红黑树数据结构"实现映射
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/04 09:56
 */
public class TreeMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private Node<K, V> root;
    private Comparator<K> comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key); // 传入元素不能为空

        // 添加第一个节点
        if (root == null) {
            root = new Node<>(key, value, null);
            size++;

            // 新添加节点之后的处理
            // BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            afterPut(root);
            return null;
        }

        // 添加的不是第一个节点
        // 找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        do {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                // 相等时的操作按需求来定，这里选择了覆盖
                node.key = key;
                V oldVal = node.value;
                node.value = value;
                return oldVal;  // 覆盖返回旧节点值
            }
        } while (node != null);

        // 通过上面的while循环找到了要插入节点的父节点
        // 看看插入到父节点的哪个位置
        Node<K, V> newNode = new Node<>(key, value, parent);

        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        // 新添加节点之后的处理
        // BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
        afterPut(newNode);

        // 新节点添加返回空
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> n = node(key);
        return n == null ? null : n.value;
    }

    /**
     * 根据节点的 key 查找所在的 node
     *
     * @param key
     * @return fun.tqyao.collection.map.TreeMap.Node<K, V>
     * @author tqyao
     * @create 2023/10/4 10:52
     */
    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int cmp = compare(node.key, key);
            if (cmp == 0) {
                return node;
            }
            if (cmp > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }


    @Override
    public V remove(K key) {
        return remove(node(key));
    }


    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    /**
     * value 没有比较性，因此需要遍历整个树
     *
     * @param value
     * @return boolean
     * @author tqyao
     * @create 2023/10/4 11:18
     */
    @Override
    public boolean containsValue(V value) {
        if (root == null) return false;
        // 层序遍历
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<K, V> poll = queue.poll();
            if (isEquals(value, poll.value)) {
                return true;
            }

            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
        return false;
    }

    private boolean isEquals(V val1, V val2) {
//        if (val1 == null && val2 == null) {
//            return true;
//        } else if (val1 == null) {
//            return false;
//        } else if (val2 == null) {
//            return false;
//        } else if (val1.equals(val2)) {
//            return true;
//        }
//        return false;
        return val1 == null ? val2 == null : val1.equals(val2);
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        traversal(root, visitor);
    }

    private void traversal(Node<K, V> node, Visitor<K, V> visitor) {
        if (node == null || visitor.stop) {
            return;
        }
        traversal(node.left, visitor);
        if (visitor.stop) return;
        visitor.visit(node.key, node.value);
        traversal(node.right, visitor);
    }

    protected void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent))
            return;

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            afterPut(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    /**
     * 左旋
     */
    protected void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 右旋
     */
    protected void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 公共代码：不管是左旋、右旋，都要执行的
     *
     * @param grand  失衡节点
     * @param parent 失衡节点的tallerChild
     * @param child  g和p需要交换的子树（本来是p的子树，后来会变成g的子树）
     */
    protected void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) { // grand是左子树
            grand.parent.left = parent;
        } else if (grand.isRightChild()) { // grand是右子树
            grand.parent.right = parent;
        } else { // grand是root节点
            root = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
    }

    /**
     * 统一处理的旋转代码（神奇）
     */
    protected void rotate(
            Node<K, V> r, // 子树的根节点
            Node<K, V> b, Node<K, V> c,
            Node<K, V> d,
            Node<K, V> e, Node<K, V> f) {
        // 让d成为这棵子树的根节点
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }

        //b-c
        b.right = c;
        if (c != null) {
            c.parent = b;
        }

        // e-f
        f.left = e;
        if (e != null) {
            e.parent = f;
        }

        // b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
    }


    /**
     * 下面是一些辅助方法
     */
    // 染色
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null)
            return node;
        node.color = color;
        return node;
    }

    // 将该节点染为红色
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    // 将该节点染为黑色
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    // 返回该节点的颜色
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    // 该节点是否为黑色
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    // 该节点是否为红色
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }


    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    /**
     * @return 返回值等于0，代表e1和e2相等；返回值大于0，代表e1大于e2；返回值小于于0，代表e1小于e2
     */
    private int compare(K e1, K e2) {
        if (comparator != null) { // 比较器不为空，则使用比较器
            return comparator.compare(e1, e2);
        }
        // 没有传入比较器，则要求元素本身必须实现Comparable接口
        return ((Comparable<K>) e1).compareTo(e2);
    }


    private V remove(Node<K, V> node) {
        if (node == null) return null;

        size--;

        V oldVal = node.value;

        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
//            node.element = s.element;
            node.key = s.key;
            node.value = s.value;
            // 删除后继节点
            // 这里是因为后面必然会删除node节点
            // 所以直接将后继节点赋给node,在后面将它删除
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node是度为1的节点
            // 核心：用子节点替代原节点的位置
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            afterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            root = null;

            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            afterRemove(node);
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) { // 是左子树
                node.parent.left = null;
            } else { // node == node.parent.right // 是右子树
                node.parent.right = null;
            }

            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            afterRemove(node);
        }

        return oldVal;
    }

    protected void afterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        // 删除的是根节点
        if (parent == null)
            return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    protected Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<K, V> p = node.right;
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

    private static class Node<K, V> {
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
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

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }

}
