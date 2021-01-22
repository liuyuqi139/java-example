package flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * 调度器与线程模型
 */
public class SchedulerTest {
    /**
     * publishOn会影响链中其后的操作符，比如第一个publishOn调整调度器为elastic，则filter的处理操作是在弹性线程池中执行的；同理，flatMap是执行在固定大小的parallel线程池中的；
     * subscribeOn无论出现在什么位置，都只影响源头的执行环境，也就是range方法是执行在单线程中的，直至被第一个publishOn切换调度器之前，所以range后的map也在单线程中执行。
     */
    @Test
    public void testScheduling() {
        Flux.range(0, 10)
                .log()
                .publishOn(Schedulers.newParallel("myParrallel"))
                .log()
                .subscribeOn(Schedulers.newElastic("myElastic"))
                .log()
                .blockLast();
    }

    /**
     * 并行执行
     */
    @Test
    public void testParallelFlux() throws InterruptedException {
        Flux.range(1, 10)
//                .log()
                .parallel(2)
                .runOn(Schedulers.parallel())
//                .publishOn(Schedulers.parallel())
                .log()
                .subscribe();

        TimeUnit.MILLISECONDS.sleep(10);
    }
}
