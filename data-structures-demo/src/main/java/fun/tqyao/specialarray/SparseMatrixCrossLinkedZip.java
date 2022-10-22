package fun.tqyao.specialarray;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 稀疏矩阵的压缩存储 ———— 十字链表
 *
 * @author tqyao
 * @create 2022/9/25 14:13
 */
public class SparseMatrixCrossLinkedZip {


    int[][] matrixArr;

    /**
     * 矩阵生成
     *
     * @param n 矩阵阶数
     * @author tqyao
     * @create 2022/9/28 16:43
     */
    void createSparseMatrix(int n) {
        matrixArr = new int[n][n];
        Scanner scanner = new Scanner(System.in);
        String inp;
        int[] arr;
        int i, j, val;
        while (true) {
            System.out.print("依次输入矩阵行、列以及其对应值（\" \"间隔，\"q\"结束） >>");
            inp = scanner.nextLine();
            if ("q".equals(inp)) return;
            arr = Arrays.stream(inp
                    .split(" ")).mapToInt(Integer::parseInt).toArray();
            i = arr[0];
            j = arr[1];
            val = arr[2];
            matrixArr[i][j] = val;

        }
    }

    /**
     * 打印矩阵
     * @author tqyao
     * @create 2022/9/28 16:44
     */
    void printMatrixArr() {
        int n = matrixArr.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrixArr[i][j] + "\t");
            }
            System.out.println();
        }
    }


    void matrixZip(){

    }

    public static void main(String[] args) {
        SparseMatrixCrossLinkedZip crossLinkedZip = new SparseMatrixCrossLinkedZip();
        crossLinkedZip.createSparseMatrix(5);
        crossLinkedZip.printMatrixArr();
    }



}