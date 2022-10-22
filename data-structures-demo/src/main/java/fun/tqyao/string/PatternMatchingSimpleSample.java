/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.string;


import org.junit.Test;

import java.util.Arrays;

/**
 * 简单模式匹配算法
 * @author tqyao
 * @version 1.0.0
 * @create 2022/10/05 13:53
 */
public class PatternMatchingSimpleSample {


    @Test
    public void test() {
        String mainStr = "I Love You";
//        String subStr = "u";
        String subStr = "ILove";
//        String subStr = "u ";
        System.out.println(indexOf(mainStr.toCharArray(), subStr.toCharArray()));
        System.out.format("%s indexOf %s = %d",
                Arrays.toString(mainStr.toCharArray()), subStr, mainStr.indexOf(subStr));
    }

    @Test
    public void test02(){
        String mainStr = "I Love You";
//        String subStr = "u";
//        String subStr = "ILove";
//        String subStr = "u ";
        String subStr = "Love You";
        System.out.println(indexOf02(mainStr.toCharArray(), subStr.toCharArray()));
        System.out.format("%s indexOf %s = %d",
                Arrays.toString(mainStr.toCharArray()), subStr, mainStr.indexOf(subStr));
    }



    public static int indexOf02(char[] mainChs, char[] subChs) {
        // 当前匹配开始索引
        int k = 0;
        // 主串 与 子串索引
        int i, j;
        i = j = 0;
        // 1. i 不满足说明主串越界
        // 2. j 不满足说明主串与子串成功匹配返回此时索引
        while (i < mainChs.length && j < subChs.length) {
            if (mainChs[i] == subChs[j]) {
                i++;
                j++;
            } else {
                k++;
                i = k;
                j = 0;
            }
        }
        if (j >= subChs.length) {
            return k;
        }
        return -1;
    }

    /**
     * 串 简单模式匹配
     *
     * @param mainChs
     * @param subChs
     * @return int
     * @author tqyao
     * @create 2022/10/5 15:21
     */
    public static int indexOf(char[] mainChs, char[] subChs) {
        int k;
        boolean flag;
        for (int i = 0; i < mainChs.length; i++) {
            k = i;
            flag = true;
            for (int j = 0; j < subChs.length; j++) {
                // 如果有不匹配 flag 设为 false ，如果子串每个字符都匹配则 flag 为 true
                if (mainChs[k++] != subChs[j]) {
                    flag = false;
                    break;
                }
                // 如果 k = 主串长度，说明匹配到主串最后一个元素的下一个元素（越界）
                // ，前面子串与主串的各个字符是相匹配的，如果此时子串还有下一个元素那么就判定不匹配
                // 否者说明在主串与子串匹配恰好在主串最后几个等长于子串的几个元素
                if (k == mainChs.length && j + 1 < subChs.length) return -1;
            }
            // flag 为 T 的话说明找到匹配子串 立即返回
            if (flag) return i;
        }
        return -1;
    }
}
