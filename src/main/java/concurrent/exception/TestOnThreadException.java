package concurrent.exception;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/26 Created
 */
public class TestOnThreadException {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                int j = 0;
            }
        });

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Thread-" + t.getName() + " caught an exception.");
                System.out.print(e);
            }
        });

        thread.start();
    }
}
