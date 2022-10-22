package fun.tqyao.stack;

/**
 * 数组栈栈实现
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack (3);
        arrayStack.push (111);
        arrayStack.push (222);
        arrayStack.push (333);
        arrayStack.push (444);
        arrayStack.foreach ();
        System.out.println ();
        System.out.println (arrayStack.pop ());
        System.out.println (arrayStack.pop ());
    }
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
        if (isFull ()) {
            System.out.println ("栈满，无法入栈");
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
        if (isEmpty ()) {
            throw new RuntimeException ("栈空，无法出栈");
        }
        return stack[top--];
    }

    public void foreach() {
        for (int i = top; i >= 0; i--) {
            System.out.printf ("stack[%d] = %d\n", i, stack[i]);
        }
    }

}