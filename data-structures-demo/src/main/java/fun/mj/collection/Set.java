/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.collection;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/03 17:10
 */
public interface Set<E> {
    int size();

    boolean isEmpty();

    void clear();

    boolean contains(E element);

    void add(E element);

    void remove(E element);

    void traversal(Visitor<E> visitor); //遍历集合

    public static abstract class Visitor<E> {
        boolean stop;

        public abstract boolean visit(E element);
    }
}

