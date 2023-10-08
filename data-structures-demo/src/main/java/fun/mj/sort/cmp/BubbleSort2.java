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
public class BubbleSort2<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        boolean flag = false;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (cmp(j, j + 1) > 0) {
                    swap(j, j + 1);
                    flag = true;
                }
            }
            if (!flag) return;
        }
    }

}
