/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.sort.cmp;

import fun.mj.sort.Sort;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/08 08:35
 */
public class BubbleSort1<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
//        sort1();
//        sort2();
        sort3();
    }

    // 从后往前冒泡（每轮确定一个最小）
    private void sort3() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = array.length - 1; j > i; j--) {
                if (cmp(j, j - 1) < 0) {
                    swap(j, j - 1);
                }
            }
        }
    }

    // 从前往后冒泡（每轮确定一个最大）
    private void sort2() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 1; j < array.length - i; j++) {
                if (cmp(j - 1, j) > 0) {
                    swap(j - 1, j);
                }
            }
        }
    }

    // 从前往后冒泡
    private void sort1() {
        // n - 1 轮
        // 每轮比较 n - i 次
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (cmp(j, j + 1) > 0) {
                    swap(j, j + 1);
                }
            }
        }
    }
}
