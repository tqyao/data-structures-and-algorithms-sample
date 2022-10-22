package fun.tqyao.queue;

import java.util.Scanner;

public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue (3);
        char key = ' ';
        Scanner scanner = new Scanner (System.in);
        boolean loop = true;

        while (loop) {
            System.out.println ("----------------------------------");
            System.out.println ("s(show):显示队列");
            System.out.println ("e(exit):退出队列");
            System.out.println ("a(add):添加队列");
            System.out.println ("g(get):从队列取出数据");
            System.out.println ("h(head):查看队列头部");
            System.out.println ("============请输入您的操作==========");
            key = scanner.next ().charAt (0);
            switch (key) {
                case 's':
                    queue.showQueue ();
                    break;
                case 'e':
                    loop = false;
                    break;
                case 'a':
                    System.out.print ("请输入添加到队列的数字:");
                    int el = scanner.nextInt ();
                    queue.addQueue (el);
                    break;
                case 'g':
                    try {
                        System.out.println ("成功！取出数据：" + queue.getQueue ());
                    } catch (Exception e) {
                        System.out.println (e.getMessage ());
                    }
                    break;
                case 'h':
                    try {
                        System.out.println ("成功！头部数据：" +queue.getHead ());
                    } catch (Exception e) {
                        System.out.println (e.getMessage ());
                    }
            }
        }
        System.out.println ("退出成功！");
    }
}

class ArrayQueue {
    //队列容量
    private int maxSize;
    //队列头
    private int front;
    //队列尾
    private int real;
    //模拟队列，存放数据
    private int[] queue;

    public ArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new int[maxSize];
        // 队列头，指向队列头的前的一个位置
        front = -1;
        // 队列尾，指向队列最后一个元素（包含）
        real = -1;
    }

    /**
     * 当 font = real 时，队列为空
     *
     * @return
     */
    public boolean isEmpty() {
        return front == real;
    }

    /**
     * 当 real = maxSize -1 时，队列满
     *
     * @return
     */
    public boolean isFull() {
        return real == maxSize - 1;
    }

    /**
     * 当队列不是Full时，可以添加
     *
     * @param elements
     */
    public void addQueue(int elements) {
        if (isFull ()) {
            System.out.println ("队列满了，无法添加");
            return;
        }
        real++;
        queue[real] = elements;
    }

    /**
     *
     * @return
     */
    public int getQueue() {
        if (isEmpty ()) {
            throw new RuntimeException ("队列为空，无法移除");
        }
        front++;
        return queue[front];
    }

    /**
     *
     * @return
     */
    public int getHead() {
        if (isEmpty ()) {
            throw new RuntimeException ("队列为空，无法获取头元素");
        }
        return queue[front + 1];
    }

    /**
     *
     */
    public void showQueue() {
        if (isEmpty ()) {
            System.out.println ("队列为空，没有数据~~");
        }
        for (int i = 0; i < queue.length; i++) {
            System.out.printf ("queue[%d]=%d\n", i, queue[i]);
        }
    }
}