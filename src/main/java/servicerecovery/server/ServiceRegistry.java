package servicerecovery.server;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/21 Created
 */
public interface ServiceRegistry {
    void registerService(Class<?> clazz, String address);
}
