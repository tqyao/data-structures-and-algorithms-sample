/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.heap;

import java.util.Comparator;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/07 08:55
 */
public abstract class AbstractHeap<E> implements Heap<E> {

    protected int size;
    private Comparator<E> comparator;

    public AbstractHeap(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    protected int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2)
                : ((Comparable) e1).compareTo(e2);
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
