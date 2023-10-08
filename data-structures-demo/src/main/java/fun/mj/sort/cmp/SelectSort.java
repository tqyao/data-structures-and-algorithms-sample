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
public class SelectSort<T extends Comparable<T>> extends Sort<T> {

    @Override
    protected void sort() {
        for (int i = 0; i < array.length - 1; i++) {
            int maxIdx = array.length - i - 1;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (cmp(j, maxIdx) > 0) {
                    maxIdx = j;
                }
            }
            if (maxIdx != array.length - i - 1) {
                swap(maxIdx, array.length - i - 1);
            }
        }


//        for (int i = 0; i < array.length - 1; i++) {
//            int minIdx = i;
//            for (int j = i + 1; j < array.length; j++) {
//                if (cmp(j, minIdx) < 0) {
//                    minIdx = j;
//                }
//            }
//            if (minIdx != i) {
//                swap(minIdx, i);
//            }
//        }
    }

}
