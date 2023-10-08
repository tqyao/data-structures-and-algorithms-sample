/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.sort;

import fun.mj.sort.cmp.*;
import fun.mj.util.ArrayUtil;
import fun.mj.util.Asserts;
import fun.mj.util.Integers;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/08 10:13
 */
public class Main {

    @Test
    public void testBubble1() {

        Integer[] array = new Integer[]{13, 36, 22, 85, 28, 65, 23, 83, 52, 19};
        ArrayUtil.print(array);

        Sort<Integer> bubbleSort1 = new BubbleSort1<>();
        Integer[] newArray = Integers.copy(array);
        bubbleSort1.sort(newArray);

//        Asserts.test(Integers.isAscOrder(newArray));
        ArrayUtil.print(newArray);
    }

    @Test
    public void testBubble2() {

//        Integer[]array = new Integer[]{13, 36, 22, 85, 28, 65, 23, 83, 52, 19};
//        Integer[]array = new Integer[]{1,2,3,4,5};
//        Integer[]array = new Integer[]{5,4,3,2,1};
        Integer[] array = new Integer[]{1, 4, 3, 2, 5};
        ArrayUtil.print(array);

        Sort<Integer> bubbleSort1 = new BubbleSort2<>();
        Integer[] newArray = Integers.copy(array);
        bubbleSort1.sort(newArray);

//        Asserts.test(Integers.isAscOrder(newArray));
        ArrayUtil.print(newArray);
    }

    @Test
    public void testBubble3() {

//        Integer[]array = new Integer[]{13, 36, 22, 85, 28, 65, 23, 83, 52, 19};
//        Integer[]array = new Integer[]{1,2,3,4,5};
//        Integer[]array = new Integer[]{5,4,3,2,1};
//        Integer[]array = new Integer[]{1,4,3,2,5};
//        ArrayUtil.print(array);
//
//        Sort<Integer> bubbleSort1= new BubbleSort3<>();
//        Integer[] newArray = Integers.copy(array);
//        bubbleSort1.sort(newArray);
//
////        Asserts.test(Integers.isAscOrder(newArray));
//        ArrayUtil.print(newArray);

//        Integer[] integers = Integers.tailAscOrder(0, 10, 3);
//        ArrayUtil.print(integers);
        sortByType(new BubbleSort2<>());
        sortByType(new BubbleSort3<>());
        sortByType(new BubbleSort4<>());

    }

    @Test
    public void testSelect() {

        sortByType(new SelectSort<>());
    }

    private static void sortByType(Sort<Integer>bubbleSort1 ) {
        Integer[] integers = new Integer[]{3, 2, 1, 8, 9, 10};//5 + 3 +
        Integer[] newArray = Integers.copy(integers);
        ArrayUtil.print(newArray);
        bubbleSort1.sort(newArray);
        ArrayUtil.print(newArray);

        System.out.println(bubbleSort1);
    }



}
