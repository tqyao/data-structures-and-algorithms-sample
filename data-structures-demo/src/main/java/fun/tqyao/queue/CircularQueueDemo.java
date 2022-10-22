package fun.tqyao.queue;

import java.util.Scanner;

public class CircularQueueDemo {
    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue (3);
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
                        System.out.println ("成功！头部数据：" + queue.getHead ());
                    } catch (Exception e) {
                        System.out.println (e.getMessage ());
                    }
            }
        }
        System.out.println ("退出成功！");
    }
}

class CircularQueue {

    private int[] queue;
    private int front, real, maxSize;

    public CircularQueue(int maxSize) {
        this.maxSize = maxSize;
        queue = new int[maxSize];
        //front：表示队首，始终指向队列中第一个元素的位置（当队列为空时，front指向索引为0的位置）
        //real：表示对尾，始终指向队列中的最后一个元素的下一个位置
        front = real = 0;
    }

    public boolean isEmpty() {
        return front == real;
    }

    /**
     * 始终会浪费一个数组空间 以区分队列为空和队列为满的状态条件
     * @return
     */
    public boolean isFull() {
        return (real + 1) % maxSize == front;
    }

    public void addQueue(int el) {
        if (isFull ()) {
            System.out.println ("队列满了，无法添加");
            return;
        }
        queue[real] = el;
        real = (real + 1) % maxSize;
        System.out.println ("添加成功！");
    }

    public int getQueue() {
        if (isEmpty ()) {
            throw new RuntimeException ("队列为空，无法取出数据");
        }
        int temp = queue[front];
        front = (front + 1) % maxSize;
        return temp;
    }

    public void showQueue() {
        if (isEmpty ()) {
            System.out.println ("队列是空的！");
            return;
        }
        for (int i = front; i < getAvailableLength (); i++) {
            System.out.printf ("queue[%d]=%d", (front + 1) % maxSize, queue[(front + 1) % maxSize]);
        }
        System.out.println ();
    }

    public int getHead() {
        if (isEmpty ()) {
            throw new RuntimeException ("队列为空！无法查看头数据");
        }
        return queue[front];
    }

    public int getAvailableLength() {
        return (real + maxSize - front) % maxSize;
    }
}