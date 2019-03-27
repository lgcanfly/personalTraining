package concurrent.visualable;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/27 Created
 */
public class TestVisualable {
    public static void main(String[] args) {
        Visuality visuality = new Visuality();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 1,
                TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>(), new AbortPolicy());

        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    visuality.get();
                }
            });
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                visuality.set();
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    visuality.get();
                }
            });
        }
    }
}
