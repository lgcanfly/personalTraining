package proxy.dynamic.jdk;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/18 Created
 */
public class Student implements Person {
    @Override
    public void pay() {
        System.out.println("Student pay $50.");
    }

    @Override
    public void showSex() {
        System.out.println("I am a male.");
    }
}
