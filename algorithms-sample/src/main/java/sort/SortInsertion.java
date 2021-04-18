/**
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package sort;

import java.util.Arrays;

/**
 * .<br>
 *
 * @author tanqinyao<br>
 * @version 1.0.0 <br>
 * @date Create in 2021/4/18 13:30 <br>
 */
public class SortInsertion {

    public static void main(String[] args) {
        int[] array = {3, 4, 1, 6, 2};
        int[] insertion = insertion4(array);
        System.out.println(Arrays.toString(insertion));
        System.out.println();

    }

    public static int[] insertion(int[] array) {
        boolean flag = true;
        int[] sourceArray = Arrays.copyOf(array, array.length);
        int[] sortArray = new int[sourceArray.length];
        for (int i = 0; i < sourceArray.length; i++) {
            for (int j = 0; j < sortArray.length; j++) {
                if (sourceArray[i] <= sortArray[j]) {
                    flag = false;
                    System.arraycopy(sortArray, j, sortArray, j + 1, sortArray.length - (j + 1));
                    sortArray[j] = sourceArray[i];
                    break;
                }
            }
            if (flag) {
                sortArray[i] = sourceArray[i];
            }
            flag = true;
        }
        return sortArray;
    }

//    public static int[] insertion2(int[] array) {
//
//        boolean flag = true;
//        int[] sourceArray = Arrays.copyOf(array, array.length);
//        int[] sortArray = new int[sourceArray.length];
//        for (int i = 0; i < sourceArray.length; i++) {
//            for (int j = sortArray.length - 1; j > 0; j--) {
//                if (sourceArray[i] >= sortArray[j]) {
//                    flag = false;
//                    System.arraycopy(sortArray, j + 1, sortArray, j + 2, sortArray.length - (j + 2));
//                    sortArray[j + 1] = sourceArray[i];
//                    break;
//                }
//            }
//            if (flag) {
//                sortArray[i] = sourceArray[i];
//            }
//            flag = true;
//        }
//        return sortArray;
//    }
//
//    public static int[] insertion3(int[] array) {
//        int[] sourceArray = Arrays.copyOf(array, array.length);
//        int[] sortArray = new int[sourceArray.length];
//
//        int index, val;
//        for (int i = 0; i < sourceArray.length - 1; i++) {
//            index = -1;
//            val = sortArray[i]; //记录原始数组值
//            for (int j = 0; j < i; j--) {
//                if (sourceArray[i] <= sortArray[j]) {
//                    index = j;  // 记录排序待插入的索引
//                }
//            }
//
//            if (index != -1) {
//                System.arraycopy(sortArray, index, sortArray, index + 1, i + 1);
//                sortArray[index] = val;
//            } else {
//                sortArray[i] = val;
//            }
//        }
//        return null;
//    }

    public static int[] insertion4(int[] array) {
        int[] sourceArray = Arrays.copyOf(array, array.length);
//        int[] sortArray = new int[sourceArray.length];

        int temp;
        for (int i = 1; i < sourceArray.length - 1; i++) {
//            temp = sourceArray[i];
            for (int j = i; j > 0; j++) {
                if (sourceArray[i] < sourceArray[j - 1]) {
                    temp = sourceArray[j];
                    sourceArray[j] = sourceArray[j - 1];
                    sourceArray[j - 1] = temp;
                }
            }
        }
        return sourceArray;
    }

    /**
     * 4.的小优化
     * @param array
     * @return
     */
    public static int[] insertion5(int[] array) {
        int[] sourceArray = Arrays.copyOf(array, array.length);

        int temp, j;
        for (int i = 1; i < sourceArray.length ; i++) {
            temp = sourceArray[i]; //待插入元素
            j = i;
            while (j > 0 && temp < sourceArray[j - 1]) {
                sourceArray[j] = sourceArray[j - 1];
                j--;
            }
            if (j != i) {
                sourceArray[j] = temp;
            }
        }
        return sourceArray;
    }
}
