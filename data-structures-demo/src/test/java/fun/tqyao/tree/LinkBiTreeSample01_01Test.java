package fun.tqyao.tree;

import org.junit.BeforeClass;
import fun.tqyao.tree.LinkBiTreeSample01_01.*;
import org.junit.Test;

import java.util.Arrays;

//import static fun.tqyao.tree.LinkBiTreeSample01_01.createCompleteBinaryTreeRecursion;
//import static fun.tqyao.tree.LinkBiTreeSample01_01.levelOrder;
import static org.junit.Assert.*;

public class LinkBiTreeSample01_01Test {

    static BitNode<String> root;

    @BeforeClass
    public static void beforeClass() {
//        String[] strings = "A,B,C,D,E,F,G,H,I,J".split(",");
        String[] strings = "A,B,C".split(",");
        System.out.println("原> " + Arrays.toString(strings));
        root = LinkBiTreeSample01_01.createCompleteBinaryTreeRecursion(strings, 0);
    }

    @Test
    public void testLevelOrder() {
        System.out.println(LinkBiTreeSample01_01.levelOrder(root));
    }

}