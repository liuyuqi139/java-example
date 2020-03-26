package web.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认是单例模式
 * <p>多个线程请求同一个Controller类中的同一个方法，线程之间是不互相影响的</p>
 * <p>createTime：2019-03-28 11:38:14
 *
 * @author liuyq
 * @since 1.0
 */
@Scope("singleton")
@RestController
@RequestMapping("/singletonTest")
public class SingletonControllerTest {
    private int a = 1;

    private Map<String, String> map = new HashMap<>();

    // 单例：项目启动会走构造方法，每次请求不走构造方法
    public SingletonControllerTest() {
        map.put("a", "123");
    }

    @GetMapping(value = "/test")
    public int test() {
        return this.a ++;
    }

    @RequestMapping(value = "/test1")
    public String test1(@RequestParam String str){
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

    @RequestMapping(value = "/test2")
    public int test2(@RequestParam int a){
        if (1 == a) {
            this.a += a;
        }
        return this.a;
    }

    @RequestMapping(value = "/test3")
    public String test3(@RequestParam String str){
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
