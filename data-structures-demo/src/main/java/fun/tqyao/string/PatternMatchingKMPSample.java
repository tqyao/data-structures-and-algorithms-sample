/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.string;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

/**
 * kmp
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/10/07 10:40
 */
public class PatternMatchingKMPSample {


    static char[] sub;
    static char[] main;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("PatternMatchingKMPSample.beforeClass");

        sub = "ababaaababaa".toCharArray();
        main = "aaaababtaaababaabbababaaababaab".toCharArray();

        sub = "ababaaababaa".toCharArray();
        main = "asdaababaddsaababaaababaaababaaas".toCharArray();

        sub = "aaaab".toCharArray();
        main = "aaacaaaab".toCharArray();
    }

    @Test
    public void testGetNext() {
//        char[] sub = "aaab".toCharArray();
//        char[] sub = "ababaaababaa".toCharArray();
        System.out.println(Arrays.toString(getNext(sub)));
    }

    @Test
    public void testGetNext02() {
//        char[] sub = "ababaaababaa".toCharArray();
        System.out.println(Arrays.toString(getNext02(sub)));
    }

    @Test
    public void testKmp() {
        String mainStr = new String(main);
        String subStr = new String(sub);
        int idx01 = mainStr.indexOf(subStr);
        int idx02 = kmpIndex02(main, sub, getNext02(sub));
        System.out.println(idx01);
        System.out.println(idx02);
        Assert.assertEquals(idx01, idx02);
//        Assert.assertNotEquals(idx01,idx02);
    }

    /**
     * kmp 求子串位置
     *
     * @param main 主串
     * @param sub  模式串
     * @param next
     * @return
     */
    public static int kmpIndex01(char[] main, char[] sub, int[] next) {
        int i, j;
        i = j = 0;
/*         指针 i 不回溯，最坏情况是：模式子串中不存在最大前后缀相等的子串长度
         ，意味着S[i] != sub[j] 时，每次 j 都回溯到 0，从子串头部开始再次寻求匹配
         ，如果匹配失败，至少需要需要比较 m + n 次
         ，如果匹配成功则只需要比较 m 次（m 为 主串的长度，n为子串的长度）*/

        // 由于 i 不回溯，所以如果是因 i 大于等于主串长度退出循环是时是匹配失败的情况
        // ，当时因 j 大于等于子串长度而退出循环，此时是匹配成功情况
        // （这点和朴素模式匹配一直）
        while (i < main.length && j < sub.length) {
             /*如果 子串与主串元素匹配，则i++ j++ 检查下一项也是否匹配
             ，否者通过 next 数组找到当前子串第 j 个字符匹配失败时指针 j 应当回溯到的位置。

             特殊的：当子串 j = 0 也匹配失败时，说明主串s[i]此时以该元素开头的元素无法与子串第一个字符匹配
             ，则需将子串右滑1位，主串也前进一位进行比较
             注意：next[j = 0] = -1
             */
            if (j == -1 || main[i] == sub[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        // 当时因 j 大于等于子串长度而退出循环，此时是匹配成功情况
        // ，用此时 主串 i 减去已匹配的子串长度 = 子串在主串中第一次出现的位序
        if (j >= sub.length) return i - sub.length;
        return -1;
    }

    /**
     * 求 kpm next 数组
     *
     * @param sub 模式串
     * @return
     */
    public static int[] getNext(char[] sub) {
        int[] next = new int[sub.length];
        next[0] = -1;
        //任何子串第二个字符匹配失败时，前面只有1个字符（也可以说最大前后缀相等长度为0）
//        next[1] = 0;
        int j = 0;
        int k = next[j];
        boolean flag = false;
        while (j < sub.length - 1) {
            k = flag ? k : next[j];
            if (k == -1 || sub[j] == sub[k]) {
                next[j + 1] = k + 1;
                j++;
                flag = false;
            } else {
                k = next[k];
                flag = true;
            }
        }
        return next;
    }


    public static int kmpIndex02(char[] main, char[] sub, int[] next) {
        int i, j;
        i = j = 0;
        while (i < main.length && j < sub.length) {
            if (j == -1 || main[i] == sub[j]) {
                i++;
                j++;
            } else
                j = next[j];
        }
        return j >= sub.length ? i - sub.length : -1;
    }


    /**
     * 求 kmp next数组
     * 数学归纳法求已知第一项的下一项： next [j + 1] = ?
     * <p>
     * 初始化:
     * next [0] = -1
     * j = 0, k = next[j] = next [0] = -1
     * <p>
     * 第一次循环if判断: k == -1 进入 if 代码块
     * next [++j] = next [j = 1+1 = 1]
     * ++ k = -1 + 1 = 0;
     * next[++j] = ++k  <=> next [1] = 0;
     * 此时 j = 1, k = 0
     * <p>
     * 第二次循环:
     * 假设模式串为：aaab
     * if判断： sub[1] == sub[0] <=>  a == a <=> T
     * next[j = (1+1 = 2)] = (k = 0+1 = 1)
     * next[2] = 1 意思为当匹配到模式串j = 2 处失败时，j 回溯到 1，因为此时有长度为 1 的最大前后缀相等子串
     * <p>
     * 第二次循环:
     * 假设模式串为：abca
     * if判断： sub[1] == sub[0] <=>  b == a <=> F
     * k = next[k = 0] = -1;
     * 则第三次循环且通过if语句判断为True进入代码块
     * next [++j] = ++k <=> next[j = (1+1)] = (k = -1 + 1) <=> next[2] = 0;
     * <p>
     * <p>
     * 结论：通过数学归纳法分析，从索引等于 0 开始，如果满足 sub[j] = sub [k]
     * ，则下一项最大前后缀子串长度必定是当前最大前后缀子串长度+1，也就是 next[j+1] = k + 1;
     * 如果满足sub[j] = sub [k]，则相当于是从 [0 ~ k] 中找寻当第 k 号元素匹配失败时
     * ，k 需要回溯到的位置，也就是说可以看成是 子串[0~k] 中与主串[j-k+1~j] 当 sub[k] != main[j]时
     * k 应该需要回溯到哪的模式匹配问题
     *
     * @param sub
     * @return int[]
     * @author tqyao
     * @create 2022/10/11 14:44
     */
    public static int[] getNext02(char[] sub) {
        int[] next = new int[sub.length];
        // 当子串第一个字符就匹配失败时，此时主串与子串都前进一位（++i;++i），为了代码便利设置成-1
        // ，执行（++i;++i）时 j = 0
        next[0] = -1;
        int j = 0;
        int k = next[j];
        // 为每个字符匹配失败时都设置好其应该回溯到的索引，由于每次都是为下一项设置
        // ，所以循环终止条件 j>=sub.length - 1
        while (j < sub.length - 1) {
            /**
             * P[j] == P[k] => next[j + 1] = k +1;
             * 证明: 因为有 next[j] = k;
             * 所以 [P0~Pk-1)] = [Pj-k~Pj-1]
             * 因为 P[j] == P[k]
             * 即 [P0~Pk] = [Pj-k~Pj]
             * 即 next[j + 1] = k + 1 = next[j] + 1;
             */
            if (k == -1 || sub[j] == sub[k]) {
                next[++j] = ++k;
            } else
                // 当不满足if，目前最大前后缀子串长度不再是原有最大子串长度+1
                // ，而是在 [0~k] 做为模式串与 [j-k+1~j] 作为主串当 sub[k] 与 main[j] 匹配失败时
                // ，查找 k 应该回溯到的位置
                k = next[k];
        }
        return next;
    }


    @Test
    public void test01() {
        int[] arr = new int[3];
        int i = 0;
        arr[0] = 2;
        System.out.println(Arrays.toString(arr));
        System.out.println(arr[i]);
        arr[i++] = arr[i];
        System.out.println(Arrays.toString(arr));
    }


    @Test
    public void test02() {
        int[] arr = new int[3];
        int i = 0;
        arr[0] = 2;
        System.out.println(Arrays.toString(arr));

        int[] arr02 = new int[3];
        System.out.println(Arrays.toString(arr02));
        // todo:???
        arr02[i++] = arr[i];
        System.out.println(Arrays.toString(arr02));
        System.out.println(i);

    }

    @Test
    public void testGetNextVal() {
        int[] next02 = getNext02(sub);
        System.out.println(Arrays.toString(next02));
        System.out.println(Arrays.toString(getNextVal(sub, next02)));
    }

    @Test
    public void testKmpOfNextval() {
        String ms = new String(main);
        String ss = new String(sub);
        int idx01 = ms.indexOf(ss);
        int idx02 = kmpIndex02(main, sub, getNextVal(sub, getNext(sub)));
        System.out.println(idx01);
        System.out.println(idx02);
        Assert.assertEquals(idx01, idx02);
    }


    /**
     * next 数组优化
     * 得出next数据后进行优化（实际上不应该这样）
     *
     * @param sub
     * @param next
     * @return int[]
     * @author tqyao
     * @create 2022/10/11 21:21
     */
    public static int[] getNextVal(char[] sub, int[] next) {
        int[] nextval = new int[next.length];
        nextval[0] = -1;
        int i, j;
        i = 0;
        while (i < sub.length) {
            j = next[i];
            if (j == -1 || sub[i] != sub[j]) nextval[i] = next[i];
            else nextval[i] = nextval[j];
            i++;
        }
        return nextval;
    }

    public static int kmpIndex03(char[] main, char[] sub, int[] next) {
        int i, j;
        i = j = 0;
        while (i < main.length && j < sub.length) {
            if (j == -1 || main[i] == sub[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j >= sub.length) return i - sub.length;
        return -1;
    }

    public static int[] getNext03(char[] sub) {
        int[] next = new int[sub.length];
        next[0] = -1;
        int j = 0;
        int k = next[j];
        while (j < sub.length - 1) {
            if (k == -1 || sub[j] == sub[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        return null;
    }


    static String sm;
    static String ss;

    @Before
    public  void before() {
        System.out.println("PatternMatchingKMPSample.before");
        sm = new String(main);
        ss = new String(sub);
    }

    @Test
    public void testKmpGetNextVal() {
        System.out.println("sm.indexOf(ss) = " + sm.indexOf(ss));
        int[] nextVal = getNextVal(sub);
        System.out.println(Arrays.toString(nextVal));
        System.out.println("kmpIndex03(main,sub,getNextVal(sub)) = " + kmpIndex03(main, sub, nextVal));
    }


    /**
     * 对获取 next 数组的优化（方法更名getNextval）
     *
     * @param sub
     * @return int[]
     * @author tqyao
     * @create 2022/10/12 09:14
     */
    public static int[] getNextVal(char[] sub) {
        int[] nextval = new int[sub.length];
        nextval[0] = -1;
        int i, j;
        i = 0;
        j = nextval[i];
        while (i < sub.length - 1) {
            if (j == -1 || sub[i] == sub[j]) {
//                nextval[++i] = ++j;
                // 回溯到的位序对应字符与开始匹配失败处的字符相等
                // ，由于nextval数组是递推求出每一项，所以在匹配失败前的每一项都满足
                // 该项（该位序）匹配失败后应该回溯到位序，此位序的字符一定是和匹配失败处的字符是不相等的
                if (sub[++i] == sub[++j]) nextval[i] = nextval[j];
                    // 上面if判断是对求next数组的优化，else则是原先next数组处理
                    // 与现在的if语句处理结合 即：当sub[i] == sub[j]时
                    // ，最大前后缀子串 [sub0 ~ subj] = [sub(i-j) ~ sub(i)] (此处假设i和j进行if语句的自增)
                    // 得出 j 应该回溯到的位序是此时的 j + 1，当进入else分支代表该位序上的字符与原始匹配失败
                    // 处的字符 i + 1 是不相等的
                else nextval[i] = j;
            } else
                j = nextval[j];

        }
        return nextval;
    }

}
