package fun.mj.queue;

import fun.mj.queue.model.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest {

    @Test
    public void test() {
        PriorityQueue<Person> queue = new PriorityQueue<>();
        queue.enQueue(new Person("zhagsan", 11));
        queue.enQueue(new Person("lisi", 1));
        queue.enQueue(new Person("wangwu", 6));
        queue.enQueue(new Person("zhaoliu", 10));
        queue.enQueue(new Person("wangmazi", 2));

        int size = queue.size();
        for (int i = 0; i < size; i++) {
            System.out.println(queue.deQueue());
        }
    }

}