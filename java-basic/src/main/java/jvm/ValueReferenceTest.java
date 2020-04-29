package jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * 值引用
 */
public class ValueReferenceTest {
    static class ArrayTest {
        public static void main(String[] args) {
            int [] a = new int[1];
            int [] b = a;
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
        void change(String str) {
            str = "456";
        }

        void change(Map<String, String> map) {
            map.put("a", "456");
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
        }
    }
}
