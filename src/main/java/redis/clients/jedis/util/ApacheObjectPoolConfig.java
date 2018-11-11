package redis.clients.jedis.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ApacheObjectPoolConfig extends GenericObjectPoolConfig implements RedisObjectPoolConfig {

    public ApacheObjectPoolConfig(GenericObjectPoolConfig genericObjectPoolConfig){
        if(genericObjectPoolConfig != null) {
            copy(genericObjectPoolConfig);
        }
    }

    private void copy(GenericObjectPoolConfig gopc) {
        setMaxIdle(gopc.getMaxIdle()    );
        setMaxTotal(gopc.getMaxTotal());
        setBlockWhenExhausted(gopc.getBlockWhenExhausted());
        setEvictionPolicyClassName(gopc.getEvictionPolicyClassName());
        setEvictorShutdownTimeoutMillis(gopc.getEvictorShutdownTimeoutMillis());
        setFairness(gopc.getFairness());
        setJmxEnabled(gopc.getJmxEnabled());
        setJmxNameBase(gopc.getJmxNameBase());
        setJmxNamePrefix(gopc.getJmxNamePrefix());
        setLifo(gopc.getLifo());
        setMaxWaitMillis(gopc.getMaxWaitMillis());
        setMinEvictableIdleTimeMillis(gopc.getMinEvictableIdleTimeMillis());
        setMinIdle(gopc.getMinIdle());
        setNumTestsPerEvictionRun(gopc.getNumTestsPerEvictionRun());
        setSoftMinEvictableIdleTimeMillis(gopc.getSoftMinEvictableIdleTimeMillis());
        setTestOnBorrow(gopc.getTestOnBorrow());
        setTestOnCreate(gopc.getTestOnCreate());
        setTestOnReturn(gopc.getTestOnReturn());
        setTestWhileIdle(gopc.getTestWhileIdle());
        setTimeBetweenEvictionRunsMillis(gopc.getTimeBetweenEvictionRunsMillis());

    }

}
