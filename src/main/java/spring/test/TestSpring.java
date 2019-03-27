package spring.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.beans.StaticDependService;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/26 Created
 */
public class TestSpring {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StaticDependService staticDependService = (StaticDependService) context.getBean(StaticDependService.class);
        staticDependService.doService("GreetingService", "LiGuanyun");
    }
}
