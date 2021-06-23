package basic;

import lombok.extern.slf4j.Slf4j;
import util.json.JSONUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Slf4j
public class LogTest {
    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        log.info("list：{}", list);  //[1, 2]
        log.info("list：{}", list.toString());   //[1, 2]
        log.info("list：{}", JSONUtils.json(list));  //[1,2]

        List<String> listString = new ArrayList<>();
        listString.add("a");
        listString.add("b");
        log.info("listString：{}", listString);  //[a, b]
        log.info("listString：{}", JSONUtils.json(listString));  //["a","b"]
        log.info("listString：{}", String.join(",", listString));    //a,b
    }
}
