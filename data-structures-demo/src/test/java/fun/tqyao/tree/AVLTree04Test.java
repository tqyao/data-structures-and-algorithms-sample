package fun.tqyao.tree;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AVLTree04Test {


    static AVLTree04<Integer> root;

    @BeforeClass
    public static void beforeClass() {
        root = new AVLTree04<>();
//        int[] nodeVals = Arrays.stream("1,5,4,3,2,6".split(","))
//                .mapToInt(Integer::parseInt).toArray();

        int[] nodeVals = Arrays.stream("41,20,65,11,29,50,91,8,15,32,72,99,6".split(","))
                .mapToInt(Integer::parseInt).toArray();

        System.out.print("按顺序输入>>> ");
        System.out.println(Arrays.stream(nodeVals).boxed().collect(Collectors.toList()));

        for (int i: nodeVals)
            root.insert(i);

        System.out.print("中序遍历>>> ");
        System.out.println(root.inOrderTraverse());
    }


    @Test
    public void testRemove(){
        int del = 20;
        System.out.printf("删除根节点：%d\n",del);
        root.remove(del);

        System.out.print("中序遍历>>> ");
        System.out.println(root.inOrderTraverse());
    }


    @Test
    public void testArrayToList() {
        // test
        System.out.println("================");
        String[] strArray = new String[2];
        List<String> list = Arrays.asList(strArray);
        //对转换后的list插入一条数据
        list.add("1");  // error
        System.out.println(list);

        Integer[] intArray1 = new Integer[2];
        List<Integer> integers = Arrays.asList(intArray1);
        System.out.println(integers);
        System.out.println("======================");


        System.out.println("======================");
        int[] nodeVals = Arrays.stream("1,5,4,3,2,6".split(","))
                .mapToInt(Integer::parseInt).toArray();
        List<int[]> ints = Arrays.asList(nodeVals);

        int[] intArray = new int[2];
        List<int[]> ints1 = Arrays.asList(intArray);
        System.out.println("======================");

        // String array to List
        // 1.
        String[] sArr = new String[]{"aaa", "bbb"};
        List<String> colList = Arrays.stream(sArr).collect(Collectors.toList());

        // 2.
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(sArr));


        // 基本数据类型数组 to List
        /**
         * ⭐️ boxed()
         * @Override
         * public final Stream<Integer> boxed() {
         *     return mapToObj(Integer::valueOf);
         * }
         * boxed的作用就是将int类型的stream转成了Integer类型的Stream
         */
        int []nodeVals2 = Arrays.stream("1,5,4,3,2,6".split(","))
                .mapToInt(Integer::parseInt).toArray();
        List<Integer> collect = Arrays.stream(nodeVals2).boxed().collect(Collectors.toList());

    }
}
