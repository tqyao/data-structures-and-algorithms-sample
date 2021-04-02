package linklist;

public class DoubleLinkedListDemo {

}

class DoubleLinkedList {

    /**
     * 末尾添加元素
     * @param node
     */
    public void add(DoubleHeroNode node){

    }

}


class DoubleHeroNode {

    public int no;
    public String name;
    public String nickname;
    public HeroNode next;
    public HeroNode pre;

    public DoubleHeroNode(int no, String name, String nickname) {
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