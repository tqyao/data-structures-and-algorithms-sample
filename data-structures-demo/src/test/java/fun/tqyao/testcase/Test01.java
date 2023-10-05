/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.testcase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * @author tqyao
 * @version 1.0.0
 * @create 2023/10/04 08:39
 */
public class Test01 {
    public static void main(String[] args) {
//        Foo foo = (Foo) new Base();
        Base base = new Foo();
    }

    @Test
    public void test01(){
        Person p1 = new Person("tqyao");
        Person p2 = new Person("tqyao");
        System.out.println(p1.equals(p2));
        System.out.println(Objects.equals(p1, p2));
    }
}

//@AllArgsConstructor
//@EqualsAndHashCode
class Person{
    String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
class Foo extends Base{

}
class Base{}
