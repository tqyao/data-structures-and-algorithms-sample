/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package specialarray;

/**
 * 三角矩阵压缩存储 行优先
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/25 18:55
 */
public class TriMatrixOfDownRow extends TriangleMatrixZip {


    /**
     * 三角矩阵映射一维数组
     *
     * @param i 行号
     * @param j 列号
     * @return int
     * @author tqyao
     * @create 2022/9/25 15:48
     */
    @Override
    protected int triMatrixMap(int i, int j) {
        //        return i < j ? this.arr1D[this.arr1D.length - 1] : this.arr1D[(i + 1) * i / 2 + j];
        int idx;
        if (i >= j)
            idx = (i + 1) * i / 2 + j;
        else
            idx = this.arr1D.length - 1;
        return this.arr1D[idx];
    }

    public TriMatrixOfDownRow(int[][] triMatrix) {
        super(triMatrix);
    }

    /**
     * 行优先压缩三角矩阵
     * @author tqyao
     * @create 2022/9/25 19:02
     * @param triMatrix
     */
    @Override
    protected void init(int[][] triMatrix) {
        // 获取矩阵阶数
        int n = triMatrix.length;
        // 根据阶数计算下三角矩阵有效元素个数再初始化一维数组（最后一个元素是提供给访问 j>i）
        this.arr1D = new int[getNSum(n) + 1];
        int cur = 0;
        // 把下三角矩阵有效元素按行优先一次放入一维数组
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                arr1D[cur++] = triMatrix[i][j];
            }
        }
    }

}
