package spring.beans;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/26 Created
 */
@Component("greetingService")
public class GreetingService implements Service {
    @Override
    public void sayHello(String info) {
        System.out.println("Greetings, " + info);
    }
}
