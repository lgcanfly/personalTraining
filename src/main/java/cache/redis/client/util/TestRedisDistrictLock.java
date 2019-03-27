package cache.redis.client.util;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/25 Created
 */
public class TestRedisDistrictLock {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(8);

        ShardedJedisPool pool = ShardedJedisPoolUtil.getInstance().getShardedJedisPool();

        Integer count = 0;

        String key = "add";
        String value = "";

        for (int i = 0; i < 100; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    ShardedJedis jedis = null;
                    try {
                        jedis = pool.getResource();
                        while (!RedisDistrictLock.lock(jedis, key, value)) {
                            Thread.sleep(200);
                        }

                    }
                    catch (Exception e) {

                    }
                    finally {
                        RedisDistrictLock.release(jedis, key, value);
                        pool.returnResource(jedis);
                    }

                }
            });
        }
    }
}
