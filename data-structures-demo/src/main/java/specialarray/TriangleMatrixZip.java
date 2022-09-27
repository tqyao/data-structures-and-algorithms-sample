/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package specialarray;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 三角矩阵压缩存储
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/09/25 14:32
 */
public abstract class TriangleMatrixZip {

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
        TriangleMatrixZip matrixZip = new SymMatrixOfUpColPriority(5);

        matrixZip.print2DArray();
        matrixZip.print1DArray();
//        matrixZip.print2DArray();
//        matrixZip.print1DArray();
//
        jugEqualForMatrixReflect(matrixZip.arr2d, matrixZip);
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
     * @author tqyao
     * @create 2022/9/27 08:56
     * @param i
     * @param j
     * @return int
     */
    protected abstract int triMatrixMap(int i, int j);


    /**
     * 压缩矩阵成一维数组
     * @author tqyao
     * @create 2022/9/27 08:55
     */
    protected abstract void zipTriMatrixToArray();


    protected void print1DArray() {
        System.out.println(Arrays.toString(this.arr1d));
    }


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
}
