package fun.tqyao.tree;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

@Slf4j
public class ThreadTree02_01Test {

    static ThreadTree02_01<String> theadTree;


    static String[] strings;

    @BeforeClass
    public static void beforeClass() {
        log.info("beforeClass ...");
//        strings = "A,B,C".split(",");
        strings = "A,B,C,D,E,F,G,H,I,J".split(",");
//        String[] strings = "1,2,3,0,0,0,4,5,6,7,8".split(",");
//        String[] strings = "1, 2, 0, 3, 4, 0, 0, 0, 5, 6, 0, 0, 7, 8, 9, 0, 0, 0, 0"
//                .replaceAll("\\s+", "").split(",");

        System.out.println("原> " + Arrays.toString(strings));
        theadTree = new ThreadTree02_01<>();
//        theadTree.createBinaryTree(strings);
//        theadTree.createCompleteTree(strings);
    }

    @Test
    public void testCreateCompleteTreeRecursion() {
        theadTree.createCompleteTreeRecursion(strings,0);
        System.out.println("层> " + theadTree.levelOrder());
    }

    @Test
    public void testCreateCompleteTree(){
        theadTree.createCompleteTree(strings);
        System.out.println("层> " + theadTree.levelOrder());
    }


    @Test
    public void testLevelOrder() {
        System.out.println("层> " + theadTree.levelOrder());
    }

}