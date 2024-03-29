package fun.mj.trie;

import fun.mj.util.Asserts;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieImplTest {
    public static void main(String[] args) {
        Trie<Integer> trie = new TrieImpl<>();
        trie.add("cat", 1);
        trie.add("dog", 2);
        trie.add("catalog", 3);
        trie.add("cast", 4);
        trie.add("小码哥", 5);
        Asserts.test(trie.size() == 5);
        Asserts.test(trie.startsWith("do"));
        Asserts.test(trie.startsWith("c"));
        Asserts.test(trie.startsWith("ca"));
        Asserts.test(trie.startsWith("cat"));
        Asserts.test(trie.startsWith("cata"));
        Asserts.test(!trie.startsWith("hehe"));
        Asserts.test(trie.get("小码哥") == 5);
        Asserts.test(trie.remove("cat") == 1);
        Asserts.test(trie.remove("catalog") == 3);
        Asserts.test(trie.remove("cast") == 4);
        Asserts.test(trie.size() == 2);
        Asserts.test(trie.startsWith("小"));
        Asserts.test(trie.startsWith("do"));
        Asserts.test(!trie.startsWith("c"));
    }

    @Test
    public void test() {
        Trie<Integer> trie = new TrieImpl<>();

        trie.add("cat", 1);
        trie.add("dog", 2);
        System.out.println(trie.get("cat") == 1);
        System.out.println(trie.remove("cat") == 1);
        System.out.println(!(trie.get("cat") == 1));

    }
}