/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.queue;

import fun.mj.heap.BinaryHeap;

import java.util.Comparator;

/**
 * 优先队列
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/08 19:30
 */
public class PriorityQueue<E> {
    private BinaryHeap<E> heap;


    public PriorityQueue() {
        this(null);
    }

    public PriorityQueue(Comparator<E> comparator) {
        heap = new BinaryHeap<>(comparator);
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void clear() {
        heap.clear();
    }

    public void enQueue(E element) {
        heap.add(element); //入队
    }

    public E deQueue() {
        return heap.remove(); //让优先级最高的元素出队
    }

    public E front() {
        return heap.get(); //获取堆顶元素
    }
}
