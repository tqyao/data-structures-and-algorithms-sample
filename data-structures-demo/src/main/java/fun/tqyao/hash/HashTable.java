/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.hash;

import com.google.common.hash.Hashing;
import com.sun.xml.internal.txw2.output.IndentingXMLFilter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/09/14 08:33
 */
public class HashTable {

    Entry[] table = new Entry[16];

    int size = 0;

    // 阈值
    float loadFactor = 0.75f;


    int threshold = (int) (loadFactor * table.length);

    /**
     * 获取指定 hash、key 的值
     * <p>
     * "hash & (数组长度 - 1)"
     * 这个技巧基于以下两个假设：
     * <p>
     * 数组长度是2的幂（例如，16、32、64等）。
     * 位运算中的按位与（&）操作可以实现对数字进行模运算，但只对2的幂次数有效。
     * 假设数组长度为 n，当 n 是2的幂时，n - 1 的二进制表示将全部是1，例如：
     * <p>
     * 当 n = 16 时，n - 1 = 15，二进制表示为 1111。
     * 当 n = 32 时，n - 1 = 31，二进制表示为 11111。
     * 在这种情况下，"hash & (n - 1)" 的操作可以简化为对哈希值进行按位与操作，结果将是一个介于 0 到 (n - 1) 范围内的数值，这对应于数组的有效索引。
     * <p>
     * 例如，假设哈希值为 123，而数组长度为 16，则：
     * <p>
     * 123 的二进制表示为 1111011。
     * 15 的二进制表示为 1111。
     * 通过执行按位与操作，我们可以得到 123 & 15 = 11，这是一个有效的数组索引。因此，元素会被放置在索引为 11 的位置上。
     *
     * @param hash
     * @param key
     * @return java.lang.Object
     * @author tqyao
     * @create 2023/9/14 09:07
     */
    Object get(int hash, Object key) {
        /*
        ① idx = hash % 数组长度
        ② idx = hash & (数组长度-1)
        1、2 是等价的，但条件必须满足：要求数组长度必须是2^n
         */
        int idx = hash & (table.length - 1);
        Entry entry = table[idx];
        while (entry != null && !entry.key.equals(key)) {
            entry = entry.next;
        }
        return entry == null ? null : entry.value;
    }


    /**
     * 1. 散列位置为空 直接加入
     * 2. 散列位置存在，比较链表中是否存在相同，相同覆盖
     * 3. 链表中无相同，直接加入对尾
     *
     * @param hash
     * @param key
     * @param value
     * @author tqyao
     * @create 2023/9/14 09:16
     */
    void put(int hash, Object key, Object value) {
        //【hash & (长度-1)】 等价于 【hash%数长度】
        int idx = hash & (table.length - 1);

        Entry entry = table[idx];

        //1.如果 对应散列位置为空，直接加入节点
        if (entry == null) {
            table[idx] = new Entry(hash, key, value, null);
        } else {
            // 2. 查找链表中存在相同 key
            Entry prev = null;
            while (entry != null) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
                prev = entry;
                entry = entry.next;
            }
            //3.不存在相同 key，链表尾部追加
            prev.next = new Entry(hash, key, value, null);
        }

        size++;

        if (size >= threshold) {
            resize();
        }
    }


    /**
     * @author tqyao
     * @create 2023/9/14 18:56
     */
    private void resize() {

        int newLen = table.length * 2;
        // 1. 数组长度翻倍
        Entry[] newTable = new Entry[newLen];

        // 拷贝旧数组元素到新数组
        // 2. 对旧数组每个元素的每个节点链上的节点重新散列
        for (int i = 0; i < table.length; i++) {
            Entry entry = table[i];
            // 3. 旧数组的每个节点链分成两个条新节点链，一条保留原始散列位置，另一条则 当前 idx = idx + 旧数组长度
            if (entry != null) {
                Entry a = null, b = null;
                Entry aHead = null, bHead = null;
                while (entry != null) {
                    /*
                     1、hash & 新数组长度 == 0
                     - 扩容后保持原索引位置不变

                     2、hash & 新数组长度 == 新数组长度
                     - 扩容后散列索引改变：idx + 旧数组长度
                     */

                    if ((entry.hash & newLen) == newLen) {
                        if (a == null) {
                            a = entry;
                            aHead = a;  // 记录链表头结点
                        } else {
                            a.next = entry;
                        }
                    } else {
                        if (b == null) {
                            b = entry;
                            bHead = b;
                        } else {
                            b.next = entry;
                        }
                    }
                    entry = entry.next;
                }

                //拆分链表后，处理新链表尾结点
                if (a != null) {
                    a.next = null;
                }
                if (b != null) {
                    b.next = null;
                }
                newTable[i + table.length] = aHead;
                newTable[i] = bHead;
            }
        }

        // 重新计算阈值
        threshold = (int) (loadFactor * table.length);
        table = newTable;
    }

    /**
     * 1. 散列地址位置为空，删除失败返回空
     * 2. 不为空。查找链表是否存在该 key，存在删除之；不存在。删除失败返回 null
     *
     * @param hash
     * @param key
     * @return java.lang.Object
     * @author tqyao
     * @create 2023/9/14 10:02
     */
    Object remove(int hash, Object key) {
        int idx = hash & (table.length - 1);
        Entry entry = table[idx];

        // 1. 如果散列后不存在节点，返回 null
        if (entry == null) {
            return null;
        }

        // 查找链表中是否存在指定 key 节点
        Entry prev = null; // 记录上一个节点
        while (entry != null) {
            // 2. 存在指定 key 节点，删除之
            if (entry.key.equals(key)) {
                if (prev == null) {
                    // 删除的是链表头
                    table[idx] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return entry.value;
            }
            prev = entry;
            entry = entry.next;
        }
        // 3. 循环结束。指定 key 不存在于链表
        return null;
    }


    Object get(Object key) {
        return get(getHash(key), key);
    }

    void put(Object key, Object value) {
        put(getHash(key), key, value);
    }

    Object remove(Object key) {
        return remove(getHash(key), key);
    }

    private int getHash(Object obj) {
        if (obj instanceof String) {
            return Hashing.murmur3_32().hashString((CharSequence) obj, StandardCharsets.UTF_8).asInt();
        }
        return obj.hashCode();
    }


    public static int hashcode(String str) {
        int hash = 0;

        /*
        "abc" a * 100 + b * 10 + c * 1
         */

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
//            hash = hash * 10 + c;

            // 基于经验：乘一个较大的质数发生碰撞的几率较小，选中 31
            // hash = hash * 32 - hash + c;// 32=2^5
            hash = (hash << 5) - hash + c;
        }
        return hash;
    }

    /**
     * 计算数组每个链表的长度并打印
     *
     * @author tqyao
     * @create 2023/9/14 21:37
     */
    public void print() {
        int sums[] = new int[table.length];
        for (int i = 0; i < sums.length; i++) {
            Entry p = table[i];
            while (p != null) {
                sums[i] += 1;
                p = p.next;
            }
        }

        Map<Integer, Long> collect = Arrays.stream(sums).boxed().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        System.out.println(collect);
//        System.out.println(Arrays.toString(sums));
    }


    public static void main01(String[] args) {
        System.out.println(hashcode("abc"));
        System.out.println(hashcode("bac"));
        System.out.println("abc".hashCode());

        System.out.println(Integer.valueOf(1).hashCode());
    }

    @AllArgsConstructor
    class Entry {
        int hash;

        Object key;

        Object value;

        Entry next;

    }
}
