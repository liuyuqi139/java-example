package flux;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Flux测试
 */
public class FluxTest {
    public static void main(String[] args) {
        // 声明数据流
        Flux.just(1, 2, 3, 4, 5, 6);
        Mono.just(1);

        // 基于数组、集合和Stream生成
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6};
        Flux.fromArray(array);
        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);
        Stream<Integer> stream = list.stream();
        Flux.fromStream(stream);
    }

    @Test
    public void example1() {
//        Flux.just(1, 2, 3, 4, 5, 6).subscribe(System.out::print);
//        System.out.println();
//        Mono.just(1).subscribe(System.out::println);

        Flux.just(1, 2, 3, 4, 5).subscribe(new Subscriber<Integer>() { // 1 Subscriber通过匿名内部类定义，其中需要实现接口的四个方法

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("onSubscribe");
//                s.request(6);   // 2 订阅时请求6个元素。
//                s.request(5);   // 2 订阅时请求5个元素。
//                s.request(3);   // 2 订阅时请求3个元素。，不会触发onComplete
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext:" + integer);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }

    @Test
    public void example2() {
        Flux.just(1, 2, 3, 4, 5, 6).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Completed!"));
    }

    @Test
    public void example3() {
        Mono.error(new Exception("some error")).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Completed!")
        );
    }

    /**
     * map - 元素映射为新元素
     * <p>
     * Flux.range(1, 6)用于生成从“1”开始的，自增为1的“6”个整型数据；
     * map接受lambdai -> i * i为参数，表示对每个数据进行平方；
     * 验证新的序列的数据；
     * verifyComplete()相当于expectComplete().verify()。
     */
    @Test
    public void example4() {
        StepVerifier.create(Flux.range(1, 6)    // 1
                .map(i -> i * i))   // 2
                .expectNext(1, 4, 9, 16, 25, 36)    //3
                .verifyComplete();  // 4

    }

    /**
     * flatMap - 元素映射为流
     * <p>
     * 对于每一个字符串s，将其拆分为包含一个字符的字符串流；
     * 对每个元素延迟100ms；
     * 对每个元素进行打印（注doOnNext方法是“偷窥式”的方法，不会消费数据流）；
     * 验证是否发出了8个元素
     */
    @Test
    public void example5() {
        StepVerifier.create(
                Flux.just("flux", "mono")
                        .flatMap(s -> Flux.fromArray(s.split("\\s*"))   // 1
                                .delayElements(Duration.ofMillis(100))) // 2
                        .doOnNext(System.out::print)) // 3
                .expectNextCount(8) // 4
                .verifyComplete();
    }

    /**
     * filter - 过滤
     * <p>
     * filter的lambda参数表示过滤操作将保留奇数；
     * 验证仅得到奇数的平方。
     */
    @Test
    public void example6() {
        StepVerifier.create(Flux.range(1, 6)
                .filter(i -> i % 2 == 1)    // 1
                .map(i -> i * i))
                .expectNext(1, 9, 25)   // 2
                .verifyComplete();

    }

    private Flux<String> getZipDescFlux() {
        String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
        return Flux.fromArray(desc.split("\\s+"));  // 1
    }

    /**
     * zip - 一对一合并
     * <p>
     * 将英文说明用空格拆分为字符串流；
     * 定义一个CountDownLatch，初始为1，则会等待执行1次countDown方法后结束，不使用它的话，测试方法所在的线程会直接返回而不会等待数据流发出完毕；
     * 使用Flux.interval声明一个每200ms发出一个元素的long数据流；因为zip操作是一对一的，故而将其与字符串流zip之后，字符串流也将具有同样的速度；
     * zip之后的流中元素类型为Tuple2，使用getT1方法拿到字符串流的元素；定义完成信号的处理为countDown;
     * countDownLatch.await(10, TimeUnit.SECONDS)会等待countDown倒数至0，最多等待10秒钟。
     */
    @Test
    public void testSimpleOperators() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);  // 2
        Flux.zip(
                getZipDescFlux(),
                Flux.interval(Duration.ofMillis(200)))  // 3
                .subscribe(t -> System.out.println(t.getT1()), null, countDownLatch::countDown);    // 4
        countDownLatch.await(10, TimeUnit.SECONDS);     // 5
    }


    private String getStringSync() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello, Reactor!";
    }

    /**
     * 将同步的阻塞调用变为异步的
     * <p>
     * 使用fromCallable声明一个基于Callable的Mono；
     * 使用subscribeOn将任务调度到Schedulers内置的弹性线程池执行，弹性线程池会为Callable的执行任务分配一个单独的线程
     */
    @Test
    public void testSyncToAsync() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromCallable(() -> getStringSync())    // 1
                .subscribeOn(Schedulers.elastic())  // 2
                .subscribe(System.out::println, null, countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    /**
     * 使用SynchronousSink生成数据流
     * 1用于计数；
     * 2向“池子”放自定义的数据；
     * 3告诉generate方法，自定义数据已发完；
     * 4触发数据流。
     */
    @Test
    public void testGenerate1() {
        final AtomicInteger count = new AtomicInteger(1);   // 1
        Flux.generate(sink -> {
            sink.next(count.get() + " : " + new Date());   // 2
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count.getAndIncrement() >= 5) {
                sink.complete();     // 3
            }
        }).subscribe(System.out::println);  // 4
    }

    /**
     * 1初始化状态值；
     * 2第二个参数是BiFunction，输入为状态和sink；
     * 3每次循环都要返回新的状态值给下次使用。
     */
    @Test
    public void testGenerate2() {
        Flux.generate(
                () -> 1,    // 1
                (count, sink) -> {      // 2
                    sink.next(count + " : " + new Date());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count >= 5) {
                        sink.complete();
                    }
                    return count + 1;   // 3
                }).subscribe(System.out::println);
    }

    /**
     * 1初始化状态值；
     * 2第二个参数是BiFunction，输入为状态和sink；
     * 3每次循环都要返回新的状态值给下次使用。
     * 4最后将count值打印出来。
     */
    @Test
    public void testGenerate3() {
        Flux.generate(
                () -> 1,    // 1
                (count, sink) -> {      // 2
                    sink.next(count + " : " + new Date());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (count >= 5) {
                        sink.complete();
                    }
                    return count + 1;   // 3
                }, System.out::println) //4
                .subscribe(System.out::println);
    }

    private Flux<Integer> generateFluxFrom1To6() {
        return Flux.just(1, 2, 3, 4, 5, 6);
    }

    private Mono<Integer> generateMonoWithError() {
        return Mono.error(new Exception("some error"));
    }

    /**
     * 如何测试
     */
    @Test
    public void testViaStepVerifier() {
        StepVerifier.create(generateFluxFrom1To6())
                .expectNext(1, 2, 3, 4, 5, 6)
                .expectComplete()
                .verify();
        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("some error")
                .verify();
    }
}
