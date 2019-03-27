package spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/19 Created
 */
public class TestSpi {
    public static void main(String[] args) {
        ServiceLoader<SimpleService> spiLoader = ServiceLoader.load(SimpleService.class);
        Iterator<SimpleService> iterator = spiLoader.iterator();
        while (iterator.hasNext()) {
            SimpleService simpleService = iterator.next();
            simpleService.echo("LiGuanyun");
        }
    }
}
