package concurrent;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/8 Created
 */
public class TestInterrupt {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new BlockingQueue<String>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(8, 8, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(), new SimpleThreadFactory("TestBlockingQueue"));

        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                for (int i = 0; i < 20; i++) {
                    queue.take();
                    try {
                        Thread.sleep(random.nextInt(10) * 1000);
                    }
                    catch (InterruptedException e) {

                    }
                }
            }
        });

        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    queue.offer(Thread.currentThread().getName() + i);
                    try {
                        Thread.sleep(random.nextInt(5) * 1000);
                    }
                    catch (InterruptedException e) {

                    }
                }
            }
        });

        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                for (int i = 0; i < 10; i++) {
                    queue.offer(String.valueOf(i));
                    try {
                        Thread.sleep(random.nextInt(5) * 1000);
                    }
                    catch (InterruptedException e) {

                    }
                }
            }
        });

        poolExecutor.shutdown();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                queue.take();
//            }
//        }, "SingleThread");
//
//        thread.start();
//        try {
//            Thread.sleep(5 * 1000);
//        }
//        catch (InterruptedException e) {
//
//        }
//        thread.interrupt();
    }
}