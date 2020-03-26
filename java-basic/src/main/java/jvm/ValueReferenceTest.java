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
}
