package concurrent.visualable;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/27 Created
 */
public class Visuality {
    private int a;
    private volatile int b;

    public Visuality() {
        a = 1;
        b = 1;
    }

    public void set() {
        a = 2;
        b = 2;
    }

    public void get() {
        System.out.print("b = " + b + ", ");
        System.out.print("a = " + a);
        System.out.println();
    }
}
