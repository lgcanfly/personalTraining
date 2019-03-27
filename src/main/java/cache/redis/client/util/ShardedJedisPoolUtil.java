package cache.redis.client.util;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/25 Created
 */
public final class ShardedJedisPoolUtil {
    private static final String NODE_A = "127.0.0.1:6379";
    private static final String NODE_B = "127.0.0.1:6380";

    private static final Object MUTEX = new Object();
    private static volatile ShardedJedisPoolUtil INSTANCE = null;

    private ShardedJedisPool pool;

    public ShardedJedisPool getShardedJedisPool() {
        return pool;
    }

    public static ShardedJedisPoolUtil getInstance() {
        if (null == INSTANCE) {
            synchronized (MUTEX) {
                if (null == INSTANCE) {
                    INSTANCE = new ShardedJedisPoolUtil();
                    return INSTANCE;
                }

                return INSTANCE;
            }
        }

        return INSTANCE;
    }

    private ShardedJedisPoolUtil() {
        init();
    }

    private void init() {
        // jedis pool configuration
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(500);
        poolConfig.setMaxIdle(60 * 1000);
        poolConfig.setMaxWaitMillis(10 * 1000);
        poolConfig.setTestOnBorrow(true);

        // cluster instance info
        List<JedisShardInfo> jedisShardInfos = new ArrayList<>(2);
        String[] nodeAInfo = NODE_A.split(":");
        String[] nodeBInfo = NODE_B.split(":");
        jedisShardInfos.add(new JedisShardInfo(nodeAInfo[0], Integer.valueOf(nodeAInfo[1]), "Node_A"));
        jedisShardInfos.add(new JedisShardInfo(nodeBInfo[0], Integer.valueOf(nodeBInfo[1]), "Node_B"));

        pool = new ShardedJedisPool(poolConfig, jedisShardInfos, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }
}
