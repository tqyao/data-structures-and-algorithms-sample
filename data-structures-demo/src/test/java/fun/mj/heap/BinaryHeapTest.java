package fun.mj.heap;

import fun.mj.util.RandomUtil;
import fun.mj.util.printer.BinaryTrees;
import org.junit.jupiter.api.Test;
import sun.java2d.pipe.SpanIterator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BinaryHeapTest {

    @Test
    public void testAdd() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        heap.add(66);

        BinaryTrees.println(heap);
        heap.add(51);
//        heap.add(41);

//        BinaryTrees.println(heap);
    }

    @Test
    public void testRemove() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(68);
        heap.add(72);
        heap.add(43);
        heap.add(50);
        heap.add(38);
        heap.add(10);
        heap.add(90);
        heap.add(65);
        heap.add(66);
        BinaryTrees.print(heap);
        System.out.println("\n-------");

        heap.remove();
        BinaryTrees.print(heap);
        System.out.println("\n---------");

        heap.remove();
        BinaryTrees.print(heap);
        System.out.println("\n---------");

    }


    @Test
    public void testHeapify() {
        Integer[] integers = RandomUtil.rangeRandom(20, 0, 100);
        BinaryHeap<Integer> heap = new BinaryHeap<>(integers);
        BinaryTrees.print(heap);
    }


    @Test
    public void testSmallHeap() {
        Integer[] integers = new Integer[]{
                35, 13, 22, 19, 9, 3, 43, 39, 95, 76
        };
        BinaryHeap<Integer> smallHeap = new BinaryHeap<>(integers, (o1, o2) -> o2 - o1);
        BinaryTrees.print(smallHeap);
    }


    /**
     * topK 问题
     * 找出无序数组中最大的 K 个数
     * 解决 ：小顶堆
     * <p>
     * 1. 前 k 个元素加入堆构成 k 个元素小顶堆
     * 2. 当加入元素 > k 时，用堆顶元素与数组后续加入元素比较
     * ，如果数组元素较大则替换堆顶元素
     * <p>
     * TC = nlog(k)
     *
     * @author tqyao
     * @create 2023/10/7 20:20
     */
    @Test
    public void testTopK() {
//        Integer[] integers = RandomUtil.rangeRandom(30, 0, 100);
//        System.out.println(Arrays.toString(integers));

        // 目标数组
        Integer[] integers = new Integer[]{
                35, 13, 22, 19, 9, 3, 43, 39, 95, 76
        };
        System.out.println(Arrays.toString(integers));
        // 创建一个小顶堆
        BinaryHeap<Integer> smallHeap = new BinaryHeap<>((o1, o2) -> o2 - o1);
        int k = 4;//找到数组前 k 个最大值

        for (int i = 0; i < integers.length; i++) {

            if (i < k) {
                smallHeap.add(integers[i]); // log(k)
            } else if (integers[i] > smallHeap.get()) {
                smallHeap.replace(integers[i]); // log(k)
            }
        }

        BinaryTrees.print(smallHeap);
    }

}