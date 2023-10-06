package fun.mj.collection.map;

import fun.mj.collection.Map;
import fun.mj.collection.model.Key;
import fun.mj.collection.model.Person;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.LinkedList;

class HashMapTest {


    @Test
    public void testPut() {
        HashMap<Object, Integer> hashMap = new HashMap<>();

        Person p1 = new Person(11, 10.6f, "zs");
        Person p2 = new Person(11, 10.6f, "zs");
        Map<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put(null, 7);
        map.put("jack", 3);
        map.put("rose", 4);
        map.put("jack", 5);
//        System.out.println(map.get(p1));
//        System.out.println(map.get(p2));
//        System.out.println(map.get("jack"));
//        System.out.println(map.get("rose"));
//        System.out.println(map.get(null));

        System.out.println(map.size());
        System.out.println(map.remove("rose"));
        System.out.println(map.size());

    }

    @Test
    public void testTravel() {

        Person p1 = new Person(11, 10.6f, "zs");
        Person p2 = new Person(11, 10.6f, "zs");
        Map<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put(null, 7);
        map.put("jack", 3);
        map.put("rose", 4);
        map.put("jack", 5);

        map.traversal(new Map.Visitor<Object, Integer>() {
            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });

    }

    @Test
    public void testPrint() {
        HashMap<Object, Integer> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put(new Key(i), i);
        }
        /*
        问题复现
        加入 new Key(8) 与 get 时 new Key(8) 不是同一对象，
        compare 逻辑不确定性，生成对象的地址值大小不确定
         */
        map.print();
        System.out.println(map.get(new Key(8)));
    }

    @Test
    public void testPut01() {
        HashMap<Object, Integer> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put(new Key(i), i);
        }

        map.print();
        System.out.println(map.get(new Key(8)));
        System.out.println(map.size());

    }

    @Test
    public void testAddNull() {
        LinkedList list = new LinkedList();

        list.offer(null);
        System.out.println(list.isEmpty());
        System.out.println(list.size());
    }


    @Test
    public void testLinkHashMap() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        linkedHashSet.add("aaa");
        linkedHashSet.add("bbb");
        linkedHashSet.add("ccc");
        linkedHashSet.add("ddd");
        System.out.println(linkedHashSet);
    }
}