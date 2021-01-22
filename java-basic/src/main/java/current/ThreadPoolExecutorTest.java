package current;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import util.ThreadPoolUtil;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * <p>createTime：2019-05-08 10:07:35
 *
 * @author liuyq
 * @since 1.0
 */
public class ThreadPoolExecutorTest {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecutorTest.class);

    public void test() {
//        ExecutorService executorService = new ScheduledThreadPoolExecutor(5);
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        final CyclicBarrier barrier = new CyclicBarrier(500);
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 500; i++) {
            try {
                executorService.execute(() -> {
                    longAdder.increment();
                    int b = 1;
//                    b = 1 / 0;
                    System.out.println("123_" + Thread.currentThread());
                    try {
                        b = 1 / 0;
                    } catch (Exception e) {
                        System.out.println(longAdder.longValue() + "_" + Thread.currentThread());
                    }

                    try {
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    throw new RuntimeException();
                });
            } catch (Exception e) {
                System.out.println("456");
            }
        }

        try {
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("longAdder_" + longAdder.longValue());
    }

    public void test2() {
        ExecutorService executorService = new ScheduledThreadPoolExecutor(10);
        CountDownLatch latch = new CountDownLatch(5000);
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 5000; i++) {
            try {
                executorService.execute(() -> {
                    System.out.println("开始执行_" + Thread.currentThread());
                    int b = 1;
//                    latch.countDown();
//                    b = 1 / 0;
                    try {
                        b = 1 / 0;
                    } catch (Exception e) {
                        System.out.println(longAdder.longValue() + "_" + Thread.currentThread());
                    }

                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    longAdder.increment();
                    latch.countDown();
                    throw new RuntimeException();
                });
            } catch (Exception e) {
                System.out.println("子线程异常");
            }
        }

        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("主线程_longAdder_" + longAdder.longValue());
    }

    public void test3() {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(256));
//        ExecutorService executorService = new ScheduledThreadPoolExecutor(10);
        CountDownLatch latch = new CountDownLatch(5000);
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 5000; i++) {
            try {
                executorService.execute(() -> {
                    System.out.println("开始执行_" + Thread.currentThread() + "_" + Thread.currentThread().getId());
                    int b = 1;
//                    latch.countDown();
//                    b = 1 / 0;
                    try {
                        b = 1 / 0;
                    } catch (Exception e) {
                        System.out.println(longAdder.longValue() + "_" + Thread.currentThread());
                    }

                    longAdder.increment();
                    latch.countDown();
//                    throw new RuntimeException();
                });
            } catch (Exception e) {
                logger.info("子线程异常", e);
            }
        }

        try {
            latch.await();
        } catch (Exception e) {
            logger.info("主线程异常", e);
        }
        System.out.println("主线程_longAdder_" + longAdder.longValue());
    }

    public static void main(String[] args) {
        ThreadPoolExecutorTest test = new ThreadPoolExecutorTest();
//        test.test();
//        test.test2();
        test.test3();
    }
}

class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(256));
        long a = 0;
        for (int i = 0; i < 5000; i++) {
            MyTask myTask = new MyTask(i);
            try {
                a = System.currentTimeMillis();
                executor.execute(myTask);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                long b = System.currentTimeMillis() - a;
                System.out.println("线程提交时间间隔" + b);
            }
        }
        executor.shutdown();
    }

    static class MyTask implements Runnable {
        private int taskNum;

        MyTask(int num) {
            this.taskNum = num;
        }

        @Override
        public void run() {
            System.out.println("正在执行task " + taskNum);
            System.out.println("正在执行,线程id " + Thread.currentThread());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println("task " + taskNum + "执行完毕");
        }
    }
}

/**
 * 可见每次执行的线程都不一样，之前的线程都没有复用。原因是因为出现了未捕获的异常
 */
class ThreadPoolExceptionTest {
    public static void main(String[] args) {
        ExecutorService executorService = ThreadPoolUtil.getExecutorService();

        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 500; i++) {
            if (i % 2 == 0) {
                executorService.execute(() -> do1(atomicInteger));
            } else {
                executorService.execute(() -> do2(atomicInteger));
            }
        }
    }

    private static void do1(AtomicInteger atomicInteger) {
        System.out.println("do1-" + atomicInteger.incrementAndGet());
    }

    private static void do2(AtomicInteger atomicInteger) {
        System.out.println("do2-" + atomicInteger.incrementAndGet());
        List<String> list = Collections.emptyList();
        list.add("a");
    }
}

/**
 * 可见当异常捕获了，线程就可以复用了
 */
@Slf4j
class ThreadPoolExceptionTest2 {
    public static void main(String[] args) {
        ExecutorService executorService = ThreadPoolUtil.getExecutorService();

        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 500; i++) {
            if (i % 2 == 0) {
                executorService.execute(() -> do1(atomicInteger));
            } else {
                executorService.execute(() -> do2(atomicInteger));
            }
        }
    }

    private static void do1(AtomicInteger atomicInteger) {
        System.out.println("do1-" + atomicInteger.incrementAndGet());
    }

    private static void do2(AtomicInteger atomicInteger) {
        try {
            System.out.println("do2-" + atomicInteger.incrementAndGet());
            List<String> list = Collections.emptyList();
            list.add("a");
        } catch (Exception e) {
            log.error("do2异常", e);
        }
    }
}

/**
 * 通过submit提交线程可以屏蔽线程中产生的异常，达到线程复用。当get()执行结果时异常才会抛出。
 * 原因是通过submit提交的线程，当发生异常时，会将异常保存，待future.get();时才会抛出
 */
@Slf4j
class ThreadPoolExceptionTest3 {
    public static void main(String[] args) {
        ExecutorService executorService = ThreadPoolUtil.getExecutorService();

        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 500; i++) {
            if (i % 2 == 0) {
                Future<Integer> future = executorService.submit(() -> do1(atomicInteger));
            } else {
                Future<Integer> future = executorService.submit(() -> do2(atomicInteger));
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("do2 future.get()异常", e);
                }
            }
        }
    }

    private static Integer do1(AtomicInteger atomicInteger) {
        log.info("do1：{}", Thread.currentThread());
        System.out.println("do1-" + atomicInteger.incrementAndGet());
        return atomicInteger.get();
    }

    private static Integer do2(AtomicInteger atomicInteger) {
        log.info("do2：{}", Thread.currentThread());
        System.out.println("do2-" + atomicInteger.incrementAndGet());
        List<String> list = Collections.emptyList();
        list.add("a");

//        try {
//            System.out.println("do2-" + atomicInteger.incrementAndGet());
//            List<String> list = Collections.emptyList();
//            list.add("a");
//        } catch (Exception e) {
//            log.error("do2异常", e);
//        }
        return atomicInteger.get();
    }
}