package fun.tqyao.leetcode;

public class Test1 {

    public static void main(String[] args) {

        ListNode node = new  ListNode (1);
        ListNode node2 = new  ListNode (2);
        node.next = node2;
        node2.next = node;

        System.out.println (detectCycle (node));

    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }

    public static ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            slow = slow.next;
            if (fast.next == null) {
                return null;
            } else {
                fast = fast.next.next;
            }

            if (slow != fast) {
                // 不相等，还未找到成环点
                continue;
            }

            ListNode temp = head;
//            if (temp != slow) {
//                while (true) {
//                    slow = slow.next;
//                    temp = temp.next;
//                    if (slow == temp) {
//                        //找到环入口
//                        return slow;
//                    }
//                }
//            }else {
//                return slow;
//            }

            while (slow != temp) {
                slow = slow.next;
                temp = temp.next;
                if (slow == temp) {
                    //找到环入口
                    return slow;
                }
            }
            return slow;
        }

        return null;
    }
}
