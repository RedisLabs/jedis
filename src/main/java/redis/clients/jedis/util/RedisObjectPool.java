package redis.clients.jedis.util;

import java.io.Closeable;

public interface RedisObjectPool<T> extends Closeable {

    boolean isClosed();

    T getResource();

    void returnResource(final T resource);

    void destroy();

    void returnBrokenResource(final T resource);

    void init(RedisObjectPoolConfig redisObjectPoolConfig, RedisObjectFactory redisObjectFactory);

    void clear();

    int getNumActive();

    int getNumIdle();

    void addObjects(int count);

}
