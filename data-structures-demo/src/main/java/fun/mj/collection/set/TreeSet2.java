/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.collection.set;

import fun.mj.collection.Map;
import fun.mj.collection.Set;
import fun.mj.collection.map.TreeMap;

/**
 * 利用 TreeMap 实现 set
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/04 12:43
 */
public class TreeSet2<E> implements Set<E> {

    private TreeMap<E, Object> treeMap = new TreeMap<>();

    @Override
    public int size() {
        return treeMap.size();
    }

    @Override
    public boolean isEmpty() {
        return treeMap.isEmpty();
    }

    @Override
    public void clear() {
        treeMap.clear();
    }

    @Override
    public boolean contains(E element) {
        return treeMap.containsKey(element);
    }

    @Override
    public void add(E element) {
        treeMap.put(element, null);
    }

    @Override
    public void remove(E element) {
        treeMap.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        treeMap.traversal(
                new Map.Visitor<E, Object>() {
                    @Override
                    public boolean visit(E key, Object value) {
                        return visitor.visit(key);
                    }
                }
        );
    }
}
