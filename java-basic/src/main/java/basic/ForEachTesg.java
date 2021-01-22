package basic;

import java.util.Arrays;
import java.util.List;

/**
 * forEach
 */
public class ForEachTesg {
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
