package spring.beans;

import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/26 Created
 */
@Component("echoService")
public class EchoService implements Service {
    public void sayHello(String info) {
        System.out.println(info + " " + info);
    }
}
