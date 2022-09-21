package stack;

/**
 * 栈应用————递归
 * 斐波那契数列
 * 1、 1、 2、 3、 5、 8、 13、 21、 34、 55、 89、 144、 233、 377、 610
 */
public class FibonacciOfStackApply {
    /**
     * 递归求斐波那契数列第n项
     *
     * @param n
     * @return
     */
    public static int fibSequence01(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fibSequence01(n - 1) + fibSequence01(n - 2);
    }

    /**
     * 非递归求斐波那契数列第n项
     *
     * @param n
     * @return
     */
    public static int fibSequence02(int n) {
    /*
    method 01：
     */
//        int a = 1, b = 1, res = 0;
//        if (n == 1 | n == 2) {
//            return 1;
//        }
//        for (int i = 3; i <= n; i++) {
//            res = a + b;
//            a = b;
//            b = res;
//        }
//        return res;


            /*
    method 02：
     */
        int a = 0, b = 1, res = 0;
        if (n <= 1) {
            return b;
        }
        for (int i = 2; i <= n; i++) {
            res = a + b;
            a = b;
            b = res;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(fibSequence01(8));
        System.out.println(fibSequence02(8));

    }
}
