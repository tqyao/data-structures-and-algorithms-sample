/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package specialarray;

/**
 * 三角矩阵压缩存储 列优先
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/25 18:55
 */
public class TriMatrixOfDownCol extends TriangleMatrixZip {

    private int order;

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
        int idx;
        if (i >= j) {
            idx = (2 * this.order - j + 1) * j / 2 + i - j; // 最后 i -j 无需再减去1，因为数组下标从0开始
            System.out.format("idx = %d ", idx);
        } else {
            idx = getNSum(this.order);
        }
        return this.arr1D[idx];
    }

    public TriMatrixOfDownCol(int[][] triMatrix) {
        super(triMatrix);
        this.order = triMatrix.length;
    }

    /**
     * 列优先压缩三角矩阵
     *
     * @param triMatrix
     * @author tqyao
     * @create 2022/9/25 19:02
     */
    @Override
    protected void init(int[][] triMatrix) {
        // 获取三角矩阵阶数
        int n = triMatrix.length;
        // 根据阶数计算一维数组长度
        this.arr1D = new int[getNSum(n) + 1];
        // 记录一维数组存储数据指针
        int cur = 0;
        int i;
        for (int j = 0; j < n; j++) {
            // 按列优先存储，第 j 列是从第 i 行元素开始
            i = j;
            for (; i < n; i++) {
                this.arr1D[cur++] = triMatrix[i][j];
            }
        }
    }


}
