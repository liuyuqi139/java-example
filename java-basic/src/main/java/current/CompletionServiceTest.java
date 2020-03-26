package current;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * <pre>
 * （1）动态加载数据、下载图片等，一旦队列中有了数据，就可以陆续返回加载到的数据，不需要等到所有数据都加载完成才返回；滚动网页显示加载图片可以用其实现。
 * （2）从不同数据源加载数据，一个ExecutorCompletionService从一个数据源中获取数据，然后通过各个ExecutorCompletionService返回的结果，再做数据整合
 * （3）多线程并行处理数据，可以大大提高程序处理时间，提高性能
 * </pre>
 */
public class CompletionServiceTest {
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        //开启5个线程
        ExecutorService exs = Executors.newFixedThreadPool(5);
        try {
            int taskCount = 10;
            // 结果集
            List<Integer> list = new ArrayList<>();
            List<Future<Integer>> futureList = new ArrayList<>();

            // 1.定义CompletionService
            CompletionService<Integer> completionService = new ExecutorCompletionService<>(exs);

            // 2.添加任务
            for (int i = 0; i < taskCount; i++) {
                Future<Integer> future = completionService.submit(new Task(i + 1));
                futureList.add(future);
            }

            // 3.获取结果
            for (int i = 0; i < taskCount; i++) {
                Integer result = completionService.take().get();
                System.out.println("任务i==" + result + "完成!" + new Date());
                list.add(result);
            }

            System.out.println("list=" + list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭线程池
            exs.shutdown();
        }

    }

    static class Task implements Callable<Integer> {
        Integer i;

        public Task(Integer i) {
            super();
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            if (i == 5) {
                Thread.sleep(5000);
            } else {
                Thread.sleep(1000);
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "任务i=" + i + ",执行完成！");
            return i;
        }
    }
}
