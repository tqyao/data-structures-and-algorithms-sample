package fun.tqyao.mj_collection;

import fun.mj.collection.Set;
import fun.mj.collection.set.ListSet;
import org.junit.jupiter.api.Test;

class ListSetTest {
    @Test
    public void test01(){
        Set<Integer> set = new ListSet<>();
        set.add(12);
        set.add(12);
        set.add(11);
        set.add(12);
        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }
}