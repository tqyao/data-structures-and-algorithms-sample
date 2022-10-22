/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.specialarray;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 三角矩阵压缩存储
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/25 14:32
 */
public abstract class TriangleMatrixZip implements ITriDiagonalMatrix {

    public TriangleMatrixZip(int[][] arr2d) {
        this.arr2d = arr2d;
        if (arr2d.length > 0)
            zipTriMatrixToArray();
    }


    public TriangleMatrixZip(ITriMatrixCreate matrixCreate, int n) {
        this.arr2d = matrixCreate.triMatrixInit(n);
        zipTriMatrixToArray();
    }

    public static void main(String[] args) {
//        TriangleMatrixZip matrixZip = new TriMatrixOfDownRowPriority(5);
//        TriangleMatrixZip matrixZip = new SymMatrixOfUpRowPriority(5);
//        TriangleMatrixZip matrixZip = new SymMatrixOfUpColPriority(5);
//        TriangleMatrixZip matrixZip = new SymMatrixOfDownColPriority(5);
//        TriangleMatrixZip matrixZip = new TriDiagonalMatrixOfRowPriority(5);

//        matrixZip.print2DArray();
//        matrixZip.print1DArray();
//        matrixZip.print2DArray();
//        matrixZip.print1DArray();
//
//        jugEqualForMatrixReflect(matrixZip.arr2d, matrixZip);


        TriangleMatrixZip matrixZip = new TriDiagonalMatrixOfRowPriority(5);
        matrixZip.print2DArray();
        matrixZip.print1DArray();
//        jugEqualForMatrixReflect(matrixZip.arr2d, matrixZip);

        jugEqualForOneDimensionReflect(matrixZip);

    }


    /**
     * 压缩后的一维数组
     */
    public int[] arr1d;

    /**
     * 矩阵
     */
    public int[][] arr2d;


    /**
     * 二维数组映射一维数组
     *
     * @param i
     * @param j
     * @return int
     * @author tqyao
     * @create 2022/9/27 08:56
     */
    protected abstract int triMatrixMap(int i, int j);


    /**
     * 压缩矩阵成一维数组
     *
     * @author tqyao
     * @create 2022/9/27 08:55
     */
    protected abstract void zipTriMatrixToArray();


    protected void print1DArray() {
        System.out.println(Arrays.toString(this.arr1d));
    }


    /**
     * 输入 压缩后一维数组索引在矩阵中查找对应值是否相等
     * @author tqyao
     * @create 2022/9/27 20:22
     * @param diagonalMatrix
     */
    public static void jugEqualForOneDimensionReflect(TriangleMatrixZip diagonalMatrix) {
        Scanner scanner = new Scanner(System.in);
        int inp;
        while (true) {
            System.out.print("一维数组索引，按 '-1' 结束>> ");
            inp = scanner.nextInt();
            if (inp == -1) return;
            System.out.printf("arr1d[%d] == .oneDimensionMap(%d) " +
                            ">> %d == %d => %b%n", inp, inp, diagonalMatrix.arr1d[inp], diagonalMatrix.oneDimensionMap(inp)
                    , diagonalMatrix.arr1d[inp] == diagonalMatrix.oneDimensionMap(inp));
        }
    }

    /**
     * 输入矩阵行列值在压缩后的一维数组中查找对应值是否相等
     *
     * @param triArray
     * @param arrayZip
     * @author tqyao
     * @create 2022/9/27 19:47
     */
    public static void jugEqualForMatrixReflect(int[][] triArray, TriangleMatrixZip arrayZip) {
        Scanner scanner = new Scanner(System.in);
        String inp;
        int[] arr;
        int i, j;
        while (true) {
            System.out.print("输入行、列号以','分隔，按 'q' 结束>> ");
            inp = scanner.nextLine();
            if ("q".equals(inp)) return;
            arr = Arrays.stream(inp.split(",")).mapToInt(Integer::parseInt).toArray();
            i = arr[0];
            j = arr[1];
            System.out.format("triMatrixMap(%d, %d) " +
                            "== triArray[%d][%d] >> %d == %d => %b\n", i, j, i, j,
                    arrayZip.triMatrixMap(i, j), triArray[i][j],
                    triArray[i][j] == arrayZip.triMatrixMap(i, j));
        }
    }

    /**
     * 获取前1~n项和
     *
     * @param n
     * @return int
     * @author tqyao
     * @create 2022/9/25 15:08
     */
    public static int getNSum(int n) {
        return (n + 1) * n / 2;
    }


    /**
     * 打印二维数组
     *
     * @author tqyao
     * @create 2022/9/25 12:02
     */
    public void print2DArray() {
        for (int[] i : arr2d) {
            for (int j : i) {
                System.out.print(j + "\t");
            }
            System.out.println();
        }
    }


    public static int getRandom() {
        return (int) (Math.random() * 20 + 1);
    }


    /**
     * 创建一个三角矩阵
     *
     * @author tqyao
     * @create 2022/9/26 19:57
     */
    interface ITriMatrixCreate {
        /**
         * 三角矩阵初始化
         *
         * @param n
         * @return
         */
        int[][] triMatrixInit(int n);
    }

    /**
     * 下三角矩阵
     */
    static class DownTriMatrixCreate implements ITriMatrixCreate {
        @Override
        public int[][] triMatrixInit(int n) {
            int[][] triMatrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= i; j++) {
                    triMatrix[i][j] = getRandom();
                }
            }
            return triMatrix;
        }
    }

    /**
     * 对称矩阵
     */
    static class SymMatrixCreate implements ITriMatrixCreate {

        @Override
        public int[][] triMatrixInit(int n) {
            int[][] symMatrix = new int[n][n];
            int j;
            for (int i = 0; i < n; i++) {
                j = i;
                for (; j < n; j++) {
                    symMatrix[i][j] = getRandom();
                    symMatrix[j][i] = symMatrix[i][j];
                }
            }
            return symMatrix;
        }
    }

    /**
     * 三对角矩阵创建
     *
     * @author tqyao
     * @create 2022/9/27 18:18
     */
    static class TriDiagonalMatrixCreate implements ITriMatrixCreate {
        @Override
        public int[][] triMatrixInit(int n) {
            int[][] triDiagonalMx = new int[n][n];
            int j;
            for (int i = 0; i < n; i++) {
                // 如果是前两行，则从第0列开始录入随机值，否则从第 i - 1 列开始录入随机值
                j = i <= 1 ? 0 : i - 1;

                /*
                除第0行和最后一行是两个元素，其余行都是3元素;
                最后一行仅用 i- j 绝对值是否大于1判断会数组越界，所以需要 j!=n约束
                 */
                for (; Math.abs(i - j) <= 1 && j != n; j++) {
                    triDiagonalMx[i][j] = getRandom();
                }
            }
            return triDiagonalMx;
        }
    }
}
