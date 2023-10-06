package fun.mj.collection.map;

import fun.mj.collection.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedHashMapTest {

    @Test
    public void testTraversal(){
        LinkedHashMap<Object,Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("aaa",1);
        linkedHashMap.put("ccc",2);
        linkedHashMap.put("bb",3);
        linkedHashMap.put(33,77);
        testTrav(linkedHashMap);
        System.out.println();

       HashMap<Object,Integer> hashMap = new HashMap<>();
        hashMap.put("aaa",1);
        hashMap.put("ccc",2);
        hashMap.put("bb",3);
        hashMap.put(33,77);
        hashMap.print();
        testTrav(hashMap);


    }

    public void testTrav(Map map){
        map.traversal(new Map.Visitor<Object, Integer>() {
            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

}