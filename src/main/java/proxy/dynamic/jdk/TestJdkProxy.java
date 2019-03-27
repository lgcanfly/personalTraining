package proxy.dynamic.jdk;

import java.lang.reflect.Proxy;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/18 Created
 */
public class TestJdkProxy {
    public static void main(String[] args) {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/liguanyun/temp/class");
        Student student = new Student();
        PersonInvocationHandler invocationHandler = new PersonInvocationHandler<Person>(student);
        Person proxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(),
                new Class<?>[] {Person.class}, invocationHandler);
        proxy.pay();
        proxy.showSex();
    }
}
