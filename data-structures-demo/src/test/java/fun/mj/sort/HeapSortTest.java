package fun.mj.sort;

import fun.mj.sort.cmp.HeapSort;
import fun.mj.util.Integers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class HeapSortTest {

    @Test
    public void testHeapfiy() {
        Integer[] is = new Integer[]{36, 61, 52, 74, 57, 68, 88, 29, 24};
        System.out.println(Arrays.toString(is));
        HeapSort<Integer> heapSort = new HeapSort<>();
        heapSort.sort(Integers.copy(is));
//        BinaryTrees.print(heapSort);
        System.out.println(Arrays.toString(heapSort.array));

    }

}