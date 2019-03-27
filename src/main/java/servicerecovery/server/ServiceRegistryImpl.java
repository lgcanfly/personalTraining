package servicerecovery.server;

import org.apache.curator.framework.CuratorFramework;
import servicerecovery.common.Constants;
import servicerecovery.common.CuratorFrameworkUtil;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/22 Created
 */
public class ServiceRegistryImpl implements ServiceRegistry {
    private static final ServiceRegistry INSTANCE = new ServiceRegistryImpl();

    private CuratorFramework client;

    private ServiceRegistryImpl() {
        client = CuratorFrameworkUtil.getClient();

        if (!CuratorFrameworkUtil.checkExists(client, Constants.ROOT_PATH)) {
            CuratorFrameworkUtil.create(client, Constants.ROOT_PATH, null);
        }
    }

    public static ServiceRegistry getInstance() {
        return INSTANCE;
    }

    @Override
    public void registerService(Class<?> clazz, String address) {
        if (clazz == null) {
            return;
        }
        String serviceName = clazz.getName();
        String url = generateURL(clazz, address);

        String servicePath = Constants.ROOT_PATH + "/" + serviceName;

        if (!CuratorFrameworkUtil.checkExists(client, servicePath)) {
            CuratorFrameworkUtil.create(client, servicePath,  null);
        }

        CuratorFrameworkUtil.createEphemeralSequence(client, servicePath + "/Provider-", url.getBytes());
    }

    private String generateURL(Class<?> clazz, String address) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.PROTOCOL)
                .append(address);

        StringBuilder methodsBuilder = new StringBuilder();
        methodsBuilder.append("?methods=");
        Method[] methods = clazz.getMethods();
        int i = 0;
        for (Method method : methods) {
            if (i == methods.length - 1) {
                methodsBuilder.append(method.getName());
                break;
            }

            methodsBuilder.append(method.getName()).append(',');
            i++;
        }

        url.append(methodsBuilder).append("&timestamp=")
                .append(System.currentTimeMillis());
        return url.toString();
    }
}
