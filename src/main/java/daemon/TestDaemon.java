package daemon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/13 Created
 */
public class TestDaemon {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Shutdown after 10s");
                try {
                    Thread.sleep(10 * 1000);
                }
                catch (InterruptedException e) {

                }
            }
        });

        Thread daemon = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Daemon thread is working.");
                    try {
                        Thread.sleep(5 * 1000);
                    }
                    catch (InterruptedException e) {

                    }
                }
            }
        });
        daemon.setDaemon(true);
        daemon.start();

        executorService.shutdown();
    }
}
