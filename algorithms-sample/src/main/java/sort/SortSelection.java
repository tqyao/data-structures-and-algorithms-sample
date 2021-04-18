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
 * @date Create in 2021/4/17 16:20 <br>
 */
public class SortSelection {

    public static void main(String[] args) {
        int[] array = {3, 4, 5, 1, 6, 2};
        int[] bubble = selection(array);
        System.out.println(Arrays.toString(bubble));
//        int[] array = {3, 4, 0, 0, 0};
//        System.arraycopy(array, 0, array, 0 + 1, array.length - (1));
//        System.out.println(Arrays.toString(array));
    }

    /**
     * @return
     */
    public static int[] selection(int[] array) {
        int[] newArray = Arrays.copyOf(array, array.length);
        int minIndex;
        int temp;
        for (int i = 0; i < newArray.length - 1; i++) {
            minIndex = i;
            for (int j = i; j < newArray.length - 1; j++) {
                if (newArray[minIndex] > newArray[j + 1]) {
                    minIndex = j + 1;
                }
            }

            if (minIndex != i) {
                temp = newArray[minIndex];
                newArray[minIndex] = newArray[i];
                newArray[i] = temp;
            }
        }
        return newArray;
    }
}
