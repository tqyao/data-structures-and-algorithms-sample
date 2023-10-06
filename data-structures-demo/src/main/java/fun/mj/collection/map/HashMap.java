/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.collection.map;

import fun.mj.collection.Map;
import fun.mj.util.printer.BinaryTreeInfo;
import fun.mj.util.printer.BinaryTrees;

import java.util.*;

/**
 * hashMap
 * - 数组 table 存储节点数据
 * - 每个数组存储一颗红黑树节点
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/04 09:40
 */
public class HashMap<K, V> implements Map<K, V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;


    private Node<K, V> table[];

    // map 中所有节点个数
    private int size;

    // 数组默认容量 2^4
    private static final int DEFAULT_CAPACITY = 1 << 4;

    // 加载因子
    private static float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap() {
        this.table = new Node[DEFAULT_CAPACITY];
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
        if (size == 0) return;
        Arrays.fill(table, null);
    }

    @Override
    public V put(K key, V value) {
        resize();
        int idx = index(key);
        Node<K, V> root = table[idx];
        // 添加第一个节点
        if (root == null) {
            root = createNode(key, value, null);
            table[idx] = root;
            size++;

            // 新添加节点之后的处理
            // BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            fixAfterPut(root);
            return null;
        }

        // 添加的不是第一个节点
        // 找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;

        int k1Hash = hash(key);
        Node<K, V> result;
        boolean searched = false;
        int cmp = 0;
        do {
//            cmp = compare(key, node.key);
//            cmp = compareDeprecated(key, node.key, hash(key), node.hash); // 优化：减少 hash 计算

            int k2Hash = node.hash;

            // 比较hashcode大小
            if (k1Hash > k2Hash) {
                cmp = 1;
            } else if (k1Hash < k2Hash) {
                cmp = -1;
            } else if (Objects.equals(key, node.key)) { // hashcode 相等。判断 equal 是否相等
                cmp = 0;
            }
            // hashcode 相等，equal 不相等。判断是否同一类型且是否可比较
            else if (key != null && node.key != null
                    && key.getClass() == node.key.getClass()
                    && key instanceof Comparable
                    // 同一类型可比较。比较器比较大小。且如果用比较器比较值是相等的，但不能由此决定k1,k2是相等的
                    // ，需要扫描整个红黑树查找满足 hash相等 equal 相等的 key
                    && (cmp = ((Comparable) key).compareTo(node.key)) != 0) {

            } else if (searched) {// （非同一类型或不可比较 &&） 判断是否扫描过
                // 该桶红黑树已经扫描过一遍。则使用内存地址比较大小
                cmp = System.identityHashCode(key) - System.identityHashCode(node.key);
            }
            // 还未扫描一遍 && 非同一类型或不可比较。扫描右子树和左子树，查找 key 对应 node 是否存在
            else if ((node.right != null && (result = node(node.right, key)) != null)
                    || node.left != null && (result = node(node.left, key)) != null) {
                node = result;
                cmp = 0;
            } else {    // （node.right || node.left ）= null 意味着已经扫描过一遍整个红黑树，指定key不存在于该桶红黑树中
                // ，故比较内存地址大小决定该 key 位置
                searched = true; // 优化：当递归扫描过一次红黑树时，进入该分支，并设置标记
                cmp = System.identityHashCode(key) - System.identityHashCode(node.key);
            }

            // 记录上一个遍历节点指针
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
        Node<K, V> newNode = createNode(key, value, parent);

        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        // 新添加节点之后的处理
        // BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
        fixAfterPut(newNode);

        // 新节点添加返回空
        return null;
    }

    /**
     * 扩容
     *
     * @author tqyao
     * @create 2023/10/5 18:22
     */
    private void resize() {
        // 节点个数/数组长度 >= 加载因子，才进行扩容
        if (!(size / table.length >= DEFAULT_LOAD_FACTOR)) return;
        Node<K, V>[] oldTable = table;
        // 新数组扩容至原先的 2 倍
        table = new Node[table.length << 1];

        // 扩容后，桶中每棵红黑树的节点需要重新计算桶索引
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < oldTable.length; i++) {
            Node<K, V> root = oldTable[i];
            if (root == null) continue;
            queue.offer(root);
            while (!queue.isEmpty()) {
                Node<K, V> poll = queue.poll();
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
                // 加入新桶的节点需要先把孩子入队再加入到新桶
                moveNode(poll);
            }
        }
    }

    /**
     * 指定节点添加到新桶--扩容时调用
     *
     * @param newNode
     * @author tqyao
     * @create 2023/10/5 18:41
     */
    private void moveNode(Node<K, V> newNode) {
        // 加入到新桶时清空节点关系
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        K key = newNode.key;
        int idx = index(key);
        Node<K, V> root = table[idx];
        // 添加第一个节点
        if (root == null) {
            root = newNode;
            table[idx] = root;

            // 新添加节点之后的处理
            // BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            fixAfterPut(root);
            return;
        }

        // 添加的不是第一个节点
        // 找到父节点
        Node<K, V> parent;
        Node<K, V> node = root;

        int k1Hash = hash(key);
//        Node<K, V> result;
//        boolean searched = false;
        int cmp;
        do {
            int k2Hash = node.hash;

            // 比较hashcode大小
            if (k1Hash > k2Hash) {
                cmp = 1;
            } else if (k1Hash < k2Hash) {
                cmp = -1;
            }
            // 扩容时，原哈希表中不存在"相同"元素，因此可以删减下述相等性判断
            /*else if (Objects.equals(key, node.key)) { // hashcode 相等。判断 equal 是否相等
                cmp = 0;
            }*/
            // hashcode 相等，equal 不相等。判断是否同一类型且是否可比较
            else if (key != null && node.key != null
                    && key.getClass() == node.key.getClass()
                    && key instanceof Comparable
                    // 同一类型可比较。比较器比较大小。且如果用比较器比较值是相等的，但不能由此决定k1,k2是相等的
                    // ，需要扫描整个红黑树查找满足 hash相等 equal 相等的 key
                    && (cmp = ((Comparable) key).compareTo(node.key)) != 0) {


            }
            /*else if (searched) {// （非同一类型或不可比较 &&） 判断是否扫描过
                // 该桶红黑树已经扫描过一遍。则使用内存地址比较大小
                cmp = System.identityHashCode(key) - System.identityHashCode(node.key);
            }
            // 还未扫描一遍 && 非同一类型或不可比较。扫描右子树和左子树，查找 key 对应 node 是否存在
            else if ((node.right != null && (result = node(node.right, key)) != null)
                    || node.left != null && (result = node(node.left, key)) != null) {
                node = result;
                cmp = 0;
            } */
            else {
                cmp = System.identityHashCode(key) - System.identityHashCode(node.key);
            }

            // 记录上一个遍历节点指针
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
            /*else { // 相等
                // 相等时的操作按需求来定，这里选择了覆盖
                node.key = key;
                V oldVal = node.value;
                node.value = value;
                return oldVal;  // 覆盖返回旧节点值
            }*/
        } while (node != null);

        // 通过上面的while循环找到了要插入节点的父节点
        // 看看插入到父节点的哪个位置
//        Node<K, V> newNode = new Node<>(key, value, parent);

        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        // 新添加节点之后的处理
        // BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
        fixAfterPut(newNode);

    }


    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? null : node.value;
    }


    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        // 遍历每一个桶上的红黑树
        for (int i = 0; i < table.length; i++) {
            Node<K, V> kvNode = table[i];
            if (kvNode == null)
                continue;

            // 层序遍历
            queue.offer(kvNode);

            while (!queue.isEmpty()) {
                Node<K, V> poll = queue.poll();
                if (Objects.equals(poll.value, value)) {
                    return true;
                }

                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            Node<K, V> kvNode = table[i];
            if (kvNode == null)
                continue;

            // 层序遍历
            queue.offer(kvNode);

            while (!queue.isEmpty()) {
                Node<K, V> poll = queue.poll();
                if (visitor.visit(poll.key, poll.value)) {
                    return;
                }
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }
        }
    }


    protected void fixAfterPut(Node<K, V> node) {
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
            fixAfterPut(grand);
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

            /*
            下面是等价的。传入的三个节点都是处于同一棵红黑树，因此任意节点 index() 计算出的索引都是相同的
            table[index(grand)] ~ table[index(parent)] ~ table[index(child)] = parent
             */
            // root = parent;
            table[index(grand)] = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
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


    /**
     * 根据key 计算对应的索引（在桶数组中的位置）
     *
     * @param key
     * @return int
     * @author tqyao
     * @create 2023/10/4 15:53
     */
    private int index(K key) {
        return hash(key) & (table.length - 1);
    }

    /**
     * 根据 key 计算对应的索引（在桶数组中的位置）
     *
     * @param node
     * @return int
     * @author tqyao
     * @create 2023/10/4 16:09
     */
    private int index(Node<K, V> node) {
        int hash = node.hash;
        return hash & (table.length - 1);
    }

    private int hash(K key) {
        // 规定 null 值的 hash为 0
        if (key == null) return 0;
        int hash = key.hashCode();  // 使用用户重写的 hashcode
        return hash ^ (hash >>> 16); // 让高 16 与低 16 参与hash运算（混淆，分布均匀）
    }


    /**
     * bug
     * 比较 key 的大小
     *
     * @param key1
     * @param key2
     * @param key1Hash k1 的 hash
     * @param key2Hash k2 的 hash
     * @return int
     * 0 相等
     * >0 key1 大于 key 2
     * <0 key1 小于 key 2
     * @author tqyao
     * @create 2023/10/4 16:19
     */
    @Deprecated
    private int compareDeprecated(K key1, K key2, int key1Hash, int key2Hash) {
        // 1. hashcode 不相等
        if (key1Hash != key2Hash) return key1Hash - key2Hash;

        // 2. hashcode 相等，比较 equal
        // 2.1. hashcode 相等，equal 相等，两 key 相等，返回 0
        if (Objects.equals(key1, key2)) return 0;

        // 2.2. hashcode 相等，equal 不相等
        if (key1 != null && key2 != null) {
            String k1Clz = key1.getClass().toString();
            String k2Clz = key2.getClass().toString();
            // 类型不同，返回类型字符串比较值
            if (!k1Clz.equals(k2Clz)) return k1Clz.compareTo(k2Clz);

            //类型相同，且可比较，返回比较器值
            if (key1 instanceof Comparable) {
                return ((Comparable) key1).compareTo(key2);
            }
        }

        /*
        3.
        - 类型相同，但不可以比较
        - key1 为空 key2 不为空
        - key2 为空 key1 不为空

        比较地址值
         */

        return System.identityHashCode(key1) - System.identityHashCode(key2);

    }


    /**
     * 获取对应 k1 的 node
     *
     * @param k1
     * @return fun.mj.collection.map.HashMap.Node<K, V>
     * @author tqyao
     * @create 2023/10/4 17:09
     */
    private Node<K, V> node(K k1) {
        // k1 对应桶的红黑树根节点
        Node<K, V> root = table[index(k1)];
        return root == null ? null : node(root, k1);
    }

    /**
     * 递归-在以 root 为根节点的红黑树中查找 k1
     *
     * @param root 在此红黑树根节点查找
     * @param k1   需要查找的节点的 key
     * @return fun.mj.collection.map.HashMap.Node<K, V>
     * @author tqyao
     * @create 2023/10/4 21:31
     */
    private Node<K, V> node(Node<K, V> root, K k1) {
        int k1Hash = hash(k1);

        int cmp;
        Node<K, V> result;
        while (root != null) {
            K k2 = root.key;
            int k2Hash = root.hash;

            // 1. hashcode 不相等
            if (k1Hash > k2Hash) {
                root = root.right;
            } else if (k1Hash < k2Hash) {
                root = root.left;
            } else if (Objects.equals(k1, k2)) {// 2. hashcode 相等，equal 相等
                return root;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {// 3. hashcode 相等，equal 不相等，但类型相同且可比较
                root = cmp > 0 ? root.right : root.left;
            }
            // 4. hashcode 相等，equal 不相等，类型不同或不可比较
            // 根-右-左（深度优先）
            else if (root.right != null && (result = node(root.right, k1)) != null) {
                // 递归，在右子树查找k1
                return result;
            }
            // 优化：减少一次递归调用
            else {
                root = root.left;
            }
            /*else if (root.left != null && (result = node(root.left, k1)) != null) {
                // 递归，在左子树查找k1
                return result;
            } else {
                return null;
            }*/
        }

        return null;
    }


    private V remove(Node<K, V> node) {
        if (node == null) return null;

        Node<K, V> willNode = node;

        size--;

        V oldVal = node.value;

        if (node.hasTwoChildren()) { //aaa. 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
//            node.element = s.element;
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            // 删除后继节点
            // 这里是因为后面必然会删除node节点
            // 所以直接将后继节点赋给node,在后面将它删除
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;

        // 获取节点所在桶索引
        int index = index(node);

        if (replacement != null) { // node是度为1的节点
            // 核心：用子节点替代原节点的位置
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                table[index] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            fixAfterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            table[index] = null;

//            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
//            afterRemove(node);
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) { // 是左子树
                node.parent.left = null;
            } else { // node == node.parent.right // 是右子树
                node.parent.right = null;
            }

            // 删除节点之后的处理，BST中无需处理，为 AVL树 和 B树提供可覆盖的方法
            fixAfterRemove(node);
        }


        /*
         willnode = node 是待删除节点
         当代删除节点的度为 2 时，node = s，node 是后继节点
         */
        afterRemove(willNode, node);
        return oldVal;
    }

    protected void fixAfterRemove(Node<K, V> node) {
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
                    fixAfterRemove(parent);
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
                    fixAfterRemove(parent);
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

    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object string(Object node) {
                    return node;
                }

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }
            });
            System.out.println("---------------------------------------------------");
        }
    }

    /**
     * 删除后处理（主要是提供给 LinkedHashMap 处理双向列表指针指向问题）
     *
     * @param willNode
     * @param removedNode
     */
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {

    }

    /**
     * 创建节点（提供子类重写）
     *
     * @param key
     * @param val
     * @param parent
     * @return fun.mj.collection.map.HashMap.Node<K, V>
     * @author tqyao
     * @create 2023/10/6 08:28
     */
    protected Node<K, V> createNode(K key, V val, Node<K, V> parent) {
        return new Node<K, V>(key, val, parent);
    }


    public static class Node<K, V> {

        int hash;
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16);
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

        @Override
        public String toString() {
            return "node_" + key + "_" + value;
        }
    }

}
