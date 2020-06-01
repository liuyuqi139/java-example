package jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * 值引用
 */
public class ValueReferenceTest {
    static class ArrayTest {
        public static void main(String[] args) {
            int[] a = new int[1];
            int[] b = a;
            b[0] = 1;
            System.out.println(b[0]);
            System.out.println(a[0]);

            int c = 1;
            int d = c;
            d = 2;
            System.out.println(c);
            System.out.println(d);
        }
    }

    static class ObjectTest {
        public static void main(String[] args) {
            Map<String, String> map = new HashMap<>();
            Map<String, String> map2 = map;
            map2.put("1", "1");
            System.out.println(map.get("1"));
            System.out.println(map2.get("1"));
        }
    }

    static class StringTest {
        int count;

        void change(String str) {
            str = "456";
        }

        void change(Map<String, String> map) {
            map.put("a", "456");
        }

        void change() {
            count = 1;
        }

        public static void main(String[] args) {
            String str1 = "123";
            String str2 = "123";
            System.out.println(str1 == str2);

            StringTest stringTest = new StringTest();
            stringTest.change(str2);
            System.out.println(str2);

            Map<String, String> map = new HashMap<>();
            map.put("a", "123");
            stringTest.change(map);
            System.out.println(map);

            int a = 0;
            int b = 0;
            System.out.println(a == b);

            stringTest.change();
            System.out.println(stringTest.count);
        }
    }

    static class StringTest2 {
        public static void main(String[] args) {
            // Class 文件中的常量池（编译器生成的各种字面量和符号引用）会在类加载后被放入这个区域。
            // 符号引用: 即在编译时用符号引用来代替引用类，在加载时再通过虚拟机获取该引用类的实际地址
            // 字面量：String a =“享学课堂”

             /* new String都是在堆上创建字符串对象。当调用 intern() 方法时，
             编译器会将字符串添加到常量池中（stringTable维护），并返回指向该常量的引用。*/

             /* 通过字面量赋值创建字符串（如：String str=”twm”）时，会先在常量池中查找是否存在相同的字符串，
             若存在，则将栈中的引用直接指向该字符串；若不存在，则在常量池中生成一个字符串，
             再将栈中的引用指向该字符串。 */

            String e = "123" + new String("456");
            // 创建了4个对象
            // 常量池对象"123" ,"456"，new String("456")创建堆对象，还有一个堆对象"123456"。

            String str2 = new String("str") + new String("01");
            str2.intern();
            String str1 = "str01";
            System.out.println(str2 == str1);

            // 当执行str2.intern();时，因为常量池中没有“str01”这个字符串，所以会在常量池中生成一个
            // 对堆中的“str01”的引用(注意这里是引用 ，就是这个区别于JDK 1.6的地方。在JDK1.6下是生成
            // 原字符串的拷贝)，而在进行String str1 = “str01”; 字面量赋值的时候，常量池中已经存在一个引用，
            // 所以直接返回了该引用，因此str1和str2都指向堆中的同一个字符串，返回true。

            String str22 = new String("str") + new String("01");
            String str11 = "str01";
            str22.intern();
            System.out.println(str22 == str11);

            // 将中间两行调换位置以后，因为在进行字面量赋值（String str1 = “str01″）的时候，常量池中不存在，
            // 所以str1指向的常量池中的位置，而str2指向的是堆中的对象，再进行intern方法时，
            // 对str1和str2已经没有影响了，所以返回false。

//            String s1 = “abc”;
//            String s2 = “a”;
//            String s3 = “bc”;
//            String s4 = s2 + s3;
//            System.out.println(s1 == s4);
            // A：false，因为s2+s3实际上是使用StringBuilder.append来完成，会生成不同的对象。

//            String s1 = “abc”;
//            final String s2 = “a”;
//            final String s3 = “bc”;
//            String s4 = s2 + s3;
//            System.out.println(s1 == s4);
            // A：true，因为final变量在编译后会直接替换成对应的值，所以实际上等于s4=”a”+”bc”，而这种情况下，编译器会直接合并为s4=”abc”，所以最终s1==s4。

            String a = new String("a");
            String a1 = a.intern();
            String b = "a";
            System.out.println(a == b);
            System.out.println(a1 == b);
        }
    }
}
