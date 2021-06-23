package util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * StringUtilTest
 */
public class StringUtilTest {
    @Test
    public void formatTest() {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);

        System.out.println(String.format("courseId=%s", list));
    }
}
