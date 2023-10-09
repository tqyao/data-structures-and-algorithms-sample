/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.trie;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/09 10:04
 */
public interface Trie <V> {
    int size();
    boolean isEmpty();
    void clear();

    V get(String key);
    boolean contains(String str);
    /**
     *
     * @author tqyao
     * @create 2023/10/9 10:41
     * @param str
     * @param value
     * @return V 旧节点值
     */
    V add(String str,V value);
    V remove(String str);
    boolean startsWith(String prefix);
}

