package leaderlatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/15 Created
 */
public class TestLeaderLatch {
    private static final String LATCH = "/job/latch";

    public static void main(String[] args) {
        List<LeaderLatch> leaderLatches = new ArrayList<>(10);
        List<CuratorFramework> clients = new ArrayList<>(10);

        try {
            for (int i = 0; i < 5; i++) {
                LeaderLatch leaderLatch;
                CuratorFramework client;
                client = getClient();
                leaderLatch = new LeaderLatch(client, LATCH, "Client#" + i);
                leaderLatch.addListener(new LeaderLatchListener() {
                    @Override
                    public void isLeader() {
                        System.out.println("I have the leadership:" + leaderLatch.getId());

                        try {
                            Thread.sleep(30 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        CloseableUtils.closeQuietly(client);
                    }

                    @Override
                    public void notLeader() {
                        System.out.println("I failed to be a leader:" + leaderLatch.getId());
                    }
                });
                leaderLatch.start();
            }

            System.out.println("##########Sleeping.");
            Thread.sleep(Integer.MAX_VALUE);
        }
        catch (Exception e) {
            System.out.println("##########");
        }
        finally {
            for(CuratorFramework curatorFramework : clients){
                CloseableUtils.closeQuietly(curatorFramework);
            }

            for(LeaderLatch leader : leaderLatches){
                CloseableUtils.closeQuietly(leader);
            }
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
