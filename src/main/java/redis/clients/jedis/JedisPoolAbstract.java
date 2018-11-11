package redis.clients.jedis;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.util.Pool;

public class JedisPoolAbstract extends Pool<Jedis> {

  public JedisPoolAbstract() {
    super();
  }

  public JedisPoolAbstract(GenericObjectPoolConfig poolConfig, PooledObjectFactory<Jedis> factory) {
    super(poolConfig, factory);
  }

  @Override
  public void returnBrokenResource(Jedis resource) {
    super.returnBrokenResource(resource);
  }

  @Override
  public void returnResource(Jedis resource) {
    super.returnResource(resource);
  }
}
