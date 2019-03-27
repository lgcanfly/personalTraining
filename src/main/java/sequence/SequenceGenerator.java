package sequence;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.data.Stat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/13 Created
 */
public class SequenceGenerator {

    private static final String DATA_PATH = "/sequence/data";
    private static final String LOCK_PATH = "/sequence/lock";
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final int BITS = 8;
    private static final char PAD_CHAR = '0';

    public String generateSequence(String category) throws Exception {
        if (null == category || category.isEmpty()) {
            return "";
        }

        CuratorFramework client = getClient();
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, appendPath(LOCK_PATH, category));

        try {
            interProcessMutex.acquire();
            String dataPath = appendPath(DATA_PATH, category);
            Stat existStat = client.checkExists().forPath(dataPath);
            String currentDate = FORMAT.format(new Date());
            String sequence = null;
            if (null == existStat) {
                sequence =  new StringBuilder().append(category)
                        .append(currentDate)
                        .append(StringUtils.leftPad(String.valueOf(1), BITS, PAD_CHAR)).toString();
                client.create().creatingParentsIfNeeded().forPath(dataPath, sequence.getBytes());
                System.out.println("Generated sequence:" + sequence);
                return sequence;
            }

            Stat dataStat = new Stat();
            String data = new String(client.getData().storingStatIn(dataStat).forPath(dataPath));
            sequence = deduceCurrentSequence(data, currentDate, category);
            client.setData().withVersion(dataStat.getVersion()).forPath(dataPath, sequence.getBytes());
            System.out.println("Generated sequence:" + sequence);
            return sequence;
        }
        catch (Exception e) {
            System.out.println("Get sequence failed.");
            e.printStackTrace();
            throw e;
        }
        finally {
            interProcessMutex.release();
            CloseableUtils.closeQuietly(client);
        }
    }


    private CuratorFramework getClient() {
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

    private String appendPath(String parent, String child) {
        return new StringBuilder().append(parent).append('/').append(child).toString();
    }

    private String deduceCurrentSequence(String oldSequence, String currentDate, String category) {
        if (!oldSequence.contains(category)) {
            throw new IllegalArgumentException("Path and data is not match.");
        }

        String[] splits = oldSequence.split(currentDate);

        if (splits.length != 2) {
            return new StringBuilder().append(category)
                    .append(currentDate)
                    .append(StringUtils.leftPad(String.valueOf(1), BITS, PAD_CHAR)).toString();
        }

        int currentNumber = Integer.valueOf(splits[1]).intValue() + 1;
        return new StringBuilder().append(category)
                .append(currentDate)
                .append(StringUtils.leftPad(String.valueOf(currentNumber), BITS, PAD_CHAR)).toString();
    }
}
