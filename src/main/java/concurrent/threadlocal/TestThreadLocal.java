package concurrent.threadlocal;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/22 Created
 */
public class TestThreadLocal {
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        int size = 3;
        CountDownLatch latch = new CountDownLatch(size);


        for (int i = 0; i < size; i++) {
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("CurrentThread:" + ContextUtil.getThreadId());
//                    ContextUtil.remove();
//
//                    try {
//                        Thread.sleep(30 * 60 * 1000);
//                    } catch (InterruptedException e) {
//
//                    }
//                }
//            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("CurrentThread:" + ContextUtil.getThreadId());
                    ContextUtil.remove();
                    latch.countDown();

                    try {
                        Thread.sleep(30 * 60 * 1000);
                    } catch (InterruptedException e) {

                    }
                }
            }).start();
        }

        try {
            latch.await();
            ContextUtil.clear();
        } catch (InterruptedException e) {

        }

        System.out.println("###");
        char[] demo = new char[12 * 8 * 1024 * 1024];
        Arrays.fill(demo, '1');
        System.in.read();
    }
}
