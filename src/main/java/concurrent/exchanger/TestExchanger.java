package concurrent.exchanger;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/11 Created
 */
public class TestExchanger {
    public static void main(String[] args) {
        Exchanger<Set<Integer>> exchanger = new Exchanger<>();
        BatchSender batchSender = new BatchSender(exchanger);
        BatchReceiver batchReceiver = new BatchReceiver(exchanger);

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(batchReceiver);
        service.execute(batchSender);
        service.shutdown();
    }
}

class BatchReceiver implements Runnable {
    Exchanger<Set<Integer>> exchanger;
    Set<Integer> holder;
    int exchangeThreshold;

    public BatchReceiver(Exchanger exchanger) {
        this.exchanger = exchanger;
        holder = new LinkedHashSet<>();
        exchangeThreshold = 5;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < exchangeThreshold) {
            try {
                holder = exchanger.exchange(holder);
                showReceiver(holder);
                holder.clear();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    private void showReceiver(Set<Integer> holder) {
        Iterator iterator = holder.iterator();
        while (iterator.hasNext()) {
            System.out.print((Integer)iterator.next() + ", ");
        }
        System.out.println();
    }
}

class BatchSender implements Runnable {
    Exchanger<Set<Integer>> exchanger;
    Set<Integer> holder;
    int batchSize;
    int exchangeThreshold;

    public BatchSender(Exchanger exchanger) {
        this.exchanger = exchanger;
        holder = new LinkedHashSet<>();
        batchSize = 10;
        exchangeThreshold = 5;
    }

    @Override
    public void run() {
        int batch = 0;
        int i = 0;
        while (i < exchangeThreshold) {
            for (; batch < (i+1) * batchSize; batch++) {
                System.out.println("Sender add:" + batch);
                holder.add(batch);
            }
            try {
                System.out.println("Sender batch data is ready.");
                holder = exchanger.exchange(holder);
//                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }

    }
}
