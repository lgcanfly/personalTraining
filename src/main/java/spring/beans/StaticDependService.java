package spring.beans;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import spring.aware.ApplicationContextUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/26 Created
 */
@Component
public class StaticDependService implements InitializingBean {
    private Map<String, Service> serviceBeans;

    @Override
    public void afterPropertiesSet() {
        serviceBeans = new HashMap<>();
        Map<String, Service> services = ApplicationContextUtil.getContext().getBeansOfType(Service.class);
        serviceBeans.put("EchoService", services.get("echoService"));
        serviceBeans.put("GreetingService", services.get("greetingService"));
    }

    public void doService(String type, String info) {
        Service service = serviceBeans.get(type);

        if (null != service) {
            service.sayHello(info);
        }
    }
}
