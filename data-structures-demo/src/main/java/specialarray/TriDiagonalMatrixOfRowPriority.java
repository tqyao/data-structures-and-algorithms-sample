/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package specialarray;

/**
 * 三对角矩阵
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/27 15:05
 */
public class TriDiagonalMatrixOfRowPriority extends TriangleMatrixZip {

    private static final ITriMatrixCreate matrixCreate = new TriDiagonalMatrixCreate();

    public TriDiagonalMatrixOfRowPriority(int n) {
        super(matrixCreate, n);
    }

    @Override
    protected int triMatrixMap(int i, int j) {
        /*
         *  如果 i-j 绝对值小于等1 （符合三对角矩阵定义），则根据行优先递推公式计算前 (i-1) 行
         *  的元素总和，再计算第 i 行起始有效元素至所要映射元素中有多少元素，前者后者相加得总元素个数len，注意不
         * 由于1维数组是从索引0开始，所以len需要-1
         */
//        int idx = Math.abs(i - j) <= 1 ? (3 * i) - 1 + j - (i - 1) : getSumByN(arr2d.length);
        int idx = Math.abs(i - j) <= 1 ? 2 * i + j : getSumByN(arr2d.length);
        return arr1d[idx];
    }

    @Override
    protected void zipTriMatrixToArray() {
        int n = arr2d.length;
        arr1d = new int[getSumByN(n) + 1];
        // 一维数组指针，指向当前数组最后一个元素索引
        int cur = 0;
        int j;
        for (int i = 0; i < n; i++) {
            j = i <= 1 ? 0 : i - 1;
            for (; Math.abs(i - j) <= 1 && j < n; j++) {
                arr1d[cur++] = arr2d[i][j];
            }
        }
    }


    @Override
    public int oneDimensionMap(int idx) {
        int i, j;
        /*
         已知一维数组索引idx，则 idx + 1 <= 3（i+1） - 1，
         求出 i 后，根据  (3 * i) - 1 + j - (i - 1)  = idx 求j
         */
        i = (int) (Math.ceil((idx + 2) / 3.0)) - 1;
        j = idx - 2 * i;
        // 矩阵越界
//        if (j >= arr2d.length) {
//            throw new RuntimeException("矩阵越界，");
//        }
        return arr2d[i][j];
    }
}
