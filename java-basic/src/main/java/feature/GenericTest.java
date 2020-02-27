package feature;

import java.util.Arrays;
import java.util.List;

/**
 * 泛型
 * <pre>
 *  <? extends Parent> 指定了泛型类型的上届，可以取元素而不能添加，这个叫get原则
 *  <? super Child> 指定了泛型类型的下届，可以添加元素但是没法取出来，这个叫put原则
 *  <?> 指定了没有限制的泛型类型
 * </pre>
 */
public class GenericTest {
    public static class GenericMethod {
        private static int add(int a, int b) {
            System.out.println(a + "+" + b + "=" + (a + b));
            return a + b;
        }

        private static <T> T genericAdd(T a, T b) {
            System.out.println(a + "+" + b + "=" + a + b);
            return a;
        }

//        报错
//        private T genericReduce(T a, T b) {
//            System.out.println(a + "-" + b + "=" + a + b);
//            return a;
//        }

        public static void main(String[] args) {
            GenericMethod.add(1, 2);
            GenericMethod.<String>genericAdd("a", "b");
            GenericMethod.genericAdd(1, 2);
        }
    }

    public static class GenericClass<T> {
        private T genericAdd(T a, T b) {
            System.out.println(a + "+" + b + "=" + a + b);
            return a;
        }

        private static <T> T genericReduce(T a, T b) {
            System.out.println(a + "-" + b + "=" + a + b);
            return a;
        }

        public static void main(String[] args) {
            GenericClass.<String>genericReduce("1", "2");
            GenericClass.genericReduce(1, 2);
            GenericClass<java.io.Serializable> genericClass = new GenericClass<>();
            genericClass.genericAdd(1, 2);
            genericClass.genericAdd("1", "2");
        }
    }

    /**
     * 类型变量的限定-方法
     */
    public static class TypeLimitForMethod {

        /**
         * 计算最小值
         * 如果要实现这样的功能就需要对泛型方法的类型做出限定
         */
//    private static <T> T getMin(T a, T b) {
//        return (a.compareTo(b) > 0) ? a : b;
//    }

        /**
         * 限定类型使用extends关键字指定
         * 可以使类，接口，类放在前面接口放在后面用&符号分割
         * 例如：<T extends ArrayList & Comparable<T> & Serializable>
         */
        public static <T extends Comparable<T>> T getMin(T a, T b) {
            return (a.compareTo(b) < 0) ? a : b;
        }

        public static void main(String[] args) {
            System.out.println(TypeLimitForMethod.getMin(2, 4));
            System.out.println(TypeLimitForMethod.getMin("r", "a"));
        }
    }

    /**
     * <pre>
     *  <? extends Parent> 指定了泛型类型的上届，可以取元素而不能添加，这个叫get原则
     *  <? super Child> 指定了泛型类型的下届，可以添加元素但是没法取出来，这个叫put原则
     * </pre>
     */
    public static class TypeLimitForMethod2 {
        public static void main(String[] args) {
            List<? extends Integer> numbers = Arrays.asList(1, 2);
//            numbers.add(1); // 会报错
            numbers.forEach(System.out::println);

            List<? super Number> numberList = Arrays.asList(1, 2);
//            Number n = numberList.get(1); // 会报错
            numberList.forEach(System.out::println);
        }
    }
}
