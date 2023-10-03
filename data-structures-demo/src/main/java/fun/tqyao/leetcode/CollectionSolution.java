/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/03 17:58
 */
public class CollectionSolution {


    /**
     * 349. 两个数组的交集
     * https://leetcode.cn/problems/intersection-of-two-arrays/
     * <p>
     * set
     *
     * @param nums1
     * @param nums2
     * @return int[]
     * @author tqyao
     * @create 2023/10/3 17:58
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> collect = Arrays.stream(nums1).boxed().collect(Collectors.<Integer>toSet());
        Set<Integer> collect2 = Arrays.stream(nums2).boxed().collect(Collectors.<Integer>toSet());
        HashSet<Integer> res = new HashSet<>();

        if (collect2.size() > collect.size()) {
            for (Integer integer : collect) {
                if (collect2.contains(integer)) {
                    res.add(integer);
                }
            }
        } else {
            for (Integer integer : collect2) {
                if (collect.contains(integer)) {
                    res.add(integer);
                }
            }
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 排序 + 双指针
     *
     * @param nums1
     * @param nums2
     * @return int[]
     * @author tqyao
     * @create 2023/10/3 19:40
     */
    public int[] intersection200(int[] nums1, int[] nums2) {
        // 1. 排序
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        Set<Integer> res = new HashSet<>();
        Integer prev = null;
        // 两数组初始指针
        int idx1 = 0, idx2 = 0;
        for (; idx1 < nums1.length && idx2 < nums2.length; ) {// 任意一数组超出范围退出循环

            // 2.1 如果数组 1，2 元素相等且不为重复元素，加入到结果集
            // 2.2 两数组指针一起向前移动
            if ((nums1[idx1] == nums2[idx2])
                    && (prev == null || !prev.equals(nums1[idx1]))) {// 防止加入重复元素
                // 交集元素
                res.add(nums1[idx1]);
                prev = nums1[idx1];
                idx1++;
                idx2++;

            }
            // 2. 移动元素比较小的数组指针
            else if (nums1[idx1] > nums2[idx2]) {
                idx2++;
            } else {
                idx1++;
            }

        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }


}
