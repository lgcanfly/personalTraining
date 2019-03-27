package generics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/18 Created
 */
public class MyFuture<T> implements Future<T> {
    private T result;
    private Throwable error;

    private CountDownLatch latch = new CountDownLatch(1);

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null || error != null;
    }

    public void success(T result) {
        this.result = result;
        latch.countDown();
    }

    public void fail(Throwable error) {
        this.error = error;
        latch.countDown();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        if (error != null) {
            throw new ExecutionException(error);
        }

        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException {
        latch.await(timeout, unit);
        if (error != null) {
            throw new ExecutionException(error);
        }

        return result;
    }
}
