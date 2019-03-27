package proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/18 Created
 */
public class PersonInvocationHandler<T> implements InvocationHandler {
    T target;

    public PersonInvocationHandler(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK Proxy start, method is " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("JDK Proxy end, method is " + method.getName());
        return result;
    }
}
