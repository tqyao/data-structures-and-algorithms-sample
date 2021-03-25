package sparsearray;

import java.awt.print.Printable;
import java.io.*;

public class SparseArray {

    static String file = "E:\\Java Workspace\\data-structures-and-algorithms_sample\\data-structures-demo\\src\\main\\java\\sparsearray\\sparse_array";

    public static void main(String[] args) {

        // 初始化11行11列的二维数组，1代表黑子，2代表蓝子
        int[][] twoArray = new int[11][11];
        twoArray[1][2] = 1;
        twoArray[2][3] = 2;
        twoArray[4][5] = 1;

        for (int[] row : twoArray) {
            for (int column : row) {
                System.out.printf ("%d\t", column);
            }
            System.out.println ();
        }
        System.out.println ();


        int[][] sparse = twoDimensionalArrayToSparseArray (twoArray);
        System.out.println ();
        saveTxt (sparse);

        sparse = readTxt (file);
        int[][] two = sparseArrayToTwoDimensionalArray (sparse);
    }

    private static int[][] readTxt(String file) {
        int[][] sparseArray = new int[10][3];
        try (
                BufferedReader br = new BufferedReader (
                        new InputStreamReader (new FileInputStream (file)))
        ) {
            String line = "";
            int row = 0;
            while ((line = br.readLine ()) != null) {
                String[] num = line.split ("\t");
                for (int i = 0; i < num.length; i++) {
                    sparseArray[row][i] = Integer.parseInt (num[i]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return sparseArray;
    }

    /**
     * 二维数组转稀疏数组
     * 1.遍历原始二维数组，得到有效数据个数sum
     * 2.根据sum创建稀疏数组 sparseArray int[sum+1][3]
     * 3.将二维数组的有效数据存入到稀疏数组
     *
     * @return
     */
    public static int[][] twoDimensionalArrayToSparseArray(int[][] twoArray) {
        //计算出二维数组总的非零数，确定稀疏数组行列长度
        int sum = 0;
        for (int row = 0; row < twoArray.length; row++) {
            for (int column = 0; column < twoArray.length; column++) {
                if (twoArray[row][column] != 0) {
                    sum++;
                }
            }
        }

        //创建稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        //给稀疏数组赋值
        int count = 0;
        for (int row = 0; row < twoArray.length; row++) {
            for (int column = 0; column < twoArray.length; column++) {
                if (twoArray[row][column] != 0) {
                    count++;
                    sparseArr[count][0] = row;
                    sparseArr[count][1] = column;
                    sparseArr[count][2] = twoArray[row][column];
                }
            }
        }

        //遍历稀疏数组
        for (int[] spa : sparseArr) {
            System.out.printf ("%d\t%d\t%d\t\n", spa[0], spa[1], spa[2]);
        }

        return sparseArr;
    }

    /**
     * 稀疏数组转二维数组
     * 1.先读取稀疏数组第一行，根据第一行的数据，创建原始的二维数组，比如上面的 chessArr2 = int[11][11]
     * 2.再给原始二维数组赋值
     *
     * @return
     */
    public static int[][] sparseArrayToTwoDimensionalArray(int[][] sparseArray) {
        //读取稀疏数组第一行数据创建原始二维数组
        int[][] towArray = new int[sparseArray[0][0]][sparseArray[0][1]];

        //给二维数组赋值
        for (int row = 1; row < sparseArray.length; row++) {
            towArray[sparseArray[row][0]][sparseArray[row][1]] = sparseArray[row][2];
        }

        //打印二维数组
        for (int i = 0; i < towArray.length; i++) {
            for (int j = 0; j < towArray[0].length; j++) {
                System.out.printf ("%d\t", towArray[i][j]);
            }
            System.out.println ();
        }

        return towArray;
    }

    public static void saveTxt(int[][] sparseArr) {
//        System.out.println (System.getProperty ("user.dir"));
//        String file =

        try (
                BufferedWriter wb = new BufferedWriter (
                        new OutputStreamWriter (new FileOutputStream (file)));
        ) {
            StringBuffer sb = new StringBuffer ();
            for (int[] arr : sparseArr) {
                wb.write (arr[0] + "\t" + arr[1] + "\t" + arr[2] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }


}
