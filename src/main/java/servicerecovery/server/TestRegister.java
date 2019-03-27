package servicerecovery.server;

import servicerecovery.service.SimpleService;

import java.io.IOException;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/22 Created
 */
public class TestRegister {
    public static void main(String[] args) throws IOException {
        ServiceRegistry zkRegistry = ServiceRegistryImpl.getInstance();
        zkRegistry.registerService(SimpleService.class, "127.0.0.1:8080");
        System.in.read();
    }
}
