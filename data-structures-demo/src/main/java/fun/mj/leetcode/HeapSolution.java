/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.leetcode;

import org.checkerframework.framework.qual.FromByteCode;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/08 15:31
 */
public class HeapSolution {

    /**
     * https://leetcode.cn/problems/smallest-k-lcci/
     * 设计一个算法，找出数组中最小的k个数。以任意顺序返回这k个数均可。
     * <p>
     * 大顶堆
     *
     * @param arr
     * @param k
     * @return int[]
     * @author tqyao
     * @create 2023/10/8 15:32
     */
    public int[] smallestK(int[] arr, int k) {
        if (k < 1)
            return new int[0];
        if (arr == null || arr.length < 2)
            return arr;
        // 大顶堆
        PriorityQueue<Integer> queue = new PriorityQueue<>((num1, num2) -> num2 - num1);
        // 维护元素为 k 的大顶堆
        for (int i = 0; i < k; ++i) {
            queue.offer(arr[i]);
        }

        for (int i = k; i < arr.length; i++) {// n - k
            if (queue.peek() > arr[i]) {
                queue.poll();//logk
                queue.offer(arr[i]);//logk
            }
        }

        return Arrays.stream(queue.toArray()).mapToInt(e -> (int) e).toArray();
    }




}
