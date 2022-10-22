/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.string;


import org.junit.Test;

import java.util.Arrays;

/**
 * 静态顺序串
 *
 * @author tqyao
 * @version 1.0.0
 * @create 2022/10/04 16:13
 */
public class SeqStringSample {
//    public static void main(String[] args) {
//
//        SeqString s1 = new SeqString("I Love You".toCharArray());
//        System.out.println(s1);
//        System.out.println(s1.subString(0, 6));
//
//        SeqString s2 = new SeqString("我爱你".toCharArray());
//        System.out.println(s2);
//
//        System.out.println(s1.concat(s2));
//
//
//
//    }

    @Test
    public void indexTest() {
        String str = "i love you";
        String sub = "";
//        String sub = "u";

        SeqString ss1 = new SeqString(str.toCharArray());
        SeqString ss2 = new SeqString(sub.toCharArray());
        System.out.println(ss1.indexOf(ss2));
        System.out.format("%s indexOf %s = %d", Arrays.toString(str.toCharArray()),sub,str.indexOf(sub));
    }

    @Test
    public void compareTest() {
        SeqString ss1 = new SeqString("i love you".toCharArray());
        SeqString ss2 = new SeqString("i love you".toCharArray());
//        System.out.println(ss1.strCompare(ss2));


        ss1.setValues("aacde".toCharArray());
        ss2.setValues("accd".toCharArray());
        System.out.println(ss1.strCompare(ss2));


    }

    @Test
    public void concatTest2() {
        SeqString s1 = new SeqString("hello".toCharArray());
        System.out.println(s1.concat(new SeqString(new char[10])));
    }

    @Test
    public void concatTest() {
        SeqString ss1 = new SeqString("i love you".toCharArray());
        SeqString ss2 = new SeqString("我爱你".toCharArray());
        System.out.println(ss1.concat(ss2));
    }


    static class SeqString {

        private SeqString() {
            this((char[]) null);
        }

        public SeqString(char[] values) {
            init(values);
        }

        private void init(char[] chars) {
            values = null == chars ? new char[255] : chars;
            size = getChsSize(values);
        }

        public static int getChsSize(char[] chs) {
            int size = 0;
            for (int i = 0; i < chs.length; i++) {
                if (chs[i] == 0) break;
                size++;
            }
            return size;
        }

        public void setValues(char[] values) {
            init(values);
        }

        public SeqString(SeqString s) {
            this.values = s.values;
        }

        private char[] values;
        private int size;


        public int getLen() {
            return size;
        }

        public void strAssign(char[] values) {
            this.values = values;
        }

        public void strCopy(SeqString s) {
            values = s.values;
        }

        public boolean isEmpty() {
            return getLen() == 0;
        }

        /**
         * 返回子串在主串中第一次出现的索引
         * @author tqyao
         * @create 2022/10/5 11:41
         * @param ss
         * @return int
         */
        public int indexOf(SeqString ss) {
            if (ss.isEmpty() || ss.size > size) {
                return -1;
            }
            // 循环到第idx个索引为止，可以少循环子串长度次
            int idx = size - ss.size;
            SeqString sub;
            for (int i = 0; i <= idx; i++) {
//                if (0 == subString(i, ss.size).strCompare(ss)) {
                sub = subString(i, ss.size);
                if (0 == sub.strCompare(ss)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 比较是否相等
         *
         * @param ss
         * @return int
         * @author tqyao
         * @create 2022/10/5 10:29
         */
        public int strCompare(SeqString ss) {
            if (null == values && null == ss) {
                return 0;
            }
            if (null == values) {
                return -1;
            }
            if (null == ss.values) {
                return 1;
            }

            int curIdx = 0;
            int compare;
            while (curIdx < size && curIdx < ss.size) {
                compare = values[curIdx] - ss.values[curIdx];
                if (compare != 0) return compare;
                curIdx++;
            }
            return size - ss.size;
        }


        /**
         * 拼接
         *
         * @param ss
         * @return string.SeqStringSample.SeqString
         * @author tqyao
         * @create 2022/10/5 10:29
         */
        public SeqString concat(SeqString ss) {
            if (null == ss) {
                return this;
            }
            if (values == null) {
                return ss;
            }

            char[] catChs = new char[ss.getLen() + getLen()];
            int cur = 0;
            for (int i = 0; i < getLen(); i++) {
                if (values[i] == 0) break;
                catChs[cur++] = values[i];
            }
            for (int i = 0; i < ss.getLen(); i++) {
                if (ss.values[i] == 0) break;
                catChs[cur++] = ss.values[i];
            }
            return new SeqString(catChs);
        }

        /**
         * @param pos 起始索引
         * @param len 截取长度
         * @return string.SeqStringSample.SeqString
         * @author tqyao
         * @create 2022/10/5 10:28
         */
        public SeqString subString(int pos, int len) {
            SeqString ss = new SeqString();
            if (pos >= getLen() || pos < 0) {
                return ss;
            }
            // 计算pos到字符数组末尾长度
            if (len > getLen()) {
                len = getLen() - pos - 1;
            }
            char[] chs = new char[len];
            int cur = 0;
            for (int i = len; i > 0; i--) {
                chs[cur++] = values[pos++];
            }
            ss.values = chs;
            ss.size = len;
            return ss;
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (char c : values) {
                sb.append(c);
            }
            return sb.toString();
        }

    }
}
