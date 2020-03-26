package web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * 异步返回
 */
@RestController
@RequestMapping("/Callable")
public class CallableController {

    @RequestMapping("/Callable")
    public Callable<String> asynCallable(){
        System.out.println("主线程开始。。。。。。。。。。。。。。。。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
        Callable<String> callable = () -> {
            System.out.println("副线程开始。。。。。。。。。。。。。。。。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
            Thread.sleep(2000);
            System.out.println("副线程结束。。。。。。。。。。。。。。。。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
            return "Callable<String> asynCallable";
        };
        System.out.println("主线程结束。。。。。。。。。。。。。。。。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
        return callable;
    }
}
