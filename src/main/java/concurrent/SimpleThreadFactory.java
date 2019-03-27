package concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/8 Created
 */
public class SimpleThreadFactory implements ThreadFactory {
    private AtomicLong id = new AtomicLong(0);
    private String prefix;

    public SimpleThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = prefix + id.incrementAndGet();
        return new Thread(runnable, name);
    }
}
