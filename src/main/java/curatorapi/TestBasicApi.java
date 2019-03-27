package curatorapi;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/17 Created
 */
public class TestBasicApi {
    private static final String PATH = "/api";

    public static void main(String[] args) {
        CuratorFramework client = getClient();

        try {
            // create a path
            BackgroundCallback createCallBack = new BackgroundCallback() {
                @Override
                public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                    System.out.println("This is a create callback.");
                    System.out.println("State is:" + event.getResultCode());
                }
            };
            client.create().creatingParentsIfNeeded().withProtection()
                    .inBackground(createCallBack).forPath(PATH, "api".getBytes());

            // get data from path
            BackgroundCallback getCallBack = new BackgroundCallback() {
                @Override
                public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {

                }
            };

//            System.out.println(client.getData().forPath(PATH).toString());

            Thread.sleep(600 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            CloseableUtils.closeQuietly(client);
        }
    }

    private static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectionTimeoutMs(3000)
                .build();
        client.start();
        return client;
    }
}
