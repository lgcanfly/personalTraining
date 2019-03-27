package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/8 Created
 */
public class TestReflect {
    public static void main(String[] args) {
        //获取class对象的三种方法
        Class stringClazz = String.class;
        Class<?> clazz = SimpleInfo.class;
        SimpleInfo simpleInfo = new SimpleInfo("1", "Work harder!");
        Class<?> instanceClazz = simpleInfo.getClass();

        System.out.println(simpleInfo.toString());

        try {
            // clazz is same because of loaded by the save class loader--AppClassLoader
            // BootstrapClassLoader
            // ExtClassLoader
            // AppClassLoader
            Class<?> forNameClazz = Class.forName("reflect.SimpleInfo");
            System.out.println("clazz == instanceClazz:" + (clazz == instanceClazz));
            System.out.println("clazz == forNameClazz:" + (clazz == forNameClazz));
        }
        catch (ClassNotFoundException e) {
            System.out.println(e);
        }
        try {
            Object defaultSimpleInfo = clazz.newInstance();
            Method setIdMethod = clazz.getDeclaredMethod("setId", String.class);
            setIdMethod.setAccessible(true);
            Method setInfoMethod = clazz.getDeclaredMethod("setInfo", String.class);
            setInfoMethod.setAccessible(true);
            setIdMethod.invoke(defaultSimpleInfo, "0");
            setInfoMethod.invoke(defaultSimpleInfo, "Work normally!");
            System.out.println(((SimpleInfo) defaultSimpleInfo).toString());
        }
        catch (Exception e) {
            System.out.println(e);
        }

        try {
            Constructor constructor = clazz.getConstructor(String.class, String.class);
            SimpleInfo customizedSimpleInfo = (SimpleInfo) constructor.newInstance("2", "Work more harder!");
            System.out.println(customizedSimpleInfo.toString());
        }
        catch (Exception e) {
            System.out.println(e);
        }

        // methods
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("This is methods of Class SimpleInfo:");
        for (Method method : methods) {
            System.out.println(method.getName() + ":" + method.getModifiers());
        }

        // static field
        try {
            Field f = clazz.getDeclaredField("DEMO");
            f.setAccessible(true);
            String demo = (String) f.get(null);
            System.out.println("Get static field:" + demo);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
