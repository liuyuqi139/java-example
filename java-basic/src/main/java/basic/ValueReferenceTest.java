package basic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import util.json.JSONUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 值引用测试
 */
@Slf4j
public class ValueReferenceTest {
    @Data
    static class Student {
        private Long id;
        private String name;
        private Integer age;
    }

    public static void main(String[] args) {
        Student student = new Student();
        student.setId(1L);
        student.setName("张三");
        student.setAge(10);

        Student student2 = student;
        student2.setName("李四");

        log.info("student：{}", JSONUtils.json(student));
        log.info("student2：{}", JSONUtils.json(student2));

        Student student3 = new Student();
        student3 = student2;
        student3.setName("王五");

        log.info("student：{}", JSONUtils.json(student));
        log.info("student2：{}", JSONUtils.json(student2));
        log.info("student3：{}", JSONUtils.json(student3));
    }
}

@Slf4j
class ListValueReferenceTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        ListValueReferenceTest test = new ListValueReferenceTest();

        test.add(list);
        list.forEach(System.out::println);

        test.add2(list);
        list.forEach(System.out::println);

        test.add3(list);
        list.forEach(System.out::println);
    }

    private void add(List<Integer> list) {
        list.add(1);
    }

    private void add2(List<Integer> list) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(3);

        list = integerList;
    }

    private void add3(List<Integer> list) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(3);

        list.addAll(integerList);
        list.addAll(null);  //NullPointerException
    }
}

@Slf4j
class MapValueReferenceTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        MapValueReferenceTest test = new MapValueReferenceTest();
        test.add(map);

        map.forEach((key, value) -> System.out.println(key + "-" + value));

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        }
    }

    private void add(Map<String, String> map) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("1", "11");
        stringMap.put("2", "22");
        stringMap.put("3", "33");

        map.putAll(stringMap);
    }
}