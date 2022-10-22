/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.specialarray;

/**
 * 下三角对称矩阵
 * 由于是对称矩阵，所以上下三角对关于对角线对称，所以创建矩阵的方式都相同，只是压缩成一维数组和映射逻辑有不同
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/27 13:07
 */
public class SymMatrixOfDownColPriority extends TriangleMatrixZip {


    private static final ITriMatrixCreate matrixCreate = new SymMatrixCreate();

    public SymMatrixOfDownColPriority(int n) {
        super(matrixCreate, n);
    }

    @Override
    protected int triMatrixMap(int i, int j) {
        int idx;
        int n = arr2d.length;
        if (i >= j) {
            idx = (2 * n - j + 1) * j / 2 + (i - j);
        } else {
            idx = (2 * n - i + 1) * i / 2 + (j - i);
        }
        return arr1d[idx];
    }

    @Override
    protected void zipTriMatrixToArray() {
        int n = arr2d.length;
        arr1d = new int[getNSum(n)];
        int cur = 0;
        int i;
        for (int j = 0; j < n; j++) {
            i = j;
            for (; i < n; i++) {
                arr1d[cur++] = arr2d[i][j];
            }
        }
    }
}
