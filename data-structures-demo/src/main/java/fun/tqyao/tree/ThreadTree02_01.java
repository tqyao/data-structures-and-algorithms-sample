/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.tree;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2022/10/25 10:08
 */
@Slf4j
public class ThreadTree02_01<T> {

    ThreadNode<T> root;


    /**
     * 中序线索化二叉树：
     * 中序遍历指定节点
     *
     * @param any
     * @return java.util.List<T>
     * @author tqyao
     * @create 2022/10/28 17:03
     */
    public List<T> inOrderThread(ThreadNode<T> any) {
        List<T> resList = new ArrayList<>();
        while (null != any) {
            resList.add(any.data);
            any = inNextNode(any);
        }
        return resList;
    }


    /**
     * 中序线索化二叉树：
     * 查找指定节点中序线索化二叉树找后继节点
     *
     * @param p
     * @return fun.tqyao.tree.ThreadTree02_01.ThreadNode<T>
     * @author tqyao
     * @create 2022/10/28 16:51
     */
    public ThreadNode<T> inNextNode(ThreadNode<T> p) {
        /*
         指定节点已经被线索化，则右孩子就是其后继
         ，否则该节点最后一个左子树中序遍历输出的第一个节点才是 指定节点 的后继
         */
        if (p.rtga == 1)
            return p.rchild;
        return inFistNode(p);
    }

    /**
     * 中序线索化二叉树：
     * 找到指定节点中序遍历第一个输出节点
     *
     * @param p
     * @return fun.tqyao.tree.ThreadTree02_01.ThreadNode<T>
     * @author tqyao
     * @create 2022/10/28 16:51
     */
    private ThreadNode<T> inFistNode(ThreadNode<T> p) {
        while (p.ltag == 0)
            p = p.lchild;
        return p;
    }

    /**
     * 后续线索化二叉树1
     * @author tqyao
     * @create 2022/10/27 19:45
     */
    public void createPostThread() {
        /*
            后续线索化最后一个左（右）孩子是空的节点如果不是最后输出节点，则说明早已经被线索化了；
            最后一个右子树时空的节点是最后输出的节点（根节点），说明是根节点右子树为空的情况，需要手动设置根节点线索化
         */
        ThreadNode<T> last = postThread(root, null);
        if (null == last.rchild) {
            log.info("createPostThread last = null");
            last.rtga = 1;
        } else {
            log.info("createPostThread last.data = {}", last.data);
        }

    }

    private ThreadNode<T> postThread(ThreadNode<T> cur, ThreadNode<T> pre) {
        if (null != cur) {
            // 后序遍历左右根，此时调用自己进入下一层，但当前层节点还未输出，所以必须传入 pre 作为最近一个已经输出的节点
            pre = postThread(cur.lchild, pre);
            // 获取下层处理结果，也就是下层返回最近一次输出节点作为 pre ，再调用自己进入下层
            pre = postThread(cur.rchild, pre);


            // 处理当前节点线索化，必须更新pre指针指向，因为下层处理有可能输出节点，所以拿到下层输出节点作为pre指针
            if (null == cur.lchild) {
                cur.lchild = pre;
                cur.ltag = 1;
            }

            if (null != pre && pre.rchild == null) {
                pre.rchild = cur;
                pre.rtga = 1;
            }
            // 后序遍历当输出节点为以该节点为根的高度为2的二叉树的最后一个节点，直接返回给到上层做为前驱
            return cur;
        }
        // cur 是空，则返回pre给上一层
        return pre;
    }


    ThreadNode<T> pre;

    /**
     * 后序线索化2
     * @author tqyao
     * @create 2022/10/29 14:42
     * @param cur
     */
    public void postThreadRecursion(ThreadNode<T> cur) {
        if (null == cur)
            return;
        postThreadRecursion(cur.lchild);
        postThreadRecursion(cur.rchild);
        if (cur.lchild == null) {
            cur.lchild = pre;
            cur.ltag = 1;
        }
        if (pre != null && pre.rchild == null) {
            pre.rchild = cur;
            pre.rtga = 1;
        }
        // 遍历输出后，自然是下一次递归调用的前驱
        pre = cur;
    }


