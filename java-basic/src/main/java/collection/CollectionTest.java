package collection;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import util.json.JSONUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 集合相关测试
 * <p>createTime：2019-05-28 15:55:29
 *
 * @author liuyq
 * @since 1.0
 */
public class CollectionTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("", "");
        map.put("1", "");
        map.put("2", "");
        map.forEach((key, value) -> System.out.println(key + "：" + value));
        map.forEach((key, value) -> {
            if ("2".equals(key)) {
                map.put("2", "4");
            }
        });

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        List<Integer> list2 = list.stream().map(Integer::new).collect(Collectors.toList());
        // 等同上一行
//        List<Integer> list2 = list.stream().map(integer -> new Integer(integer)).collect(Collectors.toList());
        list2.forEach(System.out::println);
        List<String> list3 = list.stream().map(integer -> {
            if (integer % 2 == 0) {
                return integer.toString();
            } else {
                return null;
            }
        }).collect(Collectors.toList());
        list3.forEach(System.out::println);

        List<Integer> list1 = list.stream().filter(integer -> integer % 2 == 0).collect(Collectors.toList());
        list1.forEach(System.out::println);
        // 等同上一行
//        list1.forEach(integer -> System.out.println(integer));

        List<Integer> list4 = new ArrayList<>();
        list4.add(1);
        list4.add(2);
        list4.add(3);
        list4.add(3);
        list4 = list4.stream().distinct().collect(Collectors.toList());
        System.out.println(list4);

        List<String> list5 = new ArrayList<>();
        list5.add("11");
        list5.add("22");
        list5.add("33");
        list5.add("33");
        list5 = list5.stream().distinct().collect(Collectors.toList());
        System.out.println(list5);
    }
}

@Slf4j
class CollectionForeachTest {
    public static void main(String[] args) {
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(1L);
        roleIds.add(2L);
        roleIds.add(3L);
        roleIds.add(4L);

        roleIds.forEach(roleId -> {
            if (roleId == 2L) {
                return;
            }
            log.info("roleId1：{}", roleId);
        });

        for (Long roleId : roleIds) {
            if (roleId == 2L) {
                continue;
            }
            log.info("roleId2：{}", roleId);
        }

        for (Long roleId : roleIds) {
            if (roleId == 2L) {
                break;
            }
            log.info("roleId3：{}", roleId);
        }

        for (Long roleId : roleIds) {
            if (roleId == 2L) {
                return;
            }
            log.info("roleId4：{}", roleId);
        }

        for (Long roleId : roleIds) {
            log.info("roleId5：{}", roleId);
        }
    }
}

@Slf4j
class CollectionSortTest {
    @Data
    public static class BookVersionAggrVo {
        private Long id;

        private String name;

        private boolean availableSale = false;

        private Long courseId;
    }

