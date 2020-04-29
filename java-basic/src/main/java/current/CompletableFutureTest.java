package current;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * CompletableFuture是java8中引入的机制，为了弥补异步执行时Future机制的不足
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
        CompletableFuture<Void> completableFuture1 = CompletableFuture.supplyAsync(() -> 1)
                // 转换
                .thenApply(i -> 1 + 1)
                // 消耗
                .thenAccept(i -> System.out.println(i));

        CompletableFuture<Void> completableFuture2 = CompletableFuture.supplyAsync(() -> 1)
                // 无参消耗
                .thenRun(() -> System.out.println("thenRun"));

        CompletableFuture<Integer> completableFuture3 = CompletableFuture.supplyAsync(() -> {
            return 1;
        });
        CompletableFuture<Void> completableFuture4 = CompletableFuture.runAsync(() -> System.out.println("runAsync"));
    }

    @Test
    public void testMany() throws InterruptedException, ExecutionException {
        List<CompletableFuture<Integer>> jobs = Lists.newArrayList(
                CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }),
                CompletableFuture.supplyAsync(() -> {
                    return 2;
                })
        );
        CompletableFuture.allOf(
                jobs.toArray(new CompletableFuture[jobs.size()])
        ).thenRun(()->{
            int result = jobs.stream().map(c-> {
                try {
                    int tt = c.get();
                    System.out.println("tt：" + tt);
                    return tt;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return -99;
                }
            }).reduce(0, (sum, i)->sum+i);
            System.out.println(result);
        });

        System.out.println("#########");
        int result2 = (int) CompletableFuture.anyOf(
                jobs.toArray(new CompletableFuture[jobs.size()])
        ).get();
        System.out.println(result2);
        // CompletableFuture是守护线程，当主线程结束，守护线程也跟着结束，解决办法传入Executor
        Thread.sleep(1000);
    }

    //任务一
    public String do3Second() {
        try {
            System.out.println ("正在执行3秒的方法");
            TimeUnit.SECONDS.sleep (3);
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return Thread.currentThread ().getName () + "##3";
    }
    //任务二
    public String do5Second() {
        try {
            System.out.println ("正在执行5秒的方法");
            TimeUnit.SECONDS.sleep (5);
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return Thread.currentThread ().getName () + "##5";
    }

    /**
     * 模拟业务场景：处理耗时任务，它会以最长的业务时间返回，不是响应式
     */
    @Test
    public void ExecutorServiceTest() throws Exception{
        long start = System.currentTimeMillis ();
        CompletableFutureTest demo = new CompletableFutureTest ();
        ExecutorService executorService = Executors.newFixedThreadPool (20);
        List<Callable<String>> callList = new ArrayList<>();
        for (int value = 0; value < 5; value++) {
            callList.add (() -> demo.do3Second ());
            callList.add (() -> demo.do5Second ());
        }
        //把所有的任务放入到线程池里
        List<Future<String>> futures =  executorService.invokeAll (callList);
        futures.forEach (strFture ->{
            while (true){
                //只有当所有任务执行了，返回结果集
                if (strFture.isDone ()){
                    String s = null;
                    try {
                        s = strFture.get ();
                        System.out.println (s + "_耗时：" + (System.currentTimeMillis () - start));
                        break;
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace ();
                    }
                }
            }
        });
        System.out.println ("阻塞此处 执行完毕");
        executorService.shutdown ();
    }

    /**
     * 模拟业务场景：处理耗时任务，处理完一个任务返回一个任务结果，响应式
     */
    @Test
    public void CompletableFutureTest() {
        long start = System.currentTimeMillis ();
        CompletableFutureTest demo = new CompletableFutureTest ();
        AtomicInteger num = new AtomicInteger ();
        ExecutorService executorService = Executors.newFixedThreadPool (20);
        IntStream.range (0,5).forEach (value -> {
            CompletableFuture.supplyAsync (() -> {
                return demo.do3Second ();
            },executorService).whenComplete ((resultStr, throwable) -> {
                num.addAndGet (1);
                System.out.println (resultStr + "_耗时：" + (System.currentTimeMillis () - start));
            });
            CompletableFuture.supplyAsync (() -> {
                return demo.do5Second ();
            },executorService).whenComplete ((resultStr, throwable) -> {
                num.addAndGet (1);
                System.out.println (resultStr + "_耗时：" + (System.currentTimeMillis () - start));
            });
        });

        System.out.println ("非阻塞 执行完毕");
        while (true){
            if (num.get () == 10){
                System.out.println ("任务执行完毕！");
                executorService.shutdown ();
                break;
            }

        }
    }
}
