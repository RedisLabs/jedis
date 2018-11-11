package redis.clients.jedis;

import java.io.Closeable;

public interface ConnectionPool<T> extends Closeable {

    boolean isClosed();

    T getResource();

    void returnResource(T resource);

    void returnBrokenResource(T resource);

    void destroy();

    int getNumActive();

    int getNumIdle();

    void addObjects(int count);
}
