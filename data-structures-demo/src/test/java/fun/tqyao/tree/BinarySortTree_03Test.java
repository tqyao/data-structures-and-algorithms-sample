package fun.tqyao.tree;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BinarySortTree_03Test {

    @Test
    public void testCompareTo() {
        System.out.println("A".compareTo("b"));
        System.out.println("A".compareTo("A"));
        Object o = "asda";
    }

    static BinarySortTree_03 bstree;


    @BeforeClass
    public static void beforeClass() {
//        int[] nodeVals = Arrays.stream("1,2,3,4,5,6".split(",")).mapToInt(Integer::parseInt).toArray();
//        int[] nodeVals = Arrays.stream("53,17,78,9,45,65,78,87,23".split(",")).mapToInt(Integer::parseInt).toArray();
        int[] nodeVals = Arrays.stream("53,17,78,9,45,65,94,23,81,88".split(",")).mapToInt(Integer::parseInt).toArray();
        System.out.printf("nodevals = %s\n", Arrays.toString(nodeVals));
        System.out.printf("nodeVals.length = %d\n", nodeVals.length);

        bstree = new BinarySortTree_03();
        bstree.createBSTByRecursion(nodeVals);
        List<Integer> orderList = bstree.inOrderTraverse();
        System.out.println(orderList.toString());
        System.out.printf("orderList size = %d\n", orderList.size());

//        System.out.println(">>>>>>>>>>>>>>>");
//        BinarySortTree_04 bstree2 = new BinarySortTree_04();
//        bstree2.createBSTByTraverse(nodeVals);
//        List<Integer> orderList2 = bstree2.inOrderTraverse();
//        System.out.println(orderList2.toString());
//        System.out.printf("orderList2 size = %d\n",orderList2.size());
    }

    @Test
    public void testRemove() {
        System.out.println("remove >>>>>");
//        bstree.remove(87);
//        System.out.println(bstree.inOrderTraverse());

        bstree.remove(78);
        System.out.println(bstree.inOrderTraverse());
        Optional.ofNullable(bstree.searchNodeTraverse(78)).ifPresent(System.out::println);
        Optional.ofNullable(bstree.searchNodeTraverse(81)).ifPresent(System.out::println);

        System.out.println("//////////////////");
        Optional.ofNullable(bstree.searchNodeTraverse(94))
                .map(node -> node.rchild).ifPresent(System.out::println);

        System.out.println("||||||||||||||||||||");
        Optional.ofNullable(bstree.searchNodeTraverse(94))
                .map(node -> node.lchild).ifPresent(System.out::println);
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\");

    }

    @Test
    public void testSearchNode() {
        // 左右孩子存在
        System.out.println(bstree.searchNodeTraverse(53));
        System.out.println(bstree.searchNodeRecursion(bstree.root, 53));

        // 右孩子空
        System.out.println(bstree.searchNodeTraverse(45));
        System.out.println(bstree.searchNodeRecursion(bstree.root, 45));

        // 不存在的节点值
        System.out.println("====");
        Optional.ofNullable(bstree.searchNodeTraverse(111)).ifPresent(System.out::println);
        Optional.ofNullable(bstree.searchNodeRecursion(bstree.root, 111)).ifPresent(System.out::println);
        System.out.println("====");
    }

    @Test
    public void testInOrder() {
        System.out.println(bstree.inOrderTraverse().toString());
        System.out.println(bstree.inOrderRecursion(bstree.root, null).toString());
    }


}