    public static void main(String[] args) {
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(2L);
        roleIds.add(1L);
        roleIds.add(4L);
        roleIds.add(3L);

        roleIds.sort(Comparator.comparingLong(Long::longValue));

        roleIds.forEach(roleId -> {
            log.info("roleId1：{}", roleId);
        });

        roleIds.sort(Comparator.comparingLong(Long::longValue).reversed());

        roleIds.forEach(roleId -> {
            log.info("roleId1：{}", roleId);
        });

        roleIds.sort(Comparator.comparing(Long::longValue));

        roleIds.forEach(roleId -> {
            log.info("roleId1：{}", roleId);
        });

        List<String> orderNoList = new ArrayList<>();
        orderNoList.add("C74442020123013394614190");
        orderNoList.add("C20492020123012545716992");
        orderNoList.add("8263020123010470069517");
        orderNoList.add("Z8263020123010470069517");
        orderNoList.sort(String::compareTo);
        orderNoList.forEach(log::info);

        orderNoList.sort(Comparator.comparing(String::new));
        orderNoList.forEach(log::info);

        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(1);
        integerList.add(4);
        integerList.add(3);
        //从小到大
        integerList.sort((o1, o2) -> {
            if (o1 > o2) {
                return 1;
            } else if (o1 < o2) {
                return -1;
            } else {
                return 0;
            }
        });
        integerList.forEach(roleId -> {
            log.info("custom sort：{}", roleId);
        });

        //从大到小
        integerList.sort((o1, o2) -> {
            if (o1 > o2) {
                return -1;
            } else if (o1 < o2) {
                return 1;
            } else {
                return 0;
            }
        });
        integerList.forEach(roleId -> {
            log.info("custom sort2：{}", roleId);
        });

        List<BookVersionAggrVo> bookVersionAggrVoList = new ArrayList<>();
        BookVersionAggrVo bookVersionAggrVo = new BookVersionAggrVo();
        bookVersionAggrVo.setId(1L);
        bookVersionAggrVo.setAvailableSale(true);
        bookVersionAggrVoList.add(bookVersionAggrVo);

        bookVersionAggrVo = new BookVersionAggrVo();
        bookVersionAggrVo.setId(2L);
        bookVersionAggrVo.setAvailableSale(false);
        bookVersionAggrVoList.add(bookVersionAggrVo);

        bookVersionAggrVo = new BookVersionAggrVo();
        bookVersionAggrVo.setId(3L);
        bookVersionAggrVo.setAvailableSale(true);
        bookVersionAggrVoList.add(bookVersionAggrVo);

        bookVersionAggrVoList.sort(Comparator.comparing(BookVersionAggrVo::isAvailableSale).reversed());
        bookVersionAggrVoList.forEach(bookVersionAggr -> {
            log.info("bookVersionAggr：{}", JSONUtils.json(bookVersionAggr));
        });
    }
}

@Slf4j
class CollectionCalculateTest {
    public static void main(String[] args) {
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(2L);
        roleIds.add(1L);
        roleIds.add(4L);
        roleIds.add(3L);

        long sum = roleIds.stream().mapToLong(roleId -> roleId).sum();
        System.out.println(sum);

        long reduce = roleIds.stream().reduce(Long::sum).get();
        System.out.println(reduce);


        List<BigDecimal> bigDecimals = new ArrayList<>();
        bigDecimals.add(new BigDecimal("1"));
        bigDecimals.add(new BigDecimal("2"));
        BigDecimal bigDecimal = bigDecimals.stream().reduce(BigDecimal::add).get();
        System.out.println(bigDecimal);
    }
}

@Slf4j
class MapTest {
    public static void main(String[] args) {
        Map<Long, String> map = new HashMap<>();
        map.put(3L, "3");
        map.put(1L, "1");
        map.put(5L, "5");
        map.put(6L, "6");
        map.put(7L, "7");

        System.out.println(map.get(9L));

        map.forEach((aLong, s) -> {
            log.info("key：{}, value：{}", aLong, s);
        });

        List<Long> collect = map.keySet().stream().collect(Collectors.toList());
        log.info("collect：{}", collect);
        //同上
        List<Long> collect2 = new ArrayList<>(map.keySet());
        log.info("collect2：{}", collect2);

        map.entrySet().forEach(longStringEntry -> {
            log.info("key：{}, value：{}", longStringEntry.getKey(), longStringEntry.getValue());
        });
        //同上
        map.forEach((key, value) -> log.info("key：{}, value：{}", key, value));
    }
}

