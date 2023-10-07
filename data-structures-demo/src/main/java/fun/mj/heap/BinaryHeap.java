/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.heap;

import fun.mj.util.printer.BinaryTreeInfo;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 二叉堆（最大堆）
 * 根节点>=孩子节点
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/06 18:29
 */
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {


    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;


    public BinaryHeap() {
        this(null, null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        this(null, comparator);
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        super(comparator);
        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            // 深拷贝
            size = elements.length;
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];
            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }
            // 批量建堆
            heapify200();
//            heapify100();
        }
    }

    /**
     * 批量建堆
     * - 自上而下的上滤
     * TC = (n-1) * log(n) = nlog(n)
     *
     * @author tqyao
     * @create 2023/10/7 19:39
     */
    private void heapify100() {
        for (int i = 1; i < size; i++) {
            siftUp(i);
        }
    }

    /**
     * 批量建堆
     * - 自下而上的下滤
     * TC = O(n) 整理规律公式推导
     *
     * @author tqyao
     * @create 2023/10/7 19:39
     */
    private void heapify200() {
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            sifDown(i);
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
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        E oldTop = elements[0];
        int lastIdx = --size;
        elements[0] = elements[lastIdx];
        elements[lastIdx] = null;

        sifDown(0);

        return oldTop;
    }

    /**
     * 让 index 上的元素向下滤
     * TC = logn
     *
     * @param index
     * @author tqyao
     * @create 2023/10/7 10:46
     */
    private void sifDown(int index) {
        E element = elements[index];
        int half = size >> 1;
        // 第一个叶子节点的索引 == 非叶子节点的数量
        // index < 第一个叶子节点的索引
        // 必须保证index位置是非叶子节点
        while (index < half) {
            // index的节点有2种情况
            // 1.只有左子节点
            // 2.同时有左右子节点

            // 默认为左子节点跟它进行比较
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];

            // 右子节点
            int rightIndex = childIndex + 1;

            // 选出左右子节点最大的那个
            if (rightIndex < size && compare(elements[rightIndex], child) > 0) {
                child = elements[childIndex = rightIndex];
            }

            if (compare(element, child) >= 0) break;

            // 将子节点存放到index位置
            elements[index] = child;
            // 重新设置index
            index = childIndex;
        }
        elements[index] = element;
    }


    /**
     * 让 index 上的元素向下滤
     * TC = logn
     *
     * @param index
     * @author tqyao
     * @create 2023/10/7 09:36
     */
    private void sifDown200(int index) {
        emptyCheck();

        // 堆顶元素值
        E topElement = elements[index];
        int rightChildIdx;
        while ((rightChildIdx = (index << 1) + 2) < size) { // 存在右孩子  index * 2 + 1

            E leftChildNode = elements[rightChildIdx - 1];
            E rightChildNode = elements[rightChildIdx];

            // 左右孩子中找出较大孩子索引
            int greaterNodeIdx = compare(leftChildNode, rightChildNode) > 0
                    ? rightChildIdx - 1 : rightChildIdx;

            // 父节点与左右较大值比较
            if (compare(topElement, elements[greaterNodeIdx]) >= 0) {
                //父节点值>=左右孩子较大值，堆性质保持，无需覆盖，退出循环
                break;
            }

            // 父节点<左右孩子较大值
            // 较大值覆盖父元素索引位置值，维护大顶堆性质
            elements[index] = elements[greaterNodeIdx];

            // 迭代。用左右子节点较大索引覆盖父索引
            index = greaterNodeIdx;
        }

        elements[index] = topElement;

    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E root = null;
        if (size == 0) {
            // 如果堆中没有元素直接添加
            elements[size++] = element;
        } else {
            //替换对顶元素，下滤
            root = elements[0];
            elements[0] = element;
            sifDown(0);
        }

        return root;
    }

    /**
     * 让index位置的元素上滤
     * index =0 为根节点
     *
     * @param index
     */
    private void siftUp(int index) {
  /*      E addElement = elements[index];
        while (index > 0) { // 循环终止条件 1. 当节点没有父节点时
            int parentIdx = (index - 1) >> 1;
            E parentElement = elements[parentIdx];
            if (compare(addElement, parentElement) <= 0) {// 加入元素小于父元素，无需交换
                return;
            }
            // 上滤。交换父节点与子节点元素，维护小顶堆性质
            E temp = elements[index];
            elements[index] = parentElement;
            elements[parentIdx] = temp;

            // 迭代。
            index = parentIdx;
        }*/

        E addElement = elements[index];
        while (index > 0) { // 循环终止条件 1. 当节点没有父节点时
            // 父节点索引 = (子节点索引-1) / 2
            int parentIdx = (index - 1) >> 1;
            E parentElement = elements[parentIdx];
            if (compare(addElement, parentElement) <= 0) {// 加入元素小于父元素，无需交换
                break;
            }
            // 上滤。让较小的父元素"下来"，维护小顶堆性质
            elements[index] = parentElement;

            // 迭代。
            index = parentIdx;
        }
        // index 是最终新加入元素的位置
        elements[index] = addElement;
    }


    /**
     * 确保容量足够
     * 容量不足进行扩容
     *
     * @param capacity 需求容量大小
     * @author tqyao
     * @create 2023/10/6 21:02
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        // 旧数组容量足够，无需扩容
        if (oldCapacity >= capacity) return;

        // 扩容到旧数组 1.5 倍
        int newCapacity = oldCapacity + (oldCapacity >> 1); // oldCapacity + oldCapacity/2
        E[] newElements = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }


    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }
    }


    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int) node << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int) node];
    }
}
