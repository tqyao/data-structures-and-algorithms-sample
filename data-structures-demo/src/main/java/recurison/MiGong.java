package recurison;

import java.util.Arrays;

public class MiGong {

    public static void main(String[] args) {
        // 先创建一个迷宫
        int[][] mg = new int[8][7];

        for (int i = 0; i < mg.length; i++) {
            //设置列围墙
            mg[i][0] = 1;
            mg[i][6] = 1;

            if (i < 7) {
                //设置行围墙
                mg[0][i] = 1;
                mg[7][i] = 1;
            }
        }

        mg[3][1] = 1;
        mg[3][2] = 1;


        //打印迷宫
        for (int i = 0; i < mg.length; i++) {
            for (int j = 0; j < mg[0].length; j++) {
                System.out.print (mg[i][j] + "\t");
            }
            System.out.println ();
        }

        passWay (mg, 1, 1);

        System.out.println ();
        for (int i = 0; i < mg.length; i++) {
            for (int j = 0; j < mg[0].length; j++) {
                System.out.print (mg[i][j] + "\t");
            }
            System.out.println ();
        }


    }

    /**
     * 找迷宫出口
     * 起始位置：row = 1， column = 1
     * 终点位置：row = 6， column = 5
     * 朝目标点走过的路标记为：2
     * 如果无法到达目标点标记为：3
     *
     * @param mg
     * @param row
     * @param column
     * @return
     */
    public static boolean passWay(int[][] mg, int row, int column) {
        if (mg[6][5] == 2) {
            // 找到出口
            return true;
        }

        if (mg[row][column] == 0) {   // 该点是可以走的，但不一定是走向出口的
            mg[row][column] = 2;
            // 下左上右
            if (passWay (mg, row + 1, column)) {   //下
                return true;
            } else if (passWay (mg, row, column + 1)) {  // 右
                return true;
            } else if (passWay (mg, row - 1, column)) {  // 上
                return true;
            } else if (passWay (mg, row, column - 1)) { // 左
                return true;
            } else {
                mg[row][column] = 3;
                return false;
            }
        } else {
            //   mg[row][column] = 3 或 1 或 2
            return false;
        }
    }

    /**
     * 获取步长
     * 如果全为3，表示没有找到出口的路径，则返回-1
     * @param mg
     * @return
     */
    public static int getStep(int[][] mg) {
        int step = 0;
        for (int i = 0; i < mg.length; i++) {
            for (int j = 0; j < mg[0].length; j++) {
                if (mg[i][j] == 2) {
                    step += 2;
                }
            }
        }
        return step == 0 ? -1 : step;
    }

}
