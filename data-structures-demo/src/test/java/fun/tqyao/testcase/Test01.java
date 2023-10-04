/*
 * Copyright 2020-2030 The author personally reserves all rights.
 */
package fun.tqyao.testcase;

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
}
class Foo extends Base{

}
class Base{}
