package flux;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 回压测试
 */
public class BackPressureTest {
    /**
     * 1. Flux.range是一个快的Publisher；
     * 2. 在每次request的时候打印request个数；
     * 3. 通过重写BaseSubscriber的方法来自定义Subscriber；
     * 4. hookOnSubscribe定义在订阅的时候执行的操作；
     * 5. 订阅时首先向上游请求1个元素；
     * 6. hookOnNext定义每次在收到一个元素的时候的操作；
     * 7. sleep 1秒钟来模拟慢的Subscriber；
     * 8. 打印收到的元素；
     * 9. 每次处理完1个元素后再请求1个。
     */
    @Test
    public void testBackpressure() {
        Flux.range(1, 6)    // 1
//                .log()
                .doOnRequest(n -> System.out.println("Request " + n + " values..."))    // 2
                .subscribe(new BaseSubscriber<Integer>() {  // 3
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) { // 4
                        System.out.println("Subscribed and make a request...");
                        request(1); // 5
                    }

                    @Override
                    protected void hookOnNext(Integer value) {  // 6
                        try {
                            TimeUnit.SECONDS.sleep(1);  // 7
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Get value [" + value + "]");    // 8
                        request(1); // 9
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("完成");
                    }
                });
    }

    /**
     * 1事件源；
     * 2向事件源注册用匿名内部类创建的监听器；
     * 3监听器在收到事件回调的时候通过sink将事件再发出；
     * 4监听器再收到事件源停止的回调的时候通过sink发出完成信号；
     * 5触发订阅（这时候还没有任何事件产生）；
     * 6循环产生20个事件，每个间隔不超过1秒的随机时间；
     * 7最后停止事件源。
     */
    @Test
    public void testCreate() throws InterruptedException {
        MyEventSource eventSource = new MyEventSource();    // 1
        Flux.create(sink -> {
                    eventSource.register(new MyEventListener() {    // 2
                        @Override
                        public void onNewEvent(MyEventSource.MyEvent event) {
                            sink.next(event);       // 3
                        }

                        @Override
                        public void onEventStopped() {
                            System.out.println("complete");
                            sink.complete();        // 4
                        }
                    });
                }
        ).subscribe(System.out::println);       // 5

        for (int i = 0; i < 20; i++) {  // 6
            Random random = new Random();
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            eventSource.newEvent(new MyEventSource.MyEvent(new Date(), "Event-" + i));
        }
        eventSource.eventStopped(); // 7
    }

    private final int EVENT_DURATION = 10;    // 生成的事件间隔时间，单位毫秒
    private final int EVENT_COUNT = 20;    // 生成的事件个数
    private final int PROCESS_DURATION = 30;    // 订阅者处理每个元素的时间，单位毫秒

    private Flux<MyEventSource.MyEvent> fastPublisher;
    private SlowSubscriber slowSubscriber;
    private MyEventSource eventSource;
    private CountDownLatch countDownLatch;

    /**
     * 准备工作。
     */
    @Before
    public void setup() {
        countDownLatch = new CountDownLatch(1);
        slowSubscriber = new SlowSubscriber();
        eventSource = new MyEventSource();
    }

    /**
     * 触发订阅，使用CountDownLatch等待订阅者处理完成。
     */
    @After
    public void subscribe() throws InterruptedException {
        fastPublisher.subscribe(slowSubscriber);
        generateEvent(EVENT_COUNT, EVENT_DURATION);
        countDownLatch.await(1, TimeUnit.MINUTES);
    }


    /**
     * 测试create方法的不同OverflowStrategy的效果。
     */
    @Test
    public void testCreateBackPressureStratety() {
        fastPublisher =
                createFlux(FluxSink.OverflowStrategy.IGNORE)    // BUFFER/DROP/LATEST/ERROR/IGNORE
                        .doOnRequest(n -> System.out.println("         ===  request: " + n + " ==="))
                        .publishOn(Schedulers.newSingle("newSingle"), 1);
    }

    /**
     * 测试不同的onBackpressureXxx方法的效果。
     */
    @Test
    public void testOnBackPressureXxx() {
        fastPublisher = createFlux(FluxSink.OverflowStrategy.BUFFER)
//                .onBackpressureDrop()
                .onBackpressureBuffer()
//                .onBackpressureLatest()
//                .onBackpressureError()
                .doOnRequest(n -> System.out.println("         ===  request: " + n + " ==="))
                .publishOn(Schedulers.newSingle("newSingle"), 1);
    }

    /**
     * 其他可以实现类似效果的操作符。
     */
    @Test
    public void testSimilarOperators() {
        fastPublisher = createFlux(FluxSink.OverflowStrategy.BUFFER)
                .doOnRequest(n -> System.out.println("         ===  request: " + n + " ==="))
                .sample(Duration.ofMillis(30))
        ;
    }

    /**
     * 使用create方法生成“快的发布者”。
     *
     * @param strategy 回压策略
     * @return Flux
     */
    private Flux<MyEventSource.MyEvent> createFlux(FluxSink.OverflowStrategy strategy) {
        return Flux.create(sink -> eventSource.register(new MyEventListener() {
            @Override
            public void onNewEvent(MyEventSource.MyEvent event) {
                System.out.println("publish >>> " + event.getMessage());
                sink.next(event);
            }

            @Override
            public void onEventStopped() {
                sink.complete();
            }
        }), strategy);
    }

    /**
     * 生成MyEvent。
     *
     * @param count  生成MyEvent的个数。
     * @param millis 每个MyEvent之间的时间间隔。
     */
    private void generateEvent(int count, int millis) {
        for (int i = 0; i < count; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep(millis);
            } catch (InterruptedException e) {
            }
            eventSource.newEvent(new MyEventSource.MyEvent(new Date(), "Event-" + i));
        }
        eventSource.eventStopped();
    }

    /**
     * 内部类，“慢的订阅者”。
     */
    class SlowSubscriber extends BaseSubscriber<MyEventSource.MyEvent> {

        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            System.out.println("                        hookOnSubscribe：" + Thread.currentThread());
            request(1);
        }

        @Override
        protected void hookOnNext(MyEventSource.MyEvent event) {
            System.out.println("                      receive <<< " + event.getMessage());
            System.out.println("                        hookOnNext：" + Thread.currentThread());
            try {
                TimeUnit.MILLISECONDS.sleep(PROCESS_DURATION);
            } catch (InterruptedException e) {
            }
            request(1);
        }

        @Override
        protected void hookOnError(Throwable throwable) {
            System.err.println("                      receive <<< " + throwable);
        }

        @Override
        protected void hookOnComplete() {
            countDownLatch.countDown();
        }
    }
}
