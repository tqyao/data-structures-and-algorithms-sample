package linklist;

/**
 * 双向列表
 */
public class DoubleLinkedListDemo {


    public static void main(String[] args) {
        DoubleListNode doubleListNode = new DoubleListNode ();
        doubleListNode.addAtHead (777);
        doubleListNode.addAtTail (888);
        doubleListNode.addAtTail (999);
        doubleListNode.addAtTail (1000);

        doubleListNode.addAtHead (555);

        doubleListNode.addAtIndex (2, 3121);
        doubleListNode.addAtIndex (-1, 1234);


        doubleListNode.foreach ();

        System.out.println ();
        System.out.println (doubleListNode.size);
        System.out.println (doubleListNode.getPrevNode (3));

        doubleListNode.deleteAtIndex (2);
        doubleListNode.foreach ();
        System.out.println (doubleListNode.size);
    }


    public static class DoubleListNode {

        //链表长度
        private int size;
        //链表头尾节点指针
        private ListNode head, tail;

        public DoubleListNode() {

        }

        //        /**
//         * Initialize your data structure here.
//         *
//         * @param head
//         * @param tail
//         */
//        public DoubleListNode(ListNode head, ListNode tail) {
//            this.head = head;
//            this.tail = tail;
//        }

        /**
         * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
         */
        public int get(int index) {
            if (index < 0 || index >= size) {
                return -1;
            }
            ListNode temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return (int) temp.data;
        }

        /**
         * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
         */
        public void addAtHead(int val) {
            ListNode insertNode;
            if (size == 0) {
                // 链表为空
                // 创建前驱后继都为空的节点
                insertNode = new ListNode (null, null, val);
                // 使头尾指针都指向新节点
                this.head = insertNode;
                this.tail = insertNode;
            } else {
                // 链表不为空
                // 创建前驱为空，后继为当前head节点，传入值val
                insertNode = new ListNode (null, head, val);
                // 当前head的前驱设置为新节点
                head.prev = insertNode;
                // 将新节点设置为头节点
                head = insertNode;
            }
            size++;
        }

        /**
         * Append a node of value val to the last element of the linked list.
         */
        public void addAtTail(int val) {
            ListNode insertNode;
            if (size == 0) {
                // 链表为空
                // 创建前驱后继都为空的节点
                insertNode = new ListNode (null, null, val);
                // 使头尾指针都指向新节点
                this.head = insertNode;
            } else {
                // 链表不为空
                // 创建前驱为当前tail节点的节点，后继为null，传入值val
                insertNode = new ListNode (tail, null, val);
                // 当前tail节点的后继设置为新节点
                tail.next = insertNode;
                // 将新节点设置为尾节点
            }
            this.tail = insertNode;
            size++;
        }

        /**
         * Add a node of value val before the index-th node in the linked list.
         * If index equals to the length of linked list, the node will be appended to the end of linked list.
         * If index is greater than the length, the node will not be inserted.
         */
        public void addAtIndex(int index, int val) {

            if (index > size) {
                System.out.println ("大于链表长度，无法插入");
                return;
            }

            if (index == size) {
                // index 等于当前列表长度,尾部添加
                addAtTail (val);
            }

            if (index >= 0 && index < size) {
                //index 属于当前链表长度范围内，在第index节点前添加一个新节点

                // 创建前驱后继为空的新节点
                ListNode insertNode = new ListNode (null, null, val);
                // 获取第index节点
                ListNode cur = getPrevNode (index);
                // 新节点指针指向index节点的下一个节点
                insertNode.next = cur.next;
                //index节点的下一个节点的上一个节点指针指向新节点
                cur.next.prev = insertNode;

                //新节点的上一个节点指针指向index节点
                insertNode.prev = cur;
                //index节点的下一个节点指针指向新节点
                cur.next = insertNode;
            }

            if (index < 0) {
                // index 小于0，头部添加
                addAtHead (val);
            }
            size++;

        }

        /**
         * 返回索引节点
         *
         * @param index
         * @return
         */
        private ListNode getPrevNode(int index) {
            if (index < 0 || index >= size) {
                return null;
            }
            ListNode temp;
            if (index < size / 2) {
                temp = head;
                for (int i = 0; i < index; i++) {
                    temp = temp.next;
                }
            } else {
                temp = tail;
                for (int i = size - 1; i > index; i--) {
                    temp = temp.prev;
                }
            }
            return temp;
        }


        /**
         * Delete the index-th node in the linked list, if the index is valid.
         */
        public void deleteAtIndex(int index) {
            if (index < 0 || index >= size) {
                System.out.println ("index非法，无法删除");
                return;
            }
            if (index == 0) {
                head = head.next;
                head.prev = null;
            }

            if (index == size - 1) {
                tail = tail.prev;
                tail.next = null;
            }

            if (index > 0 && index < size - 1) {
                ListNode cur = getPrevNode (index);
                cur.prev.next = cur.next;
                cur.next.prev = cur.prev;
            }
            size--;
        }

        public void foreach() {
            ListNode temp = head;
            for (int i = 0; i < size - 1; i++) {
                System.out.println (temp);
                temp = temp.next;
            }
        }


    }

    private static class ListNode<E> {
        E data;
        ListNode next;
        ListNode prev;

        ListNode(ListNode prev, ListNode next, E data) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }

        public ListNode(E val) {
            data = val;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "data=" + data +
                    '}';
        }
    }

}