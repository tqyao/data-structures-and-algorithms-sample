/**
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package sort;

import java.util.Arrays;
import java.util.List;

/**
 * .<br>
 *
 * @author tanqinyao<br>
 * @version 1.0.0 <br>
 * @date Create in 2021/4/17 14:53 <br>
 */
public class SortBubble {
    public static void main(String[] args) {
        int[] array = {3, 4, 5, 1};
        int[] bubble = bubble(array);
        System.out.println(Arrays.toString(bubble));
        double a = (double) Math.random();
        int b = (int) a;
        System.out.println(a);
        System.out.println(b);
    }


    /**
     * 前向后遍历，两两比较，比较大的往后移动
     * 平均时间复杂度：O(n2)
     * @param array
     * @return
     */
    public static int[] bubble(int[] array) {
        int[] newArray = Arrays.copyOf(array, array.length);
        int temp;
        for (int i = array.length - 1; i > 0; i--) {
            //是否发生交换
            boolean flag = false;
            for (int j = 0; j < i; j++) {
                if (flag) {
                    break;
                }
                if (newArray[j] > newArray[j + 1]) {
                    temp = newArray[j];
                    newArray[j] = newArray[j + 1];
                    newArray[j + 1] = temp;
                    flag = true;
                }
            }
        }
        return newArray;
    }
}
