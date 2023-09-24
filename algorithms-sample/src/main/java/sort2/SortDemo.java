/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package sort2;

import com.sun.org.apache.bcel.internal.generic.Select;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/09/24 11:39
 */
public class SortDemo {


    @Test
    public void testSelectSort() {
        int[] ints = Stream.of(4, 5, 3, 1, 2).mapToInt(i -> i).toArray();
        getPrintln(ints);
        selectSort(ints);
        getPrintln(ints);
    }

    public static void selectSort(int[] arr) {
        int idx = arr.length - 1;
        for (int i = idx; i > 0; i--) {
            int maxIdx = i;
            for (int j = 0; j < i; j++) {
                if (arr[maxIdx] < arr[j]) {
                    maxIdx = j;
                }
            }
            if (maxIdx != i) {
                int temp = arr[maxIdx];
                arr[maxIdx] = arr[i];
                arr[i] = temp;
            }
        }
    }

    @Test
    public void testSort() {
        int[] ints = Stream.of(4, 5, 3, 1, 2).mapToInt(i -> i).toArray();
//
//        System.out.println(Arrays.toString(ints));
//        buffSort100(ints);
//        System.out.println(Arrays.toString(ints));


        ints = Stream.of(5, 4, 3, 2, 1).mapToInt(i -> i).toArray();
        getPrintln(ints);
        buffSort101(ints);
        getPrintln(ints);
    }

    private static void getPrintln(int[] ints) {
        System.out.println(Arrays.toString(ints));
    }


    // [4,5,6,3,1,2]
    public static void buffSort100(int[] arr) {
        boolean flag = false;
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            if (!flag) return;
            flag = false;
        }
    }

    // [3,2,4,5,6]
    public static void buffSort101(int[] arr) {
        int j = arr.length - 1;
        int x;// 右边界
        while (true) {
            x = 0;
            for (int i = 0; i < j; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    x = i;
                }
            }
            j = x;
            if (0 == j) return;
//            if (0 == --j) return;//3,2,1,0 各与 0 比较判断一次，循环执行 4 次
//            if (0 == j--) return;//4,3,2,1,0 各与 0 比较判断一次，循环执行 5 次
        }
    }

//    public static void buffSort200(int[] arr) {
//
//        for (int i = arr.length - 1; i > 0; i--) {//[1,length]
//            int maxIdx = -1;
//            for (int j = 0; j < i - 1; j++) {
//                if (arr[j] < arr[j + 1]) {
//                    maxIdx = j + 1;
//                }
//            }
//            if (maxIdx != -1) {
//                int temp = arr[i];
//                arr[i] = arr[maxIdx];
//                arr[maxIdx] = temp;
//            }
//        }
//    }
}
