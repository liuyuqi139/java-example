package jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * JVM相关例子
 * <p>createTime：2019-03-27 16:19:40
 *
 * @author liuyq
 * @since 1.0
 */
public class JVMTest {
    public static void main(String[] args) {
        System.out.println("a：" + test1());
    }

    public static int test1() {
        int a = 10;
        test2(a);
        System.out.println("test1 a：" + a);
        return a;
    }

    public static void test2(int a) {
        a += 20;
        System.out.println("test2 a：" + a);
    }

//    运行结果：
//    test2 a：30
//    test1 a：10
//    a：10

//   Java中的参数传递时传值呢？还是传引用？

//　　要说明这个问题，先要明确两点：
//
//　　1. 不要试图与C进行类比，Java中没有指针的概念。
//
//　　2. 程序运行永远都是在栈中进行的，因而参数传递时，只存在传递基本类型和对象引用的问题。不会直接传对象本身。
//
//　　明确以上两点后。Java在方法调用传递参数时，因为没有指针，所以它都是进行传值调用（这点可以参考C的传值调用）。因此，很多书里面都说Java是进行传值调用，这点没有问题，而且也简化的C中复杂性。
//
//　　传值传引用都不够准确，可以理解成传引用变量的副本值。引用变量分为字面值引用变量（即基本数据类型引用变量）和对象引用变量 。 详情需要了解数据类型使用机制和堆栈的概念：http://www.cnblogs.com/alexlo/archive/2013/02/21/2920209.html
//
//　　对象引用变量：即普通java对象的引用变量 ，如 String a = "abc" , a就是对象引用变量。java 是不能直接操作对象的，只能通过对“对象引用的操作”来操作对象。而对象的引用的表示就是对象变量。可以多个对象引用变量指向同一个对象。
//　　字面值引用变量：即普通数据类型的引用变量 ，如 int b = 1 , b就是字面值引用变量。可以有多个字面值引用变量指向同一字面值，但其中一个引用修改字面值，不会影响另一个引用字面值，这点要与对象引用区别开。
}

class JVMTest1 {
    public static void main(String[] args) {
        System.out.println("a：" + test1());
    }

    public static String test1() {
        String a = "10";
        test2(a);
        System.out.println("test1 a：" + a);
        return a;
    }

    public static void test2(String a) {
        a += 20;
        System.out.println("test2 a：" + a);
    }

//    test2 a：1020
//    test1 a：10
//    a：10
}

class JVMTest2 {
    public static void main(String[] args) {
        System.out.println("a：" + test1());
    }

    public static String test1() {
        Map<String, String> a = new HashMap<>();
        a.put("1", "10");
        System.out.println("test1 a：" + a.get("1"));
        test2(a);
        return a.get("1");
    }

    public static void test2(Map<String, String> a) {
        a.put("1", "20");
        System.out.println("test2 a：" + a.get("1"));
    }

//    test1 a：10
//    test2 a：20
//    a：20
}