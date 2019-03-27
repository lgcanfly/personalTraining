package generics;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/18 Created
 */
public class TestMyFutureTask {
    public static void main(String[] args) {
        MyFutureTask<Integer> myFutureTask = new MyFutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try {
                    System.out.println("Sleeping.");
                    Thread.sleep(30 * 1000);
                    System.out.println("Sleeping down.");
                }
                catch (InterruptedException e) {
                    throw e;
                }

                return Integer.valueOf(1);
            }
        });

        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        singleThread.execute(myFutureTask);

        try {
            Integer result = myFutureTask.get();
            System.out.println("MyFutureTask has been done:" + result.toString());
        }
        catch (InterruptedException | ExecutionException e) {

        }
        singleThread.shutdown();
    }
}
