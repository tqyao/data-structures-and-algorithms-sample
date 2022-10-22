package fun.tqyao.stack;

/**
 * 单项列表实现栈
 */
public class LinkedStack {

    public static void main(String[] args) {
        LinkedStack linkedStack = new LinkedStack ();
        linkedStack.push (111);
        linkedStack.push (222);
        linkedStack.push (333);
        linkedStack.foreach ();
        System.out.println ();
        System.out.println (linkedStack.pop ());
        System.out.println (linkedStack.pop ());
        System.out.println (linkedStack.pop ());
        System.out.println (linkedStack.pop ());

    }


    Node head;

    int size;

    public LinkedStack() {
        //头节点
        this.head = new Node (-1);
        // 栈容量
        size = 0;
    }


    public boolean isEmpty() {
        return head.next == null || size == 0;
    }

    /**
     * 入栈：
     * 头插法
     *
     * @param val
     */
    public void push(int val) {
        Node insertNode = new Node (val);
        insertNode.next = head.next;
        head.next = insertNode;
        size++;
    }

    /**
     * 出栈
     */
    public int pop() {
        if (isEmpty ()) {
            throw new RuntimeException ("栈空，无法出栈");
        }
        int data = head.next.data;
        head.next = head.next.next;
        return data;
    }

    public void foreach() {
        Node temp = head.next;
        while (temp != null) {
            System.out.print (temp.data + "\t");
            temp = temp.next;
        }
    }

    private class Node {
        int data;
        Node next;

        private Node(int data) {
            this.data = data;
        }
    }


}
