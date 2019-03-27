package concurrent.cas;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/27 Created
 */
public class CasTest {
    private static int count = 0;

    private static class Lock {
        private AtomicReference<Thread> atomicReference = new AtomicReference<>(null);

        public void lock() {
            Thread thread = Thread.currentThread();
            while (!(atomicReference.compareAndSet(null, thread))) {

            }
        }

        public void unlock() {
            Thread thread = Thread.currentThread();
            atomicReference.compareAndSet(thread, null);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int CONCURRENCY = 100;
        CountDownLatch latch = new CountDownLatch(CONCURRENCY);
        Lock lock = new Lock();
        ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENCY);
        for (int i = 0; i < CONCURRENCY; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 100000; j++) {
                    lock.lock();
                    count++;
                    lock.unlock();
                }
                latch.countDown();
            });
        }

        latch.await();
        System.out.println(count);
        executorService.shutdown();
    }
}
