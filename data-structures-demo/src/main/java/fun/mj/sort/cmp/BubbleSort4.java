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
public class BubbleSort4<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            for (int begin = 1; begin <= end; begin++) {
                if (cmp(begin, begin - 1) < 0) {
                    swap(begin, begin - 1);
                    sortedIndex = begin;
                }
            }
            end = sortedIndex;
        }
    }


}
