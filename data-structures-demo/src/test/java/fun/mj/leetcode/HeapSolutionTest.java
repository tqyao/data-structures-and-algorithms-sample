package fun.mj.leetcode;

import fun.mj.util.ArrayUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HeapSolutionTest {

    @Test
    public void test(){
        int[] ints = {1, 3, 5, 7, 2, 4, 6, 8};
        int[] ints1 = new HeapSolution().smallestK(ints, 4);
        ArrayUtil.print(Arrays.stream(ints1).boxed().toArray());
    }
}