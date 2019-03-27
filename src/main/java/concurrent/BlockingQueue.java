package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/8 Created
 */
public class BlockingQueue<T> {

    private final ReentrantLock LOCK = new ReentrantLock();
    private final Condition NOT_FULL = LOCK.newCondition();
    private final Condition NOT_EMPTY = LOCK.newCondition();

    private List<T> data;
    private int capacity;
    private int size;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        data = new ArrayList<T>(this.capacity);
        size = 0;
    }

    public void offer(T item) {
        LOCK.lock();
        try {
            if (size == capacity) {
                NOT_FULL.await();
            }

            data.add(item);
            size++;
            System.out.println("Thread-" + Thread.currentThread().getName() + " offers " + item);
            NOT_EMPTY.signalAll();
        }
        catch (InterruptedException e) {
            System.out.println("Thread-" + Thread.currentThread().getName() + " has been interrupted.");
        }
        finally {
            LOCK.unlock();
        }
    }

    public T take() {
        LOCK.lock();
        T item = null;
        try {
            if (size == 0) {
                NOT_EMPTY.await();
            }

            item = data.remove(0);
            size--;
            System.out.println("Thread-" + Thread.currentThread().getName() + " takes " + item);
            NOT_FULL.signalAll();
        }
        catch (InterruptedException e) {
            System.out.println("Thread-" + Thread.currentThread().getName() + " has been interrupted.");
        }
        finally {
            LOCK.unlock();
        }

        return item;
    }
}
