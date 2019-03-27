package concurrent;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/19 Created
 */
public class TestSynchronized implements Runnable {
    int value = 100;

    synchronized void m1() {
        System.out.println("###m1 start.");
        value = 1000;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thead-" + Thread.currentThread().getName() + ":" + value);
        System.out.println("###m1 end.");
    }

    synchronized void m2() {
        System.out.println("###m2 start.");
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = 2000;
        System.out.println("###m2 end.");
    }

    @Override
    public void run() {
        m1();
    }

    public static void main(String[] args) {
        TestSynchronized instance = new TestSynchronized();
        Thread thread = new Thread(instance);
        thread.start();
        instance.m2();
        System.out.println("MainThead:" + instance.value);
    }
}
