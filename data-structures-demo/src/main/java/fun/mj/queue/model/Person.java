/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.queue.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/08 19:33
 */
@Data
@AllArgsConstructor
public class Person implements Comparable<Person> {
    private String name;
    private int brokenBone;

    @Override
    public int compareTo(Person o) {
        return this.brokenBone - o.brokenBone;
    }


}
