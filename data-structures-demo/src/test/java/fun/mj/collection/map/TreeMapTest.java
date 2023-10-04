package fun.mj.collection.map;

import fun.mj.collection.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeMapTest {

    @Test
    public void test01() {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("a",1);
        treeMap.put("b",2);
        treeMap.put("c",3);
        treeMap.put("b",36);

        treeMap.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key+"_"+value);
                return false;
            }
        });
    }

}