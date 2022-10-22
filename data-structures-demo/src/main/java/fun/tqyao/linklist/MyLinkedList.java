package fun.tqyao.linklist;

/**
 * leetcode
 * 单项列表设计
 * https://leetcode-cn.com/leetbook/read/linked-list/jy291/
 */
class MyLinkedList {

    public static void main(String[] args) {



        MyLinkedList myLinkedList = new MyLinkedList ();
        myLinkedList.addAtTail (22);
        myLinkedList.addAtTail (33);
        myLinkedList.addAtTail (44);
        myLinkedList.addAtHead (55);

        System.out.println (myLinkedList.size);

        Node temp = myLinkedList.head;
        while (temp != null) {
            System.out.println (temp);
            temp = temp.next;
        }

        System.out.println ("head" + myLinkedList.head);
        System.out.println ("last" + myLinkedList.last);
        System.out.println (myLinkedList.last.next);

    }

    // 头结点指针
    private Node head;
    // 尾结点指针
    private Node last;
    // 链表实际长度
    private int size;

    // 自定义的节点结构
    private static class Node{
        int data;
        Node next;
        Node(int data){
            this.data = data;
        }


        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if (index < 0 || index >= size){
            return -1;
        }
        Node temp = head;
        for(int i = 0; i < index; i++){
            temp = temp.next;
        }
        // 返回节点值
        return temp.data;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        // 新建插入节点
        Node insertedNode = new Node(val);
        if (size == 0){
            // 空链表插入
            head = insertedNode;
            last = insertedNode;
        }else {
            // 插入头部
            insertedNode.next = head;
            head = insertedNode;
        }
        size++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        // 新建插入节点
        Node insertedNode = new Node(val);
        if (size == 0){
            // 空链表
            head = insertedNode;
            last = insertedNode;
        }else {
            // 插入尾部
            last.next = insertedNode;
            last = insertedNode;
        }
        size++;
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        // 新建插入节点
        Node insertedNode = new Node(val);
        if(size == 0){
            // 空链表
            head = insertedNode;
            last = insertedNode;
        }else if(index <= 0){
            // 插入头部
            insertedNode.next = head;
            head = insertedNode;
        }else if(index == size){
            // 插入尾部
            last.next = insertedNode;
            last = insertedNode;
        }else {
            // 插入中间
            Node prevNode = getNodePrev(index - 1);
            // 将新结点的 next指针指向原来旧结点的 next指针指向的内存地址
            insertedNode.next = prevNode.next;
            // 将原来旧结点的 next指针指向新结点的内存地址
            prevNode.next = insertedNode;
        }
        size++;
    }

    // 获取插入节点的前一个节点
    public Node getNodePrev(int index){
        if (index < 0 || index >= size){
            return null;
        }
        Node temp = head;
        for(int i = 0; i < index; i++){
            temp = temp.next;
        }
        return temp;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size){
            return;
        }
        Node removedNode = null;
        if (index == 0){
            // 删除头部节点
            removedNode = head;
            head = head.next;
        }else if (index == size-1){
            // 删除尾部节点
            Node prevNode = getNodePrev(index-1);
            removedNode = prevNode.next;
            prevNode.next = null;
            last = prevNode;
        }else {
            // 删除中间节点
            Node prevNode = getNodePrev(index-1);
            Node nextNode = prevNode.next.next;
            removedNode = prevNode.next;
            // 即 a>>b>>c，删除 b，只需要将 c 赋值给 a.next 即可
            prevNode.next = nextNode;
        }
        size--;
    }
}
