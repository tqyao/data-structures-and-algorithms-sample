/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.collection.map;

import java.util.Objects;

/**
 * 有序 hashMap
 * 所谓有序是指遍历时按输入顺序输出
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/06 08:19
 */
public class LinkedHashMap<K, V> extends HashMap<K, V> {

    /**
     * 插入的链表首节点
     */
    LinkedNode<K, V> first;
    /**
     * 插入链表的为节点
     */
    LinkedNode<K, V> last;


    @Override
    public void clear() {
        super.clear();
        first = last = null;
    }

    @Override
    public V remove(K key) {
        return super.remove(key);
    }


    @Override
    public boolean containsValue(V value) {
        if (first == null) return false;
        LinkedNode<K, V> point = first;
        do {
            if (Objects.equals(point.value, value)) {
                return true;
            }
            point = point.next;
        } while (point != null);
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null || first == null) return;
        LinkedNode<K, V> point = first;
        while (point != null && !visitor.visit(point.key, point.value)) {
            point = point.next;
        }
    }

    @Override
    protected Node<K, V> createNode(K key, V val, Node<K, V> parent) {
        LinkedNode<K, V> newNode = new LinkedNode<>(key, val, parent);

        // 创建节点后，添加节点前后指针（双向列表指针），维护红黑树插入时顺序
        if (first == null) {    // 创建的是链表中的第一个元素
            first = last = newNode;
        } else {    // 非第一个元素
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        return newNode;
    }

    /**
     * 1. 更换双向链表 node 与 前驱/后继 节点的链接位置
     * 2. 修改删除双向链表节点前驱后继指针指向 （双向链表删除节点）
     * <p>
     * - 当 willNode = removedNode 时删除的是度为 0， 1 的节点
     * - 当 willNode != removedNode 时删除的是度为 2 的节点。removedNode 是 willNode 的后继节点
     *
     * @param willNode    要删除的节点。要删除的节点度为 2 时，实际上后继节点才会真真被删除（removedNode才要被删除）
     * @param removedNode 真真要删除的节点（后继节点）
     */
    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
        // 1. 删除度为 2 的节点时，需要交换双向链表中前驱后继节点指针位置指向，（实际红黑树中的位置没变
        // ，在 remove（）时，才可能改变红黑树中节点位置）
        // ，以保证删除后继节点后，节点的顺序保持插入顺序不变
        LinkedNode<K, V> node1 = (LinkedNode<K, V>) willNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>) removedNode;
        if (willNode != removedNode) {
            // 交换 prev 指针
            LinkedNode<K, V> tmp = node1.prev;
            node1.prev = node2.prev;
            node2.prev = tmp;
            if (node1.prev != null) {
                node1.prev.next = node1;
            } else {
                first = node1;
            }
            if (node2.prev != null) {
                node2.prev.next = node1;
            } else {
                first = node2;
            }

            // 交换 next 指针
            tmp = node1.next;
            node1.next = node2.next;
            node2.next = tmp;
            if (node1.next != null) {
                node1.next.prev = node1;
            } else {
                last = node1;
            }
            if (node2.next != null) {
                node2.next.prev = node2;
            } else {
                last = node2;
            }
        }

        // 2. 修改删除双向链表节点前驱后继指针指向 （双向链表删除节点）
        LinkedNode<K, V> prev = node2.prev;
        LinkedNode<K, V> next = node2.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }

    }

    /**
     * 每个桶中的每个节点维护指向前后插入节点的指针，遍历时根据指针遍历
     *
     * @author tqyao
     * @create 2023/10/6 08:23
     */
    static class LinkedNode<K, V> extends Node<K, V> {

        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;

        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
