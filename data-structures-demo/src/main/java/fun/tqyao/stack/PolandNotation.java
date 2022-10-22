package fun.tqyao.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class PolandNotation {
    public static void main(String[] args) {

        // 先定义一个你波兰表达式
        // （3+4）*5-6 = > 3 4 + 5 * 6 -
        String suffixExpression = "3 4 + 5 * 6 -";

        // 思路
        //1. 先将表达式放入 ArrayList 中
        //2. 再把list传递给一个方法，配合栈完成计算

        List<String> rpn = getListString (suffixExpression);
        System.out.println ("rpn => " + rpn);

        int result = getCalResult (rpn);
        System.out.println (result);
    }

    private static int getCalResult(List<String> rpn) {
        Stack<String> stack = new Stack<> ();
        for (String str : rpn) {
            if (str.matches ("\\d+")) {  // 是多位数
                stack.push (str);
            } else {
                int i1 = Integer.parseInt (stack.pop ());
                int i2 = Integer.parseInt (stack.pop ());
                stack.push (String.valueOf (cal (i2, i1, str)));
            }
        }
        return Integer.parseInt (stack.pop ());
    }

    private static int cal(int i2, int i1, String operator) {
        if (operator.equals ("+")) {
            return i2 + i1;
        } else if (operator.equals ("-")) {
            return i2 - i1;
        } else if (operator.equals ("*")) {
            return i2 * i1;
        } else if (operator.equals ("/")) {
            return i2 / i1;
        }
        throw new RuntimeException ("无法识别操作符");
    }

    private static List<String> getListString(String suffixExpression) {
        suffixExpression = suffixExpression.trim ();
        String[] str = suffixExpression.split (" ");
        return new ArrayList<> (Arrays.asList (str));
    }
}
