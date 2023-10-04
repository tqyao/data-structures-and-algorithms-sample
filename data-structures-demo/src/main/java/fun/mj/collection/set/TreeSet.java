/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.collection.set;

import fun.mj.collection.Set;
import fun.mj.tree.BinaryTree;
import fun.mj.tree.RBTree;

/**
 * 红黑树实现集合
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/03 20:08
 */
public class TreeSet<E> implements Set<E> {

    private RBTree<E> rbTree = new RBTree<>();

    @Override
    public int size() {
        return rbTree.size();
    }

    @Override
    public boolean isEmpty() {
        return rbTree.isEmpty();
    }

    @Override
    public void clear() {
        rbTree.clear();
    }

    @Override
    public boolean contains(E element) {
        return rbTree.contains(element);
    }

    @Override
    public void add(E element) {
        rbTree.add(element);
    }

    @Override
    public void remove(E element) {
        rbTree.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        rbTree.inorder(new BinaryTree.Visitor<E>() {
            @Override
            public boolean visit(E element) {
                return visitor.visit(element);
            }
        });
    }
}