    /**
     * 中序线索化二叉树2
     *
     * @param cur
     * @author tqyao
     * @create 2022/10/29 14:21
     */
    public void inThreadRecursion(ThreadNode<T> cur) {
        if (null == cur)
            return;
        inThreadRecursion(cur.lchild);
        // 遍历输出
        if (cur.lchild == null) {
            cur.lchild = pre;
            cur.ltag = 1;
        }
        if (pre != null && pre.rchild == null) {
            pre.rchild = cur;
            pre.rtga = 1;
        }
        // 遍历输出后，自然是下一次递归调用的前驱
        pre = cur;
        inThreadRecursion(cur.rchild);
    }

    /**
     * 前序线索化2
     *
     * @param cur
     * @author tqyao
     * @create 2022/10/28 21:25
     */
    public void preThreadRecursion(ThreadNode<T> cur) {
        if (null == cur)
            return;

        if (cur.lchild == null) {
            cur.lchild = pre;
            cur.ltag = 1;
        }

        if (pre != null && pre.rchild == null) {
            pre.rchild = cur;
            pre.rtga = 1;
        }
        // 遍历输出后，自然是下一次递归调用的前驱
        pre = cur;
        // 未被线索化则对左孩递归，避免死循环
        if (cur.ltag == 0)
            preThreadRecursion(cur.lchild);

        preThreadRecursion(cur.rchild);
    }


    /**
     * 前序线索化1
     *
     * @author tqyao
     * @create 2022/10/26 16:53
     */
    public void createPreThread() {
        ThreadNode<T> pre = null;
        pre = preThread(root, pre);
        pre.rtga = 1;
        log.info("createPreThread {}", pre.data);
    }

    private ThreadNode<T> preThread(ThreadNode<T> curNode, ThreadNode<T> pre) {
        if (null != curNode) {
            if (null == curNode.lchild) {
                curNode.lchild = pre;
                curNode.ltag = 1;
            }
            if (null != pre && null == pre.rchild) {
                pre.rchild = curNode;
                pre.rtga = 1;
            }
            // 接收最新做递归遍历输出的节点
            ThreadNode<T> x = null;
            if (curNode.ltag != 1)
                // 左孩子递归调用，接收下层递归结果
                x = preThread(curNode.lchild, curNode);
            // 右孩子递归调用，递归调用结果返回上一层处理
            /*
             如果递归右子树之前，左子树有输出，则右孩子前驱应该是左子树输出的节点，也就是pre应该传递上一个递归调用结果
             ，否则延用当前输出节点curNode 作为下次递归的pre指针
             */
            return preThread(curNode.rchild, x == null ? curNode : x);
        }
        // 返回上一层
        return pre;
    }

    /**
     * 中序线索化二叉树1
     *
     * @author tqyao
     * @create 2022/10/25 20:49
     */
    public void createInThread() {
        ThreadNode<T> pre = null;
        pre = inThread(root, pre);
        log.info("createInThread > {}", pre.data);
        pre.rtga = 1;
    }

    private ThreadNode<T> inThread(ThreadNode<T> curNode, ThreadNode<T> pre) {
        if (null != curNode) {
            // 中序遍历左中右，接收下层处理结果，及时更新右子树处理结果作为当前层的输出前驱
            pre = inThread(curNode.lchild, pre);
            if (null == curNode.lchild) {
                curNode.ltag = 1;
                curNode.lchild = pre;
            }
            if (null != pre && null == pre.rchild) {
                pre.rtga = 1;
                pre.rchild = curNode;
            }
            // 右递归调用后，传递返回下层处理结果给到上层处理，中序遍历右递归结果是以该节点为"最小子树"输出的最后节点
            return inThread(curNode.rchild, curNode);
        }
        // 递归返回，给上层处理
        return pre;
    }


    /**
     * 递归创建完全二叉树
     *
     * @param ts
     * @param index
     * @author tqyao
     * @create 2022/10/26 16:30
     */
    public void createCompleteTreeRecursion(T[] ts, int index) {
        root = completeTreeRecursion(ts, index);
    }

