package specialarray;

/**
 * 稀疏矩阵的压缩存储 ———— 三元组法
 * @author tqyao
 * @create 2022/9/25 14:13
 */
public class SparseMatrixTriadZip {


    public static void main(String[] args) {

        // 初始化稀疏数组
        int[][] sparseArr = new int[11][11];
        sparseArr[1][2] = 1;
        sparseArr[2][3] = 2;
        sparseArr[4][5] = 1;
        print2DArray(sparseArr);

        System.out.println();
        int[][] triadArr = sparseArrayToTriadArray(sparseArr);
        print2DArray(triadArr);

        System.out.println();
        print2DArray(triadArrayToSparseArray(triadArr));
    }


    /**
     * 打印二维数组
     *
     * @param array
     * @author tqyao
     * @create 2022/9/25 12:02
     */
    private static void print2DArray(int[][] array) {
        for (int[] i : array) {
            for (int j : i) {
                System.out.print(j + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 稀疏数组转三元组
     *
     * @param sparseArray
     * @return int[][]
     * @author tqyao
     * @create 2022/9/25 12:03
     */
    public static int[][] sparseArrayToTriadArray(int[][] sparseArray) {
        int count = 0, row = 0, col = 0;
        // 是否循环过一次列 （只计算第一次循环的总列数即可）
        boolean flag = false;
        // 1. 计算矩阵行列和非0元素个数
        for (int[] i : sparseArray) {
            row++;
            for (int j : i) {
                if (!flag) col++;
                if (j != 0) count++;
            }
            flag = true;
        }

        /*
         * 2. 初始化三元组数组
         * count + 1：第一行需存储矩阵信息以便恢复
         */
        int[][] triadArray = new int[count + 1][3];
        // 3. 设置稀疏数组首行元素为矩阵长宽
        triadArray[0][0] = row;
        triadArray[0][1] = col;
        triadArray[0][2] = count;

        // 记录三元组还未存储数据的首行数组
        int curRow = 1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (sparseArray[i][j] != 0) {
                    triadArray[curRow][0] = i;
                    triadArray[curRow][1] = j;
                    triadArray[curRow][2] = sparseArray[i][j];
                    curRow++;
                }
            }
        }
        return triadArray;
    }

    /**
     * 三元组转稀疏数组
     * @author tqyao
     * @create 2022/9/25 14:08
     * @param triadArray
     * @return int[][]
     */
    public static int[][] triadArrayToSparseArray(int[][] triadArray) {
        // 1. 初始化 稀疏矩阵
        int[][] sparseArray = new int[triadArray[0][0]][triadArray[0][1]];

        // 2. 把三元组每行、列对应值取出赋值给稀疏数组
        int row, col, val;
        for (int i = 1; i < triadArray.length; i++) {
            row = triadArray[i][0];
            col = triadArray[i][1];
            val = triadArray[i][2];
            sparseArray[row][col] = val;
        }
        return sparseArray;
    }
}