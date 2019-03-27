package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/8 Created
 */
public class TestReject {
    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2), new SimpleThreadFactory("TestReject"), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            try {
                poolExecutor.execute(new Task(i));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

        poolExecutor.shutdown();
    }
}

class Task implements Runnable {
    private int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println(id + " is sleeping for 10s.");
        try {
            Thread.sleep(10 * 1000);
        }
        catch (InterruptedException e) {

        }
    }
}
