package flux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class Task {
    private Scheduler scheduler;
    private int parallelism;

    public Task(int parallelism) {
        this.parallelism = parallelism;
        scheduler = Schedulers.newParallel("event", parallelism);
    }

    public static void main(String[] args) {
        Task task = new Task(8);
        new Thread(() -> {
            task.work("任务一");
        }).start();
        new Thread(() -> {
            task.work("任务二");
        }).start();

    }

    public void work(String name) {
        long start = System.currentTimeMillis();
        EventSink sink = this.create(name, o -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (int i = 1; i < 18; i++) {
            sink.submit(new Event<Integer>(i));
        }
        sink.complate();
        System.out.println(name + "等待开始:" + Thread.currentThread().getName());
        sink.complated();
        System.out.println(name + "等待结束:" + Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "耗时：" + (end-start));
    }

    public EventSink create(String name, Consumer consumer) {
        EventSink sink = new EventSink(name, consumer,this.parallelism);
        Flux.<Event>create(fluxSink -> {
            sink.setFluxSink(fluxSink);
        }).parallel(parallelism)
                .runOn(scheduler)
                .subscribe(sink::onConsumer, throwable -> {
                    sink.onException(throwable);
                }, () -> {
                    sink.onComplate();
                });
        return sink;
    }


    public static class EventSink {
        private String name;
        private int parallelism;
        private Consumer consumer;
        private FluxSink<Event> fluxSink;
        private AtomicInteger complateCount = new AtomicInteger(0);
        private AtomicBoolean complate = new AtomicBoolean(false);
        private AtomicBoolean exception = new AtomicBoolean(false);
        private AtomicInteger count = new AtomicInteger(0);
        private AtomicInteger runCount = new AtomicInteger(0);
        private List<Thread> wait = new ArrayList<>();

        public EventSink(String name, Consumer consumer, int parallelism) {
            this.name = name;
            this.consumer = consumer;
            this.parallelism = parallelism;
        }

        public void submit(Event r) {
            fluxSink.next(r);
            count.incrementAndGet();
        }

        public void complate() {
            fluxSink.complete();
            System.out.println(name + "complate:" + Thread.currentThread().getName());
        }

        public void onComplate() {
            System.out.println(name + "onComplate:" + Thread.currentThread().getName());
            if (complateCount.incrementAndGet() == parallelism) {
                System.out.println(name + "unpark:" + Thread.currentThread().getName());
                for (Thread thread : wait) {
                    LockSupport.unpark(thread);
                }
                complate.compareAndSet(false, true);
            }
        }

        public void onException(Throwable throwable) {
            exception.compareAndSet(false, true);
            throwable.printStackTrace();
        }

        public void onConsumer(Event r) {
            consumer.accept(r);
            System.out.println(name + "模拟签约,event:" + r.getData() + "," + Thread.currentThread().getName());
            runCount.incrementAndGet();
        }

        public boolean isComplated() {
            return complate.get();
        }

        public boolean complated() {
            if (!complate.get()) {
                wait.add(Thread.currentThread());
                LockSupport.park(Thread.currentThread());
                return false;
            } else {
                return true;
            }
        }


        void setFluxSink(FluxSink<Event> fluxSink) {
            this.fluxSink = fluxSink;
        }
    }

    public static class Event<T> {

        private T data;

        public Event(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }
}
