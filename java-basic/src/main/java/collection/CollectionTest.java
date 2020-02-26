package collection;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            if (roleId == 2L){
                return;
            }
            log.info("roleId：{}", roleId);
        });

        for (Long roleId : roleIds) {
            if (roleId == 2L){
                continue;
            }
            log.info("roleId：{}", roleId);
        }

        for (Long roleId : roleIds) {
            if (roleId == 2L){
                break;
            }
            log.info("roleId：{}", roleId);
        }

        for (Long roleId : roleIds) {
            if (roleId == 2L){
                return;
            }
            log.info("roleId：{}", roleId);
        }

    }
}