    private ThreadNode<T> completeTreeRecursion(T[] ts, int index) {
        ThreadNode<T> curNode;
        if (index < ts.length) {
            curNode = new ThreadNode<>(ts[index]);
            ThreadNode<T> lchild = completeTreeRecursion(ts, index * 2 + 1);
            ThreadNode<T> rchild = completeTreeRecursion(ts, index * 2 + 2);
            curNode.lchild = lchild;
            curNode.rchild = rchild;
            return curNode;
        } else
            return null;
    }

    /**
     * 创建完全二叉树
     *
     * @author tqyao
     * @create 2022/10/25 19:03
     */
    public void createCompleteTree(T[] ts) {
        if (null == ts || ts.length == 0)
            return;

        List<ThreadNode<T>> nodeList = new ArrayList<>();
        for (T t : ts)
            nodeList.add(new ThreadNode<>(t));

        // 非叶子节点个数 n1 + n2
        int noLeaves = nodeList.size() / 2;
        for (int i = 0; i < noLeaves; i++) {
            if (i * 2 + 1 < nodeList.size())
                nodeList.get(i).lchild = nodeList.get(i * 2 + 1);
            if (i * 2 + 2 < nodeList.size())
                nodeList.get(i).rchild = nodeList.get(i * 2 + 2);
        }
        root = nodeList.get(0);
    }


    /**
     * 层序遍历
     *
     * @return java.util.List<java.util.List < T>>
     * @author tqyao
     * @create 2022/10/25 16:01
     */
    public List<List<T>> levelOrder() {
        List<List<T>> resList = new ArrayList<>();
        if (null == root)
            return resList;

        Queue<ThreadNode<T>> queue = new LinkedList<>();
        queue.add(root);

        int curLevelLens;
        ThreadNode<T> tempNode;
        List<T> curLevelList;
        while (!queue.isEmpty()) {
            curLevelLens = queue.size();
            curLevelList = new ArrayList<>();
            for (int i = 0; i < curLevelLens; i++) {
                tempNode = queue.poll();
                curLevelList.add(tempNode.data);

                Optional.ofNullable(tempNode.lchild).ifPresent(queue::add);
                Optional.ofNullable(tempNode.rchild).ifPresent(queue::add);
            }
            resList.add(curLevelList);
        }
        return resList;
    }

    /**
     * 创建二叉树（未关联线索）
     * todo:无法完按数组层序创建
     *
     * @param ts
     * @author tqyao
     * @create 2022/10/25 15:52
     */
    public void createBinaryTree(T[] ts) {
        if (null == ts || ts.length == 0)
            return;
        if (ts instanceof String[])
            root = createBinaryTreeRecursion(ts);
    }


    static int idx = 0;

    /**
     * 递归创建二叉树
     * todo:无法完按数组层序创建
     *
     * @param ts
     * @return fun.tqyao.tree.ThreadTree02_01.ThreadNode<T>
     * @author tqyao
     * @create 2022/10/25 15:54
     */
    private ThreadNode<T> createBinaryTreeRecursion(T[] ts) {
        if (idx < ts.length) {
            T data = ts[idx++];
            if ("0".equals(data))
                return null;
            else {
                ThreadNode<T> node = new ThreadNode<>(data);
                node.lchild = createBinaryTreeRecursion(ts);
                node.rchild = createBinaryTreeRecursion(ts);
                return node;
            }
        }
        return null;
    }

    static class ThreadNode<T> {
        T data;
        ThreadNode<T> lchild, rchild;
        int ltag, rtga;

        ThreadNode() {
        }

        ThreadNode(T data) {
            this.data = data;
        }

//        @Override
//        public String toString() {
//            return "ThreadNode{" +
//                    "data=" + data +
//                    ", lchild=" + lchild +
//                    ", rchild=" + rchild +
//                    '}';
//        }


        @Override
        public String toString() {
            return "ThreadNode{" +
                    "data=" + data +
                    '}';
        }
    }
}