@Slf4j
class TreeMapTest {
    public static void main(String[] args) {
        Map<Long, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(2L, "2");
        linkedHashMap.put(3L, "3");
        linkedHashMap.put(1L, "1");

        linkedHashMap.forEach((aLong, s) -> {
            log.info("key：{}, value：{}", aLong, s);
        });

        //乍一看，好像HashMap会根据键值进行排序。其实这是在数据量少时出现的偶然情况
        Map<Long, String> map = new HashMap<>();
        map.put(3L, "3");
        map.put(1L, "1");
        map.put(5L, "5");
        map.put(6L, "6");
        map.put(7L, "7");

        map.forEach((aLong, s) -> {
            log.info("key：{}, value：{}", aLong, s);
        });

        Map<Long, String> treeMap = new TreeMap<>();
        treeMap.put(3L, "3");
        treeMap.put(1L, "1");
        treeMap.put(5L, "5");
        treeMap.put(6L, "6");
        treeMap.put(7L, "7");

        treeMap.forEach((aLong, s) -> {
            log.info("key：{}, value：{}", aLong, s);
        });

        Map<String, String> map2 = new HashMap<>();
        map2.put("d", "3");
        map2.put("e", "1");
        map2.put("f", "5");
        map2.put("c", "6");
        map2.put("a", "7");

        map2.forEach((aLong, s) -> {
            log.info("key：{}, value：{}", aLong, s);
        });

        Map<String, String> treeMap2 = new TreeMap<>();
        treeMap2.put("d", "3");
        treeMap2.put("e", "1");
        treeMap2.put("f", "5");
        treeMap2.put("c", "6");
        treeMap2.put("a", "7");

        treeMap2.forEach((aLong, s) -> {
            log.info("key：{}, value：{}", aLong, s);
        });
    }
}

@Slf4j
class LinkedHashMapTest {
    public static void main(String[] args) {
        Map<Long, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(2L, "2");
        linkedHashMap.put(3L, "3");
        linkedHashMap.put(1L, "1");

        //有序输出
        linkedHashMap.forEach((aLong, s) -> {
            log.info("key：{}, value：{}", aLong, s);
        });

        //groupingBy有序用LinkedHashMap
        //key：学年-学期-期次，按照学年、学期、期次分组
//        Map<String, List<CourseInfoDto>> schoolYearPeriodTeachingTimeMap = courseInfoList.stream().collect(Collectors.groupingBy(
//                courseInfoDto -> courseInfoDto.getSchoolYear() + "-" + courseInfoDto.getCoursePeriodDtoList().get(0).getPeriod() + "-" + courseInfoDto.getTeachingTime(),
//                LinkedHashMap::new, Collectors.toList()
//        ));
    }
}

@Slf4j
class ListTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.forEach(System.out::println);

//        list.clear();
//        list.forEach(System.out::println);

//        list.add(4);
        list.forEach(System.out::println);

        //UnsupportedOperationException
//        List<String> empty = Collections.EMPTY_LIST;
//        empty.add("1");

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(3);
        list2.add(2);

        System.out.println(list.containsAll(list2));    //true

        List<String> list3 = new ArrayList<>();
        list3.add("a");
        list3.add("b");
        list3.add("c");
        list3.add("d");

        List<String> list4 = new ArrayList<>();
        list4.add("a");
        list4.add("c");
        list4.add("b");
        System.out.println(list3.containsAll(list4));   //true
        System.out.println(list4.containsAll(list3));   //false

        List<String> list5 = new ArrayList<>(list4);
        System.out.println(list5);
        System.out.println(list5.toString());
        List<String> list6 = new ArrayList<>(null); //NullPointerException
    }
}

@Slf4j
class ListArraysTest {
    //Arrays.asList() 返回java.util.Arrays$ArrayList， 而不是ArrayList。Arrays$ArrayList和ArrayList都是继承AbstractList，remove，add等
    // method在AbstractList中是默认throw UnsupportedOperationException而且不作任何操作。ArrayList override这些method来对list进行操作，
    // 但是Arrays$ArrayList没有override remove(int)，add(int)等，所以throw UnsupportedOperationException
    public static void main(String[] args) {
        String[] myArray = { "Apple", "Banana", "Orange" };
        List<String> myList = Arrays.asList(myArray);
        for (String str : myArray) {
            //UnsupportedOperationException
            myList.add(str);
            myList.add(str);
        }
        System.out.println(myList.size());
    }
}

@Slf4j
class ListStoreTest {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();

        Student student = new Student();
        student.setName("张三");
        student.setAge(23);

        list.add(student);

        student.setName("李四");
        student.setAge(25);

        log.info("list：{}", list);  //name=李四, age=25

        student = new Student();
        student.setName("张三");
        student.setAge(23);

        list.add(student);

        log.info("list：{}", list);  //(name=李四, age=25), (name=张三, age=23)
    }

    @Data
    static class Student {
        private String name;
        private Integer age;
    }
}