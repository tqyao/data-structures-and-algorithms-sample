/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/07 19:56
 */
public class RandomUtil {

    static Random random = new Random();


    /**
     * 获取随机数
     * - 默认不去重
     *
     * @param count
     * @param start
     * @param end
     * @return java.lang.Integer[]
     * @author tqyao
     * @create 2023/10/7 20:18
     */
    public static Integer[] rangeRandom(int count, int start, int end) {
        return rangeRandom(count, start, end, false);
    }

    /**
     * 获取随机数
     *
     * @param count    指定获取个数
     * @param start    指定获取数起始范围
     * @param end      指定获取数终止范围
     * @param distinct 是否去重
     * @return
     */
    public static Integer[] rangeRandom(int count, int start, int end, boolean distinct) {
        Stream<Integer> boxed = random.ints(count, start, end).boxed();
        if (distinct) {
            boxed = boxed.distinct();
        }
        return boxed.toArray(Integer[]::new);
    }


}
