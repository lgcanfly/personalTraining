package proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/3/1 Created
 */
public class CglibProxyInteceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before method:" + method.getName());
        Object object = methodProxy.invokeSuper(sub, objects);
        System.out.println("After method:" + method.getName());
        return object;
    }
}
