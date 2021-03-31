package queue;

import java.util.LinkedList;
import java.util.Queue;

public class JavaQueueDemo {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<> ();
        queue.offer (22);
        queue.offer (33);
        queue.offer (44);
        queue.forEach (System.out::println);

        Integer poll = queue.poll ();
        System.out.println ("queue.poll():" + poll);
        queue.forEach (System.out::println);

        System.out.println ("queue.peek ():" + queue.peek ());
        System.out.println("The size is: " + queue.size());

    }
}
