/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.trie;

import jdk.nashorn.internal.ir.IfNode;

import java.util.HashMap;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/09 10:04
 */
public class TrieImpl<V> implements Trie<V> {

    private int size;
    private Node<V> root;

    private static class Node<V> {
        HashMap<Character, Node<V>> children;

        // 为删除做准备
        Node<V> parent;

        // 为删除做准备
        Character character;

        V value;

        // 是否是单词标记
        boolean word;

        public Node(Node<V> parent) {
            this.parent = parent;
        }
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
        size = 0;
        root = null;
    }

    @Override
    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.word ? node.value : null;
    }

    @Override
    public boolean contains(String str) {
        Node<V> node = node(str);
        return node != null && node.word;
    }

    @Override
    public V add(String key, V value) {

        if (key == null || key.length() == 0) return null;

        if (root == null) {
            root = new Node<>(null);
        }
        int len = key.length();
        Node<V> curNode = root;
        Node<V> childNode;
        for (int i = 0; i < len; i++) {
            char ch = key.charAt(i);
            // 1.字符对应节点不存在。

/*            if (curNode.children == null) {
                // 当前节点不存在孩子节点，创建孩子容器
                curNode.children = new HashMap<>();
            }

            childNode = curNode.children.get(ch);
            if (childNode == null) {
                // 当前节点不存在 ch 字符对应的节点，创建节点并加入到当前节点的孩子容器中
                childNode = new Node<>();
                childNode.character = ch;
                curNode.children.put(ch, childNode);
            }*/

            boolean emptyChild = curNode.children == null;
            childNode = emptyChild ? null : curNode.children.get(ch);
            if (childNode == null) {
                childNode = new Node<>(curNode);
                childNode.character = ch;
                curNode.children = emptyChild ? new HashMap<>() : curNode.children;
                curNode.children.put(ch, childNode);
            }

            // 2. 字符对应节点存在。

            // 迭代。孩子节点复制给当前节点
            curNode = childNode;
        }


        // 原本树中就存在将加入单词
        if (curNode.word) {
            V oldValue = curNode.value;
            curNode.value = value;
            return oldValue;
        }

        // 新增一个单词
        size++;
        curNode.word = true;
        curNode.value = value;

        return null;
    }

    //    @Override
    public V remove(String key) {
        // 找到单词结尾节点
        Node<V> node = node(key);

        // 单词不存在
        if (node == null || !node.word) {
            return null;
        }

        size--;
        V oldValue = node.value;

        // 单词尾结点包含子节点 key=dog doggy
        if (node.children != null && !node.children.isEmpty()) {
            // 设置单词标记为 flase，前缀树单词书-1
            node.word = false;
            return oldValue;
        }

        // 单词尾节点不包含子节点
        Node<V> parent;
        while ((parent = node.parent) != null) { // 延父节点链一直删除直到父节点是空（直到根节点）
            // 从父节点中移除子节点
            parent.children.remove(node.character);
            // 若父节点是单词或存在其他孩子，即带删除单词是其他单词前缀。删除结束
            if (parent.word || !parent.children.isEmpty()) break;
            node = parent;
        }

        return oldValue;
    }


    @Override
    public boolean startsWith(String prefix) {
        return node(prefix) != null;
    }

    private Node<V> node(String key) {
        if (key == null || key.length() == 0) return null;

        Node<V> point = root;
        for (int i = 0; i < key.length(); i++) {
            if (point == null || point.children == null || point.children.isEmpty()) return null;
            char ch = key.charAt(i);
            point = point.children.get(ch);
            if (point == null) return null;
        }
        return point;
    }
}
