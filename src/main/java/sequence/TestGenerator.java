package sequence;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/13 Created
 */
public class TestGenerator {
    public static void main(String[] args) throws Exception {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();

        CountDownLatch latch = new CountDownLatch(5000);
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        List<String> result = new LinkedList<String>();

        for (int i = 0; i < 5000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        result.add(sequenceGenerator.generateSequence("cluster"));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        latch.countDown();
                    }
                }
            });
        }

        try {
            latch.await();
        }
        catch (InterruptedException e) {

        }

        executorService.shutdown();

        Iterator iterator = result.iterator();
        while (iterator.hasNext()) {
            System.out.println("###:" + iterator.next());
        }
    }
}
