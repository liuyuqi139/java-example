package basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * forEach
 */
public class ForEachTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f");
        list.stream().forEach(item ->{
            if(item.equals("e")){
                return;
            }
            System.out.println(item);
        });
        // 输出结果是：a b c d f

        List<Long> list2 = null;
        for (Long aLong : list2) {

        }
        list2.forEach(aLong -> {

        });
    }
}

class ForEachThrowTest {
    public static void main(String[] args) {
        List<Long> explosiveCourseIds = new ArrayList<>();
        explosiveCourseIds.add(1L);
        explosiveCourseIds.add(2L);
        explosiveCourseIds.add(3L);

        try {
            explosiveCourseIds.forEach(id -> {
                System.out.println(id);
                if (id == 2) {
                    throw new RuntimeException();
                }
            });
        } catch (RuntimeException e) {
            System.out.println("RuntimeException");
        }
    }
}
