package spi;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/19 Created
 */
public class GreetingSimpleService implements SimpleService {
    @Override
    public void echo(String message) {
        System.out.println("Greetings, " + message);
    }
}
