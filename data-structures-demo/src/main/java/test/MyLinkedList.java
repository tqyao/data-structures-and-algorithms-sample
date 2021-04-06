package test;

class MyLinkedList {

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList ();
        myLinkedList.addAtHead (777);
        myLinkedList.addAtTail (888);
        myLinkedList.addAtTail (999);


    }

    private class ListNode {
        int val;
        ListNode next;
        ListNode prev;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }

    private int size;
    private ListNode dummyHead, dummyTail;

    public MyLinkedList() {
        size = 0;
        dummyHead = new ListNode (0);
        dummyTail = new ListNode (0);
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
        ListNode prevNode = getPrevNode (index);
        return prevNode.next.val;
    }

    public void addAtHead(int val) {
        addAtIndex (0, val);
    }

    public void addAtTail(int val) {
        addAtIndex (size, val);
    }

    public void addAtIndex(int index, int val) {
        if (index > size) {
            return;
        }
        if (index < 0) {
            index = 0;
        }
        ListNode prevNode = getPrevNode (index);
        ListNode newNode = new ListNode (val);
        newNode.next = prevNode.next;
        prevNode.next.prev = newNode;
        newNode.prev = prevNode;
        prevNode.next = newNode;
        size++;
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        ListNode prevNode = getPrevNode (index);
        prevNode.next = prevNode.next.next;
        prevNode.next.prev = prevNode;
        size--;
    }

    private ListNode getPrevNode(int index) {
        ListNode prevNode = dummyHead;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                prevNode = prevNode.next;
            }
        } else {
            prevNode = dummyTail;
            for (int i = 0; i <= size - index; i++) {
                prevNode = prevNode.prev;
            }
        }
        return prevNode;
    }
}
