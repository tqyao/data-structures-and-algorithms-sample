/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package sort2;

import java.text.DecimalFormat;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/08 08:11
 */
public abstract class Sort<T extends Comparable<T>> implements Comparable<Sort<T>> {

    protected T[] array;
    private long time;
    private int cmpCount;
    private int swapCount;
    private DecimalFormat fmt = new DecimalFormat("#.00");


    public void sort(T[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        this.array = array;

        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    protected abstract void sort();


    /**
     * 比较值大小
     *
     * @param idx1 数组索引
     * @param idx2 数组索引
     * @return
     */
    protected int cmp(int idx1, int idx2) {
        cmpCount++;
        return array[idx1].compareTo(array[idx2]);
    }

    /**
     * 比较值大小
     *
     * @param v1
     * @param v2
     * @return int
     * @author tqyao
     * @create 2023/10/8 08:28
     */
    protected int cmp(T v1, T v2) {
        cmpCount++;
        return v1.compareTo(v2);
    }

    protected void swap(int i1, int i2) {
        swapCount++;
        T tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }


    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }


    @Override
    public int compareTo(Sort<T> o) {
        int result = (int) (this.time - o.time);
        if (result != 0) return result;

        result = this.cmpCount - o.cmpCount;
        if (result != 0) return result;

        return this.swapCount - o.swapCount;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
//        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
//                + stableStr + " \t"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";
    }
}
