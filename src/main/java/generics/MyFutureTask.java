package generics;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/18 Created
 */
public class MyFutureTask<V> implements Future<V>, Runnable {
    private Object outcome;
    private Callable<V> callable;
    private volatile Thread runner;
    private WaitNode waiters;

    private volatile int state;
    private static final int NEW          = 0;
    private static final int COMPLETING   = 1;
    private static final int NORMAL       = 2;
    private static final int EXCEPTIONAL  = 3;
    private static final int CANCELLED    = 4;
    private static final int INTERRUPTING = 5;
    private static final int INTERRUPTED  = 6;

    public MyFutureTask(Callable<V> callable) {
        if (null == callable) {
            throw new NullPointerException();
        }

        this.callable = callable;
        this.state = NEW;
    }

    @Override
    public boolean isCancelled() {
        return state >= CANCELLED;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!(state == NEW && UNSAFE.compareAndSwapInt(this, stateOffset, NEW,
                mayInterruptIfRunning ? INTERRUPTING : CANCELLED))) {
            return false;
        }

        try {
            if (mayInterruptIfRunning) {
                try {
                    Thread t = runner;
                    if (null != t) {
                        t.interrupt();
                    }
                }
                finally {
                    UNSAFE.putOrderedInt(this, stateOffset, INTERRUPTED);
                }
            }
        }
        finally {
            finishCompletion();
        }

        return true;
    }

    @Override
    public boolean isDone() {
        return (state == NORMAL) || (state == EXCEPTIONAL);
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        int s = state;
        if (state <= COMPLETING) {
            s = awaitDone(false, 0L);
        }

        return report(s);
    }

    @Override
    public V get(long time, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        int s = state;
        if (s <= COMPLETING && (s = awaitDone(true, unit.toNanos(time))) <= COMPLETING) {
            throw new TimeoutException();
        }

        return report(s);
    }

    @Override
    public void run() {
        if ((state != NEW) ||
                !UNSAFE.compareAndSwapObject(this, runnerOffset, null, Thread.currentThread())) {
            return;
        }

        try {
            Callable<V> c = callable;
            if (c != null && state == NEW) {
                V result;
                boolean ran;

                try {
                    result = c.call();
                    ran = true;
                }
                catch (Throwable ex) {
                    result = null;
                    ran = false;
                    setException(ex);
                }

                if (ran) {
                    set(result);
                }
            }
        }
        finally {
            runner = null;
        }
    }

    // exception
    private void setException(Throwable ex) {
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, COMPLETING)) {
            outcome = ex;
            UNSAFE.putOrderedInt(this, stateOffset, EXCEPTIONAL);
            finishCompletion();
        }
    }

    // normal
    private void set(V result) {
        if (UNSAFE.compareAndSwapInt(this, stateOffset, NEW, NORMAL)) {
            outcome = result;
            finishCompletion();
        }
    }

    private void finishCompletion() {
        for (WaitNode q; (q = waiters) != null;) {
            if (UNSAFE.compareAndSwapObject(this, waitersOffset, q, null)) {
                for (;;) {
                    Thread t = q.thread;
                    if (null != t) {
                        LockSupport.unpark(t);
                    }

                    WaitNode next = q.next;
                    if (next == null) {
                        break;
                    }

                    q.next = null;
                    q = next;
                }
            }
        }
    }

    private int awaitDone(boolean timed, long nanos) throws InterruptedException {
        long deadline = timed ? System.nanoTime() + nanos : 0L;
        boolean queued = false;
        WaitNode q = null;

        for (;;) {
            if (Thread.interrupted()) {
                removeWaiter(q);
                throw new InterruptedException();
            }

            int s = state;
            if (s > COMPLETING) {
                if (q != null) {
                    q.thread = null;
                    return s;
                }
            }
            else if (s == COMPLETING) {
                Thread.yield();
            }
            else if (q == null) {
                q = new WaitNode();
            }
            else if (!queued) {
                queued = UNSAFE.compareAndSwapObject(this, waitersOffset, q.next = waiters, q);
            }
            else if (timed) {
                nanos = deadline - System.nanoTime();
                if (nanos <= 0) {
                    return s;
                }

                LockSupport.parkNanos(this, nanos);
            }
            else {
                LockSupport.park(this);
            }
        }
    }

    private void removeWaiter(WaitNode waiter) {
        if (waiter != null) {
            waiter.thread = null;
            retry:
            for (;;) {          // restart on removeWaiter race
                for (WaitNode pred = null, q = waiters, s; q != null; q = s) {
                    s = q.next;
                    if (q.thread != null)
                        pred = q;
                    else if (pred != null) {
                        pred.next = s;
                        if (pred.thread == null) // check for race
                            continue retry;
                    }
                    else if (!UNSAFE.compareAndSwapObject(this, waitersOffset,
                            q, s))
                        continue retry;
                }
                break;
            }
        }
    }

    private V report(int s) throws ExecutionException {
        Object x = outcome;
        if (s == NORMAL) {
            return (V)x;
        }
        else if (s == CANCELLED) {
            throw new CancellationException();
        }

        throw new ExecutionException((Throwable)x);
    }


    // waiter node
    static final class WaitNode {
        volatile Thread thread;
        volatile WaitNode next;

        WaitNode() {
            thread = Thread.currentThread();
        }
    }

    // Unsafe mechanics
    private static final sun.misc.Unsafe UNSAFE;
    private static final long stateOffset;
    private static final long runnerOffset;
    private static final long waitersOffset;
    static {
        try {
            //UNSAFE = sun.misc.Unsafe.getUnsafe();
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            UNSAFE = (Unsafe) f.get(null);
            Class<?> k = MyFutureTask.class;
            stateOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("state"));
            runnerOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("runner"));
            waitersOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("waiters"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
