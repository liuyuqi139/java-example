package controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 多例模式
 * <p>多个线程请求同一个Controller类中的同一个方法，线程之间是不互相影响的</p>
 * <p>createTime：2019-03-28 11:39:54
 *
 * @author liuyq
 * @since 1.0
 */
@Scope("prototype")
@RestController
@RequestMapping("/prototypeTest")
public class PrototypeControllerTest {
    private int a = 1;

    private Map<String, String> map = new HashMap<>();

    // 多例：项目启动不会走构造方法，每次请求走构造方法
    public PrototypeControllerTest() {
        map.put("a", "123");
    }

    @RequestMapping(value = "/test1")
    public String test1(@RequestParam String str) {
        if ("open".equals(str)) {
            try {
                Thread.sleep(5000);
                return "sleep on";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    @GetMapping(value = "/test2")
    public int test2(@RequestParam int a) {
        if (1 == a) {
            this.a += a;
        }
        return this.a;
    }

    @RequestMapping(value = "/test3")
    public String test3(@RequestParam String str) {
        if ("open".equals(str)) {
            try {
                Thread.sleep(5000);
                map.put("a", "456");
                return map.get("a");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return map.get("a");
    }
}
