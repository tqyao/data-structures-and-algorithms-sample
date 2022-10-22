/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.specialarray;

/**
 * 三对角矩阵接口
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/27 15:41
 */
public interface ITriDiagonalMatrix {

    /**
     * 获取 n 阶三对角矩阵有效元素总数
     *
     * @param n
     * @return int
     * @author tqyao
     * @create 2022/9/27 18:31
     */
    default int getSumByN(int n) {
        return n * 3 - 2;
    }

    /**
     * 一维数组与三对角矩阵映射
     *
     * @param idx
     * @return int
     * @author tqyao
     * @create 2022/9/27 18:32
     */
    default int oneDimensionMap(int idx){
        return 0;
    }

}
