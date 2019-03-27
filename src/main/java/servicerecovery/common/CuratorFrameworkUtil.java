package servicerecovery.common;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/21 Created
 */
public class CuratorFrameworkUtil {
    public static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        String connectString = new StringBuilder().append(Constants.HOST)
                .append(Constants.COLON).append(Constants.PORT).toString();

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectionTimeoutMs(3000)
                .build();
        client.start();
        return client;
    }

    public static void create(CuratorFramework client, String path, byte[] data) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .forPath(path, data);
        }
        catch (Exception e) {
            System.out.print("Create path error.");
        }
    }

    public static void createEphemeralSequence(CuratorFramework client, String path, byte[] data) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(path, data);
        }
        catch (Exception e) {
            System.out.print("Create ephemeral sequence path error.");
        }
    }

    public static boolean checkExists(CuratorFramework client, String path) {
        try {
            Stat stat =  client.checkExists().forPath(path);
            if (null == stat) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.print("Check exists error.");
        }

        return true;
    }
}
