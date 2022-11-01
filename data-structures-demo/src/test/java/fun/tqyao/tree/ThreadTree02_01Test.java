package fun.tqyao.tree;

import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

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
        theadTree.createCompleteTree(strings);
    }




    @Test
    public void testFrontInOrderThread(){
        theadTree.createInThread();
        System.out.println(theadTree.frontInOrderThread(theadTree.root));
        System.out.println(theadTree.subInOrderThread2(theadTree.root));

    }

    @Test
    public void testSubInOrderThread(){
        theadTree.createInThread();
        System.out.println(theadTree.subInOrderThread(theadTree.root));
    }


    @Test
    public void testPreOrderTraverse() {
        theadTree.preOrderTraverse();
    }

    @Test
    public void testPostThread2() {
        theadTree.postThreadRecursion(theadTree.root);
        log.info("==1==");
        Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.lchild;
        }).map(B -> {
            log.info("2 {}", B.data);
            return B.rchild;
        }).map(C -> {
            log.info("3 {}", C.data);
            return C.rchild;
        }).map(A -> {
            log.info("4 {}", A.data);
            return A;
        });

        log.info("==2==");
        Optional<ThreadTree02_01.ThreadNode<String>> stringThreadNode = Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.lchild;
        }).map(B -> {
            log.info("2 {}", B.data);
            return B.lchild;
        });

        if (!stringThreadNode.isPresent()){
            log.info("后续遍历输出的第一个节点的左线索为空");
        }else {
            stringThreadNode.ifPresent(System.out::println);
        }

    }

    @Test
    public void testInThread2() {
        theadTree.inThreadRecursion(theadTree.root);
        log.info("==left tree==");
        Optional<ThreadTree02_01.ThreadNode<String>> stringThreadNode1 = Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.lchild;
        }).map(B -> {
            log.info("2 {}", B.data);
            return B.rchild;
        }).map(A -> {
            log.info("3 {}", A.data);
            return A.rchild;
        });

        if (!stringThreadNode1.map(C -> C.rchild).isPresent()) {
            log.info("中序最后一个输出节点的右索引是空");
        } else {
            stringThreadNode1.ifPresent(System.out::println);
        }

        log.info("==right tree==");

        Optional<ThreadTree02_01.ThreadNode<String>> stringThreadNode = Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.rchild;
        }).map(C -> {
            log.info("2 {}", C.data);
            return C.lchild;
        }).map(A -> {
            log.info("3 {}", A.data);
            return A.lchild;
        });


        if (!stringThreadNode.map(B -> B.lchild).isPresent()) {
            log.info("中序第一个输出节点的左索引是空");
        }
    }

    @Test
    public void testPostThread() {
        theadTree.createPostThread();

        log.info("==1==");
        Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.lchild;
        }).map(B -> {
            log.info("2 {}", B.data);
            return B.rchild;
        }).map(C -> {
            log.info("3 {}", C.data);
            return C.rchild;
        }).map(A -> {
            log.info("4 {}", A.data);
            return A;
        });
    }

    @Test
    public void testInThread() {
        theadTree.createInThread();
        log.info("==left tree==");
        Optional<ThreadTree02_01.ThreadNode<String>> stringThreadNode1 = Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.lchild;
        }).map(B -> {
            log.info("2 {}", B.data);
            return B.rchild;
        }).map(A -> {
            log.info("3 {}", A.data);
            return A.rchild;
        });

        if (!stringThreadNode1.map(C -> C.rchild).isPresent()) {
            log.info("中序最后一个输出节点的右索引是空");
        } else {
            stringThreadNode1.ifPresent(System.out::println);
        }

        log.info("==right tree==");

        Optional<ThreadTree02_01.ThreadNode<String>> stringThreadNode = Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.rchild;
        }).map(C -> {
            log.info("2 {}", C.data);
            return C.lchild;
        }).map(A -> {
            log.info("3 {}", A.data);
            return A.lchild;
        });


        if (!stringThreadNode.map(B -> B.lchild).isPresent()) {
            log.info("中序第一个输出节点的左索引是空");
        }


    }


    /**
     * 用例未通过，构建失败
     *
     * @author tqyao
     * @create 2022/10/28 15:41
     */
    @Test
    public void testPreThread() {
        theadTree.createPreThread();

        log.info("==1==");
        Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.lchild;
        }).map(B -> {
            log.info("2 {}", B.data);
            return B.lchild;
        }).map(A -> {
            log.info("3 {}", A.data);
            return A.lchild;
        }).map(B -> {
            log.info("4 {}", B.data);
            return B;
        });

        log.info("==2==");

        Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.rchild;
        }).map(C -> {
            log.info("2 {}", C.data);
            return C.lchild;
        }).map(B -> {
            log.info("3 {}", B.data);
            return B.lchild;
        }).map(A -> {
            log.info("4 {}", A.data);
            return A;
        });

        log.info("==3==");
        Optional<ThreadTree02_01.ThreadNode<String>> stringThreadNode = Optional.ofNullable(theadTree.root).map(A -> {
            log.info("1 {}", A.data);
            return A.rchild;
        }).map(C -> {
            log.info("2 {}", C.data);
            return C.rchild;
        });

        if (!stringThreadNode.isPresent()) {
            log.info("C.rchild is null");
        } else {
            log.info(">> {}", stringThreadNode.get().data);
        }


    }

    @Test
    public void testCreateCompleteTreeRecursion() {
        theadTree.createCompleteTreeRecursion(strings, 0);
        System.out.println("递归 层> " + theadTree.levelOrder());
    }

    @Test
    public void testCreateCompleteTree() {
        theadTree.createCompleteTree(strings);
        System.out.println("遍历 层> " + theadTree.levelOrder());
    }


    @Test
    public void testLevelOrder() {
        System.out.println("层> " + theadTree.levelOrder());
    }

}