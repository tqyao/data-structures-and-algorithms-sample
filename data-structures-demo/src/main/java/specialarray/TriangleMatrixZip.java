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


    public static void main(String[] args) {
        int[][] triArray = TriMatrixOfDownRow.downTriMatrixInit(5);
        print2DArray(triArray);

        /*
        行优先
         */
//        TriangleMatrixZip arrayZip = new TriMatrixOfDownRow(triArray);
//        arrayZip.print1DArray();
//        jugEqualForMatrixReflect(triArray, arrayZip);

        /*
        列优先
         */
        TriMatrixOfDownCol arrayZip = new TriMatrixOfDownCol(triArray);
        arrayZip.print1DArray();
        jugEqualForMatrixReflect(triArray,arrayZip);
    }

    /**
     * 压缩后的一维数组
     */
    public int[] arr1D;


    public TriangleMatrixZip(int[][] triMatrix) {
        init(triMatrix);
    }

//    /**
//     * 三角矩阵映射一维数组
//     *
//     * @param i 行号
//     * @param j 列号
//     * @return int
//     * @author tqyao
//     * @create 2022/9/25 15:48
//     */
//    private int triMatrixMap(int i, int j) {
////        return i < j ? this.arr1D[this.arr1D.length - 1] : this.arr1D[(i + 1) * i / 2 + j];
//        int idx;
//        if (i >= j)
//            idx = (i + 1) * i / 2 + j;
//        else
//            idx = this.arr1D.length - 1;
//        return this.arr1D[idx];
//    }

    protected abstract int triMatrixMap(int i, int j);

    protected abstract void init(int[][] triMatrix);

//    /**
//     * 行优先压缩三角矩阵
//     *
//     * @param triMatrix
//     */
//    private void init(int[][] triMatrix) {
//        // 获取矩阵阶数
//        int n = triMatrix.length;
//        // 根据阶数计算下三角矩阵有效元素个数再初始化一维数组（最后一个元素是提供给访问 j>i）
//        this.arr1D = new int[getNSum(n) + 1];
//        int cur = 0;
//        // 把下三角矩阵有效元素按行优先一次放入一维数组
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j <= i; j++) {
//                arr1D[cur++] = triMatrix[i][j];
//            }
//        }
//    }


    protected void print1DArray() {
        System.out.println(Arrays.toString(this.arr1D));
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

//
//    /**
//     * 下三角矩阵初始化
//     *
//     * @param n 矩阵阶数
//     * @return int[][]
//     * @author tqyao
//     * @create 2022/9/25 14:55
//     */
//    public static int[][] downTriMatrixInit(int n) {
//        int[][] triMatrix = new int[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j <= i; j++) {
//                triMatrix[i][j] = getRandom();
//            }
//        }
//        return triMatrix;
//    }


    /**
     * 打印二维数组
     *
     * @param array
     * @author tqyao
     * @create 2022/9/25 12:02
     */
    public static void print2DArray(int[][] array) {
        for (int[] i : array) {
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
     * 下三角矩阵初始化
     *
     * @param n 矩阵阶数
     * @return int[][]
     * @author tqyao
     * @create 2022/9/25 14:55
     */
    public static int[][] downTriMatrixInit(int n) {
        int[][] triMatrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                triMatrix[i][j] = getRandom();
            }
        }
        return triMatrix;
    }

}
