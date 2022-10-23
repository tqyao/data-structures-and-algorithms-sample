package fun.tqyao.tree;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


@Slf4j
public class LinkBiTree01_03Test {


    static LinkBiTree01_03<String> biTree;

    @BeforeClass
    public static void beforeClass() throws Exception {
        log.info("beforeClass...");

        biTree = new LinkBiTree01_03<>();
        //随机节点数据
//        String[] strings = randomStringArray(10);
        String[] strings = "A,B,C,D,E,F,G,H,I,J".split(",");
        System.out.println("原> " + Arrays.toString(strings));
        biTree.createCompleteBiTree(strings);
        System.out.println("层> " + biTree.levelOrder().toString());

        testAllOrderRecursion();
    }

    static void testAllOrderRecursion(){

        List<String> list = new ArrayList<>();
        biTree.preOrderRecursion(biTree.root, list);
        System.out.println("前> " + list);

        list.clear();
        biTree.inOrderRecursion(biTree.root, list);
        System.out.println("中> " + list);

        list.clear();
        biTree.postOrderRecursion(biTree.root, list);
        System.out.println("后> " + list);
        System.out.println();
    }



    @Test
    public void preOrder() {
        System.out.println("前> " + biTree.preOrder());
    }

    @Test
    public void inOrder() {
        System.out.println("中> " + biTree.inOrder());
    }

    @Test
    public void postOrder(){
        System.out.println("后> " + biTree.postOrder());
    }


    public static String[] inputStringArray() {
        Scanner scanner = new Scanner(System.in);
        LinkBiTreeSample01_01.BitNode<String> root = new LinkBiTreeSample01_01.BitNode<>();
        System.out.print("请输入树的节点(从上到下从左到右，0代表空节点，以空格分隔，回车以结束)>>");
        return scanner.nextLine().split(" ");
    }

    public static String[] randomStringArray(int count) {
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = getRandomInt(0, 10);
        }
        return Arrays.stream(values).mapToObj(String::valueOf).toArray(String[]::new);
    }

    private static int getRandomInt(int min, int max) {
        if (max < min) return 0;
        return (int) Math.floor((Math.random() * (max - min + 1))) + min;
    }
}