/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.mj.collection.model;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/04 08:52
 */
@Data
@AllArgsConstructor
public class Person {
    private int age;
    private float weight;
    private String name;

    public static void main(String[] args) {
        //floatToRawIntBits 把浮点数转换成二进制数后，在把对应的二进制数转换为整型
        System.out.println(Float.floatToRawIntBits(10.6f));//1093245338

        Person p1 = new Person(11, 10.6f, "zs");
        Person p2 = new Person(11, 10.6f, "zs");
        System.out.println(p1.equals(p2));
        System.out.println(p1 == p2);
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());//1288494905 -469088631

        //3897  11534336
//        int a = 11534336 + 3897;
//        System.out.println(a);
    }


    @Override
    public int hashCode() {
        int hash = Integer.hashCode(age);
        // hash << 5 等价于 hash * 2^5
        //  hash << 5 - hash 等价于  hash * 2^5 - hash = hash * 32 - hash = hash * 31
        hash = (hash << 5) - hash + Float.floatToRawIntBits(weight);
        hash = (hash << 5) - hash + name.hashCode();
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (getClass() != obj.getClass()) return false;

        Person person = (Person) obj;
        return person.age == age && person.weight == weight
                && person.name.equals(name);
    }
}
