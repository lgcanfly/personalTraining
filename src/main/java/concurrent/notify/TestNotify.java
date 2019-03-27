package concurrent.notify;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/4 Created
 */
public class TestNotify {
    public void waitM() {
        synchronized (this) {
            System.out.println("I am going to wait a notify");
            try {
                this.wait();
            }
            catch (InterruptedException e) {
                System.out.println("I have been interrupted.");
            }

            System.out.println("I have been wake up.");
        }
    }

    public void notifyM() {
        synchronized (this) {
            System.out.println("I am going to send a notify");
            this.notifyAll();
            System.out.println("After notify sent.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestNotify instance = new TestNotify();
        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.waitM();
            }
        }).start();

        Thread.sleep(100);

        new Thread(new Runnable() {
            @Override
            public void run() {
                instance.notifyM();
            }
        }).start();
    }

}
