package proxy.steady;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/18 Created
 */
public class EchoImpl implements Echo {
    @Override
    public void echo() {
        System.out.println("I am an echo.");
    }
}
