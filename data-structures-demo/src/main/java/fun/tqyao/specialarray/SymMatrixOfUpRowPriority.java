/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.specialarray;

/**
 * 对称上三角行优先矩阵
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/26 22:44
 */
public class SymMatrixOfUpRowPriority extends TriangleMatrixZip {


    private static final ITriMatrixCreate matrixCreate = new SymMatrixCreate();

    public SymMatrixOfUpRowPriority(int n) {
        super(matrixCreate, n);
    }


    @Override
    protected int triMatrixMap(int i, int j) {
        int idx;
        int n = arr2d.length;
        if (j >= i) {
            idx = (2 * n - i + 1) * i / 2 + j - i;
        } else {
            idx = (2 * n - j + 1) * j / 2 + i - j;
        }
        return arr1d[idx];
    }

    @Override
    protected void zipTriMatrixToArray() {
        int n = arr2d.length;
        arr1d = new int[getNSum(arr2d.length)];
        int cur = 0;
        int j;
        for (int i = 0; i < n; i++) {
            j = i;
            for (; j < n; j++) {
                arr1d[cur++] = arr2d[i][j];
            }
        }
    }
}
