package sort2.cmp;

import org.junit.jupiter.api.Test;
import sort2.Sort;
import tools.Integers;
import tools.printer.BinaryTreeInfo;
import tools.printer.BinaryTrees;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {

    @Test
    public void testHeapSort() {
//        Integer[] random = Integers.random(10, 0, 100);
//        System.out.println(Arrays.toString(random));

        Integer[] is = new Integer[]{72, 12, 11, 17, 66, 5, 89, 66, 13, 16};



        Sort<Integer> sort =new HeapSort<>();
        sort.sort(Integers.copy(is));
    }
}