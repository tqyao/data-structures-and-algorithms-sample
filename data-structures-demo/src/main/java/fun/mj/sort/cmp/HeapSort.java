/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.sort.cmp;

import fun.mj.sort.Sort;
import fun.mj.util.printer.BinaryTreeInfo;

/**
 * 堆排序
 * 最好，最坏，平均时间复杂度：O(nlogn)
 * 空间复杂度O(1)，属于不稳定排序
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/08 08:35
 */
public class HeapSort<T extends Comparable<T>> extends Sort<T> implements BinaryTreeInfo {

    private int headSize;

    @Override
    protected void sort() {
        headSize = array.length;

        // 1. 原地批量建堆（大顶堆）
        // 自下而上的下滤 TC = O(n)
        for (int i = (headSize >> 1) - 1; i >= 0; i--) {
            sifDown(i);
        }

        // 循环条件 heapSize > 1。对数组 n - 1 个元素排序
        // 2.1. 交换堆顶与末尾元素
        // 2.2. heapSize --
        // 2.3. 对 index = 0 元素下滤
        while (headSize > 1) {  //n
            swap(0, --headSize);
            sifDown(0);//logn
        }
        // TC = O(n) + nlogn  = nlogn
    }


    /**
     * 下滤
     * TC = logn
     *
     * @param index
     * @author tqyao
     * @create 2023/10/8 08:55
     */
    private void sifDown(int index) {
        int haft = headSize >> 1;  // 非叶子节点个数
        T curNode = array[index];
        // 索引小于最后一个非叶子节点索引
        while (index < haft) {
            // 左孩子节点索引
            int greaterIdx = (index << 1) + 1;
            T greaterChild = array[greaterIdx];

            // 选择出左右孩子较大的一个索引和值
            if (greaterIdx + 1 < headSize && cmp(greaterChild, array[greaterIdx + 1]) < 0) {
                // 右孩子存在 && 左孩子小于右孩子
                greaterChild = array[++greaterIdx];
            }

            // 父节大于孩子节点
            if (cmp(curNode, greaterChild) > 0) {
                break;
            }

            // 父节点小于孩子节点，孩子节点上来覆盖父节点
            array[index] = greaterChild;
            // 迭代。继续比较较大孩子节点的子节点
            index = greaterIdx;
        }

        array[index] = curNode;
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int) node << 1) + 1;
        return index >= headSize ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = ((int) node << 1) + 2;
        return index >= headSize ? null : index;
    }

    @Override
    public Object string(Object node) {
        return array[(int) node];
    }


}
