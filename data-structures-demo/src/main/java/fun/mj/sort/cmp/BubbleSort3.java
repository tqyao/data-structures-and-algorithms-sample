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
public class BubbleSort3<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        boolean flag = false;
        for (int end = 0; end < array.length - 1; end++) {
            int lastIdx = array.length - end - 1;
            for (int j = 0; j < lastIdx; j++) {
                if (cmp(j, j + 1) > 0) {
                    swap(j, j + 1);
                    flag = true;
                    lastIdx = j;
                }
            }
            if (!flag) return;
        }
    }

}
