package fun.tqyao.hash;

import junit.framework.TestCase;
import org.junit.Test;

public class HashTableTest extends TestCase {

    @Test
    public void test03(){
        HashTable table1 = new HashTable();
        for (int i = 0; i < 2000000; i++) {
            Object o = new Object();
            table1.put(o, o);
        }
        table1.print();
    }

    @Test
    public void test(){
        HashTable hashTable = new HashTable();
        hashTable.put( 1, "张三");//1
        hashTable.put( 25, "李四");//1
        hashTable.put( 2, "王五");

        assertEquals(hashTable.table.length, 8);
        assertEquals(hashTable.table[1].value, "张三");
        assertNull(hashTable.table[1].next);
//        assertEquals(hashTable.table[1].next.value,"李四");


        assertEquals(hashTable.table[5].value,"李四");
        assertNull(hashTable.table[5].next);
    }

    @Test
    public void testResize() {

//         loadFactor = 0.75f;
//         length = 4
        HashTable hashTable = new HashTable();
        hashTable.put(1, "zhangsan", "张三");//1
        hashTable.put(25, "li", "李四");//1


        hashTable.put(2, "wang", "王五");
        assertEquals(hashTable.table.length, 8);
        assertEquals(hashTable.table[1].value, "张三");
        assertNull(hashTable.table[1].next);
//        assertEquals(hashTable.table[1].next.value,"李四");


        assertEquals(hashTable.table[5].value,"李四");
        assertNull(hashTable.table[5].next);

    }

    @Test
    public void test01() {
        HashTable hashTable = new HashTable();
        hashTable.put(1, "zhangsan", "张三");//1
        hashTable.put(17, "li", "李四");//1
        hashTable.put(2, "wang", "王五");

        hashTable.remove(1, "zhangsan");
        assertEquals(2, hashTable.size);

        System.out.println(hashTable.table[1].value);


        assertEquals("李四", hashTable.table[1].value);
        assertNull(hashTable.table[1].next);
    }

    @Test
    public void test02() {
        HashTable hashTable = new HashTable();
        hashTable.put(1, "zhangsan", "张三");//1
        hashTable.put(17, "li", "李四");//1
        hashTable.put(2, "wang", "王五");

        hashTable.remove(1, "li");
        assertEquals(2, hashTable.size);

        assertEquals("张三", hashTable.table[1].value);
        assertNull(hashTable.table[1].next);
    }
}