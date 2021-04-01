package linklist;

import java.util.Stack;

/**
 * 单项列表
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList ();
        singleLinkedList.add (new HeroNode (1, "宋江", "及时雨"));
        singleLinkedList.add (new HeroNode (3, "吴用", "智多星"));
        singleLinkedList.add (new HeroNode (2, "卢俊义", "玉麒麟"));
        singleLinkedList.add (new HeroNode (4, "林冲", "豹子头"));

//        singleLinkedList.addByOrder (new HeroNode (1, "宋江", "及时雨"));
//        singleLinkedList.addByOrder (new HeroNode (4, "林冲", "豹子头"));
//        singleLinkedList.addByOrder (new HeroNode (2, "卢俊义", "玉麒麟"));
//        singleLinkedList.addByOrder (new HeroNode (3, "吴用", "智多星"));

        singleLinkedList.update (new HeroNode (4, "关羽", "美髯公"));
        singleLinkedList.update (new HeroNode (5, "张飞", "阉人"));

//        singleLinkedList.delete (1);
        singleLinkedList.foreach ();

//        System.out.println (getLength (new HeroNode (1, "宋江", "及时雨")));

        HeroNode head = singleLinkedList.getHead ();
        System.out.println ("有效节点的个数：" + getLength (head));

        System.out.println ("倒数第3个元素：" + findLastNode (head, 3));

        HeroNode reverse = reverse (head);
        SingleLinkedList reverseList = new SingleLinkedList ();
        reverseList.setHead (reverse);
        reverseList.foreach ();

        System.out.println ();
        reverseForeach(reverse);
    }

//    /**
//     * 获取单链表的有效节点个数（如果是带头节点的链表,不统计头节点）
//     * @param heroNode
//     * @return
//     */
//    public static int getLength(HeroNode heroNode){
//        int count = 1;
//        String name = heroNode.name;
//        if (name == null || "".equals (name)) {
//            count--;
//        }
//        HeroNode temp = heroNode.next;
//        while (temp != null) {
//            temp = temp.next;
//            count++;
//        }
//        return count;
//    }

    /**
     * 查找单链表中倒数第k个节点
     * 1.遍历求出链表的长度 length
     * 2.length - k 求出所需节点正序号
     * 3.遍历该正序号
     *
     * @param node
     * @param turnNo
     * @return
     */
    public static HeroNode findLastNode(HeroNode node, int turnNo) {
        if (node.next == null) {
            return null;
        }
        int length = getLength (node);
        if (turnNo < 0 || turnNo > length) {
            return null;
        }
        HeroNode cur = node.next;
        for (int i = 0; i < length - turnNo; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 获取单链表的有效节点个数（如果是带头节点的链表,不统计头节点）
     *
     * @param head 链表的头节点
     * @return
     */
    public static int getLength(HeroNode head) {
        int count = 0;
        HeroNode cur = head.next;
        while (cur != null) {
            count++;
            cur = cur.next;
        }
        return count;
    }

    /**
     * 反转链表
     *
     * @param node
     * @return
     */
    public static HeroNode reverse(HeroNode node) {
        //如果原链表为空或是长度只有1，则直接返回原链表
        if (node.next == null || node.next.next == null) {
            return node;
        }
        // 辅助遍历指针
        HeroNode cur = node.next;
        // 指向当前节点（cur）的下一个节点
        HeroNode next = null;
        // 新链表
        HeroNode newNode = new HeroNode (0, "", "");

        while (true) {
            if (cur == null) {
                break;
            }
            // 记录原链表下一个节点的链表结构 迭代
            next = cur.next;
            // 原链表的当前节点的下一个节点（next）指向新链表的头部节点（除首节点的一下个节点）
            cur.next = newNode.next;
            // 新链表的头部节点的下一个节点（除首节点的下一个节点）再指向原链表的当前节点
            newNode.next = cur;
            //迭代
            cur = next;
        }
        node.next = newNode.next;
        return node;
    }

    /**
     * 从尾到头打印单链表
     */
    public static void reverseForeach(HeroNode node) {
        HeroNode cur = node.next;
        Stack<HeroNode> stack = new Stack<> ();
        while (cur != null) {
            stack.push (cur);
            cur = cur.next;
        }
        int size = stack.size ();
        for (int i = 0; i <size ; i++) {
            System.out.println (stack.pop ());
        }
    }

    /**
     * 合并两个有序的单项列表，合并之后链表依然有序
     * @return
     */
    public static HeroNode mergeLinkedList (HeroNode node){

        return null;
    }
}

class SingleLinkedList {


    //不存放数据，只用来存放单链表的头
    private HeroNode head = new HeroNode (0, "", "");

    public void setHead(HeroNode head) {
        this.head = head;
    }

    public HeroNode getHead() {
        return head;
    }

    /**
     * 1.由于head节点不可变更，声明一个变量来指向头节点
     * 2.循环查找temp节点的下一个节点是否存在，存在则移动temp至该节点 \
     * 不存在说明该temp.next 就是添加新节点的位置
     *
     * @param heroNode
     */
    public void add(HeroNode heroNode) {
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = heroNode;
    }

    /**
     * 删除节点
     * 1.遍历找到要删除节点的前一个节点temp
     * 2.删除节点的上一个节点.next = 删除节点.next
     */
    public void delete(int no) {
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                System.out.println ("无法删除，没有找到要删除节点的no:" + no);
                break;
            }
            if (temp.next.no == no) {// 找到待删除节点的前一个节点
                temp.next = temp.next.next;
                break;
            }
            temp = temp.next;
        }

    }

    /**
     * 修改节点信息
     * 根据no编号来修改，即no编号不能改
     *
     * @param heroNode
     */
    public void update(HeroNode heroNode) {
        // 头节点不能改变，借助临时节点来遍历列表
        HeroNode temp = head.next;
//        // 代表是否找到id等于要修改的节点相等的节点位置
//        boolean flag  = false;
        while (true) {
            if (temp == null) {
                System.out.println ("无发修改节点信息,链表不存在该no值节点:" + heroNode.no);
                break;
            }
            if (temp.no == heroNode.no) {
                //找到需要修改的节点
                temp.name = heroNode.name;
                temp.nickname = heroNode.nickname;
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 顺序添加
     * 1.找到待添加节点的上一个节点temp（通过辅助变量（指针）遍历）
     * 2.新节点.next = temp.next ; temp.next = 新节点
     *
     * @param heroNode
     */
    public void addByOrder(HeroNode heroNode) {
        // 由于head 不能移动，创建辅助变量temp
        HeroNode temp = head;
        // 代表是否是no重复
        boolean flag = false;
        //遍历节点寻找新节点位置
        while (true) {
            if (temp.next == null) {
                //如果next节点为空，原链表为空，直接添加
                break;
            } else if (temp.next.no == heroNode.no) {
                flag = true;
            } else if (temp.next.no > heroNode.no) {
                // 找到添加的元素要插入节点的位置
                break;
            }
            //继续判断下一个节点是否满足上面条件
            temp = temp.next;
        }

        if (flag) {
            System.out.println ("无法添加，该节点序号已经存在链表中");
        } else {
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    /**
     * 查看节点所有元素
     * 1.由于head节点不可变更，声明一个变量temp来指向头节点的下一个节点
     * 2.循环检查temp节点是否是空，空代表链表末尾结束循环，\
     * 非空则打印链表信息，移动temp节点指向下一个节点
     */
    public void foreach() {
        HeroNode temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println (temp);
            temp = temp.next;
        }
    }


}

class HeroNode {

    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
