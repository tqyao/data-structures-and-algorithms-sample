package exercise.sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SortDemoTest {

    @Test
    public void testSelectSort() {
        int[] is = new int[]{5, 4, 3, 2, 1};
        SortDemo.selectSort(is);
        System.out.println(Arrays.toString(is));
    }

    @Test
    public void testSelectSort2() {
        int[] is = new int[]{5, 4, 3, 2, 1};
        SortDemo.selectSort2(is);
        System.out.println(Arrays.toString(is));
    }
}