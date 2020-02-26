package current;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * 异步返回
 */
@Slf4j
public class FutureTest {
    public class AsyncExecutor {
        // 线程池，建议恰当配置（拒绝策略建议CallerRunsPolicy）和使用框架注入
        private ExecutorService executorService = Executors.newFixedThreadPool(10);

        /**
         * 单个入参，有返回值的异步执行方法 , public User getById(Long id)
         */
        public <P, R> Future<R> async(Function<P, R> method, P param) {
            return executorService.submit(() -> method.apply(param));
        }

        public void shutdown() {
            executorService.shutdown();
        }
    }

    public class UserHttpService {
        public String getById(Long id) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return id.toString();
        }
    }

    public class A {

        private AsyncExecutor asyncExecutor = new AsyncExecutor();

        private UserHttpService userHttpService = new UserHttpService();

        public void foo(Long id, Long id2) {
            // 异步调用
            Future<String> future = asyncExecutor.async(userHttpService::getById, id);
            Future<String> future2 = asyncExecutor.async(userHttpService::getById, id2);

            // 获取结果
            try {
                log.info("id：{}, id2：{}", future.get(), future2.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            asyncExecutor.shutdown();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        A a = new FutureTest().new A();
        a.foo(1L, 2L);
        long end = System.currentTimeMillis();
        log.info("耗时：{}", end - start);
    }
}
