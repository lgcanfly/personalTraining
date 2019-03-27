package cache.redis.client.util;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Random;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/25 Created
 */
public class TestShardedJedisPool {
    public static void main(String[] args) {
        Random random = new Random();
        ShardedJedis jedis = null;
        ShardedJedisPool pool = ShardedJedisPoolUtil.getInstance().getShardedJedisPool();
        String key = null;
        for (int i = 0; i < 500; i++) {
            key = generateKey();

            try {
                jedis = pool.getResource();
                System.out.println(key + " has been stored in #" + jedis.getShard(key).getClient().getPort());
                System.out.println(jedis.set(key, "Hello, Jedis!"));
            }

            catch (Exception e) {

            }
            finally {
                pool.returnResource(jedis);
            }
        }
    }

    private static int index = 1;

    public static String generateKey() {
        return String.valueOf(Thread.currentThread().getId())+"_"+(index++);
    }
}
