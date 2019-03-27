package gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/27 Created
 */
public class PhantomReferenceTest {
    private static boolean isRunning = true;

    public static void main(String[] args) throws InterruptedException {
        String abc = new String("abc");
        System.out.println(abc.getClass() + "@" + abc.hashCode());

        final ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        new Thread(()-> {
            while (isRunning) {
                Object obj = referenceQueue.poll();
                if (obj != null) {
                    try {
                        Field referent = Reference.class.getDeclaredField("referent");
                        referent.setAccessible(true);
                        Object result = referent.get(obj);
                        System.out.println("gc will collect："
                                + result.getClass() + "@"
                                + result.hashCode() + "\t"
                                + (String) result);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        PhantomReference<String> phantomReference = new PhantomReference<>(abc, referenceQueue);
        abc = null;
        Thread.currentThread().sleep(3 * 1000L);
        System.gc();
        Thread.currentThread().sleep(3 * 1000L);
        isRunning = false;
    }
}
