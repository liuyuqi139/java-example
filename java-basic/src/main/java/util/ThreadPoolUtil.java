package util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具
 */
public class ThreadPoolUtil {
    private static final ExecutorService executorService = new ThreadPoolExecutor(
            10, 10, 5L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5000)
    );

    private ThreadPoolUtil() {
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
