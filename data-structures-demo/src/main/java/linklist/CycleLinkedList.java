package linklist;

/**
 * 约瑟夫问题
 * 单项环形链表
 */
public class CycleLinkedList {

    public static void main(String[] args) {
        CycleLinkedList cycleLinkedList = new CycleLinkedList (3);
        cycleLinkedList.foreach ();

    }

    // 环形链表约定的第一个节点
    Node first = null;
    // 环长
    int length;

    public CycleLinkedList(int length) {
        if (length < 0) {
            // 链表添加错误
            throw new RuntimeException ("链表初始化错误，length长度必须大于零");
        }
        this.length = length;

        Node cur = null;
        for (int i = 1; i <= length; i++) {
            Node node = new Node (i);
            if (i == 1) {
                //链表的第一个元素
                first = node;
                node.next = first;
                cur = first;
            } else {
                cur.next = node;
                node.next = first;
                cur = cur.next;
            }
        }
    }

    /**
     * 约瑟夫问题：设有n个人围成一圈，从第k个人开始报数m下，数到m的人出列，他的下一个人继续数，数到m的人出列…以此类推，
     * 直到所有人出列，要求 按出列的顺序用编号形成一个序列。
     *
     * @param k 从第几个开始
     * @param m 间隔，数m个出圈
     */
    public void josephu(int k, int m) {
        // 存储环链出队数据
        int[] array = new int[length - 1];

        Node cur = first;
        //计算从哪个节点开始数
        for (int i = 1; i < k; i++) {
            cur = cur.next;
        }



    }


    public void foreach() {
        Node temp = first;
        while (true) {
            System.out.println (temp);
            temp = temp.next;
            if (temp == first) {
                return;
            }
        }

    }

    private class Node<E> {
        E data;
        Node next;

        public Node(E data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }
}
