/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.collection.set;

import fun.mj.collection.Set;
import fun.mj.collection.list.LinkedList;
import fun.mj.collection.list.List;

/**
 * 链表实现集合
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/03 17:25
 */
public class ListSet<E> implements Set<E> {
    private LinkedList<E> linkedList = new LinkedList<>();

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public void clear() {
        linkedList.clear();
    }

    @Override
    public boolean contains(E element) {
        return linkedList.contains(element);
    }

    @Override
    public void add(E element) {
        int idx = linkedList.indexOf(element);
        if (idx == List.ELEMENT_NOT_FOUND) {
            // 链表中不包含元素，加入链表
            linkedList.add(element);
        } else {
            // 链表中存在则覆盖
            linkedList.set(idx, element);
        }
    }

    @Override
    public void remove(E element) {
        linkedList.remove(linkedList.indexOf(element));

    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) return;
        for (int i = 0; i < linkedList.size(); i++) {
            visitor.visit(linkedList.get(i));
        }
    }
}
