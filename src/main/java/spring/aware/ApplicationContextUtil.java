package spring.aware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/26 Created
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ac = applicationContext;
    }

    public static ApplicationContext getContext() {
        return ac;
    }
}
