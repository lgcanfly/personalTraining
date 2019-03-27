package concurrent.threadlocal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/22 Created
 */
public class ContextUtil {
    private static ThreadLocal<Map<String, String>> context = new ThreadLocal<Map<String, String>>() {
        @Override
        protected Map<String, String> initialValue() {
            Map<String, String> value = new HashMap<String, String>();
            value.put("threadId", String.valueOf(Thread.currentThread().getId()));
            value.put("threadName", Thread.currentThread().getName());

            char[] temp = new char[4 * 8 * 1024 * 1024];
            Arrays.fill(temp, '0');

            value.put("temp", String.valueOf(temp));
            return value;
        }
    };

    public static String getThreadName() {
        return context.get().get("threadName");
    }

    public static String getThreadId() {
        return context.get().get("threadId");
    }

    public static void clear() {
        context = null;
    }

    public static void remove() {
        context.remove();
    }
}
