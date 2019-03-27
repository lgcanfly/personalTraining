package cache.redis.client.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.util.Collections;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/25 Created
 */
public class RedisDistrictLock {
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] \n" +
            "then\n" +
            "  return redis.call('del', KEYS[1])\n" +
            "else\n" +
            "  return 0\n" +
            "end\n";



    public static boolean lock(ShardedJedis jedis, String key, String value) {
        if (jedis.set(key, value, "NX", "PX", 1000).equals("OK")) {
            return true;
        }

        return false;
    }

    public static boolean release(ShardedJedis jedis, String key, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Jedis j = jedis.getShard(key);
        Object result = j.eval(script, Collections.singletonList(key), Collections.singletonList(value));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }

        return false;
    }
}
