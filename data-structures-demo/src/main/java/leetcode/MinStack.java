package leetcode;

import java.util.Stack;

public class MinStack {
    int min = Integer.MAX_VALUE;
    Stack<Integer> stack = new Stack<> ();

    public void push(int x) {
        //如果加入的值小于最小值，要更新最小值
        if (x <= min) {
            stack.push(min);
            min = x;
        }
        stack.push(x);
    }

    public void pop() {
        //如果把最小值出栈了，就更新最小值
        if (stack.pop() == min)
            min = stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println (minStack.stack);
        System.out.println (minStack.getMin ());
        minStack.pop();
        System.out.println (minStack.top ());
        System.out.println (minStack.getMin ());
        System.out.println (minStack.stack);


    }
}
