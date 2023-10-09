/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.sort;

import fun.mj.util.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/08 18:05
 */
public class MyHeap<E> extends PriorityQueue<E> implements BinaryTreeInfo {


    public MyHeap(Comparator<? super E> comparator) {
        super(comparator);
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index >= size() ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int) node << 1) + 2;
        return index >= size() ? null : index;
    }

    @Override
    public Object string(Object node) {
//        return queue[(int) node];
        return this.toArray()[(int) node];
    }
}
