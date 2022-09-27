/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package specialarray;

/**
 * 对称上三角列优先矩阵
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/27 09:45
 */
public class SymMatrixOfUpColPriority extends TriangleMatrixZip {

    private static final ITriMatrixCreate matrixCreate = new SymMatrixCreate();

    public SymMatrixOfUpColPriority(int n) {
        super(matrixCreate, n);
    }

    @Override
    protected int triMatrixMap(int i, int j) {
        int idx;
        if (j >= i) {
            idx = (1 + j) * j / 2 + i;
        } else {
            idx = (1 + i) * i / 2 + j;
        }
        return arr1d[idx];
    }

    @Override
    protected void zipTriMatrixToArray() {
        int n = arr2d.length;
        arr1d = new int[getNSum(n)];
        int cur = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= j; i++) {
                arr1d[cur++] = arr2d[i][j];
            }
        }
    }
}
