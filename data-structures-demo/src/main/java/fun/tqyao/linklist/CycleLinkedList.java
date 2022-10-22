package fun.tqyao.linklist;

import java.util.Arrays;

/**
 * http://www.cxyzjd.com/article/qq_42435377/107475525
 * 约瑟夫问题
 * 单项环形链表
 */
public class CycleLinkedList {

    public static void main(String[] args) {
        CycleLinkedList cycleLinkedList = new CycleLinkedList (5);
        cycleLinkedList.foreach ();
        System.out.println ();
        cycleLinkedList.josephu (2,3);

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
     * @param startNo    从第几个开始
     * @param intervalNo 间隔，数m个出圈
     */
    public void josephu(int startNo, int intervalNo) {

        if (first == null) {
            System.out.println ("链表为空");
            return;
        }

        if (startNo < 0 || intervalNo < 0) {
            System.out.println ("k, m 不能小于0");
            return;
        }

        // 存储环链出队数据
        int[] array = new int[length];
        // 如果环长4，startNo = 5 ，可以看作从第1开始
        startNo = startNo % length;

        // 找到人为意义的尾节点（实际是找到first 指针的前一个节点）
        Node temp = first;
        while (temp.next != first) {
            temp = temp.next;
        }

        // 从第 startNo 开始数
        for (int i = 1; i < startNo; i++) {
            temp = temp.next;
            first = first.next;
        }


        // 环链表有多长就数多少次，目的是全部节点出圈
        for (int i = 0; i < length; i++) {
            // 每数一次 first、temp向后移动 intervalNo 个跨度
            for (int j = 1; j < intervalNo; j++) {
                first = first.next;
                temp = temp.next;
            }
            array[i] = (int) first.data;
            temp.next = first.next;
            first = first.next;
        }

        Arrays.stream (array).forEach (System.out::println);


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
