package fun.tqyao.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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