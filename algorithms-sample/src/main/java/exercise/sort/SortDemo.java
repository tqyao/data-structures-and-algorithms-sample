/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package exercise.sort;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/07 20:58
 */
public class SortDemo {

    public static void selectSort(int[] arrs) {
        int len = arrs.length;
        for (int i = len - 1; i > 0; i--) {
            int maxIdx = 0;
            for (int j = 0; j <= i; j++) {
                if (arrs[maxIdx] < arrs[j]) {
                    maxIdx = j;
                }
            }
            int temp = arrs[maxIdx];
            arrs[maxIdx] = arrs[i];
            arrs[i] = temp;
        }
    }

    public static void selectSort2(int[] arrs) {
        int len = arrs.length;
        for (int i = 0; i < len - 1; i++) {
            int maxIdx = len - i - 1;
            for (int j = 0; j < len - i; j++) {
                if (arrs[maxIdx] < arrs[j]) {
                    maxIdx = j;
                }
            }
            if (maxIdx != len - i - 1) {
                int tmp = arrs[maxIdx];
                arrs[maxIdx] = arrs[len - i - 1];
                arrs[len - i - 1] = tmp;
            }
        }
    }
}
