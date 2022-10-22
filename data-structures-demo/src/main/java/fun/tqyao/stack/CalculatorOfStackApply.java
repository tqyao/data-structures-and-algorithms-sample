package fun.tqyao.stack;

/**
 * todo:多位数运算结果出错
 * 使用栈实现计算器(中缀表达式)。
 * 实现思路：
 * 1. 通过 index （索引）遍历所给字符串的每个字符。
 * 2. 如果发现是数字就直接入栈
 * 3. 如果扫描到的是运算符，就分如下情况
 * a. 如果栈中为空，则直接入栈
 * b. 如果当前 index 指向操作符优先级大于栈中的操作符，则直接入符号栈；如果当前 index 指向操作符优先级小于
 * 等于栈中操作符则弹出（pop）数字的两个数字和字符栈中的一个运算符号进行运算，最后把运算结果存储进（push）入数字栈中
 * 4. 当表达式扫描完毕后，按顺序从数字和符号栈中pop出相应数字符号，进行运算。
 * 数字栈中剩余的最后一个数字就是运算结果。
 */
public class CalculatorOfStackApply {


    public ArrayStack getArrayStackInstance() {
        return new ArrayStack(10);
    }

    public static void main(String[] args) {
        CalculatorOfStackApply calculator = new CalculatorOfStackApply();

        //获取数字栈和符号栈实例
        ArrayStack numberStack = calculator.getArrayStackInstance();
        ArrayStack symbolStack = calculator.getArrayStackInstance();

//        String expression = "3+2*6-2";

        //30-2*6+2*4
        String expression = "30-2*6+1";


        //拼接多位数
        String joinStr = "";

//        int index = 0;
//        while (true) {
//            expression.substring(index, index + 1).charAt(0);
//        }

        char indexCh;
        int num1, num2, result;
        // 循环直到字符串结束
        for (int i = 0; i < expression.length(); i++) {
            // 获取字符串字符
            indexCh = expression.charAt(i);
            // 字符优先级
            int priority = calculator.getPriority(indexCh);
            if (priority == -1) { //true -> 是数字,加入数字栈
                //如果是数字，接着还要判断是否为多位数

                int k = i;
                joinStr += indexCh;
                if (++k == expression.length() || calculator.getPriority(expression.charAt(k)) != -1) {
                    //如果当前数字字符的下一位=表达式的长度，说明其下一位是空，可直接把当前数字压入数字栈，或者
                    //当前数字字符的下一位是非数字，也直接压入数字栈
//                        numberStack.push(Character.getNumericValue(indexCh));
                    numberStack.push(Integer.parseInt(joinStr));
                    // 清空拼接字符
                    joinStr = "";
                }

            } else {
                //如果是字符先判断符号栈是否为空
                if (symbolStack.top == -1) {
                    // 符号栈为空，直接入符号栈
                    symbolStack.push(indexCh);
                } else {
                    //符号栈不为空，出栈一次比较符号优先级
                    char syOut = (char) symbolStack.pop();
                    int outPriority = calculator.getPriority(syOut); //获取栈中符号优先级
                    if (priority <= outPriority) {
                        // 当前运算符优先级小于等于符号栈中的优先级，执行：
                        // a. 弹出数字栈中的两个数字和符号栈中的一个字符做运算；
                        // b. 把计算结果压入数字栈中
                        num1 = numberStack.pop();
                        num2 = numberStack.pop();
                        result = calculator.getCalResult(num1, num2, syOut);
                        numberStack.push(result);
                        // 最后还需把该运算符入栈
                        symbolStack.push(indexCh);
                    } else {
                        // 当前运算符大于栈中的优先级，直接入符号栈
                        symbolStack.push(syOut);  // 先将上一步出栈判断的符号入栈
                        symbolStack.push(indexCh);
                    }
                }

            }
        }

        // 字符扫描完毕，计算剩余栈中的数字运算符
        while (symbolStack.top != -1) {
            num1 = numberStack.pop();
            num2 = numberStack.pop();
            indexCh = (char) symbolStack.pop();
            result = calculator.getCalResult(num1, num2, indexCh);
            numberStack.push(result);
        }

        System.out.println(numberStack.pop());
    }


    public int getCalResult(int num1, int num2, char ch) {
        if (ch == '*') {
            return num1 * num2;
        }
        if (ch == '/') {
            return num2 / num1;
        }
        if (ch == '+') {
            return num1 + num2;
        }
        if (ch == '-') {
            return num2 - num1;
        }
        throw new RuntimeException("无法计算运算操作符");
    }

    /**
     * 判断所给字符是否有效运算符或是数字，并返回优先级
     *
     * @param ch
     * @return 1：乘除法，优先级最高 2：加减法  -1：数字
     */
    public int getPriority(char ch) {
        if (ch == '*' || ch == '/') {
            return 1;
        }
        if (ch == '+' || ch == '-') {
            return 0;
        }
        if (Character.isDigit(ch)) {
            return -1;
        }
        throw new IllegalArgumentException("不是一个合法的数字或运算符");
    }

    class ArrayStack {

        // 最大容量
        int maxSize;
        // 存储数据
        int[] stack;
        //栈顶
        int top;

        public ArrayStack(int maxSize) {
            this.maxSize = maxSize;
            stack = new int[maxSize];
            top = -1;
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public boolean isFull() {
            return top == maxSize - 1;
        }

        /**
         * 入栈
         *
         * @param val
         */
        public void push(int val) {
            if (isFull()) {
                System.out.println("栈满，无法入栈");
                return;
            }
            stack[++top] = val;
        }

        /**
         * 出栈
         *
         * @return
         */
        public int pop() {
            if (isEmpty()) {
                throw new RuntimeException("栈空，无法出栈");
            }
            return stack[top--];
        }

        public void foreach() {
            for (int i = top; i >= 0; i--) {
                System.out.printf("stack[%d] = %d\n", i, stack[i]);
            }
        }

    }
}
