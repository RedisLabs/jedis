package redis.clients.jedis;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.util.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.URI;

public class JedisPool implements ConnectionPool<Jedis> {
    private RedisObjectPool<Jedis> jedisPool;

    public JedisPool() {
        this(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    public JedisPool(GenericObjectPoolConfig config, PooledObjectFactory factory) {
        jedisPool = new Pool<>(config,factory);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host) {
        this(poolConfig, host, Protocol.DEFAULT_PORT);
    }

    public JedisPool(String host, int port) {
        this(new GenericObjectPoolConfig(), host, port);
    }

    /**
     * This constructor uses the apache2 pool there is no other way keeps backwards compatibility
     *
     * @param host
     */
    public JedisPool(final String host) {
        URI uri = URI.create(host);
        if (JedisURIHelper.isValid(uri)) {
            jedisPool = new Pool<>(new GenericObjectPoolConfig(), new JedisFactory(uri,
                    Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, null));
        } else {
            jedisPool = new Pool<>(new GenericObjectPoolConfig(), new JedisFactory(host,
                    Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, null,
                    Protocol.DEFAULT_DATABASE, null));
        }
    }

    /**
     * This constructor uses the apache2 pool there is no other way keeps backwards compatibility
     *
     * @param host
     * @param sslSocketFactory
     * @param sslParameters
     * @param hostnameVerifier
     */
    public JedisPool(final String host, final SSLSocketFactory sslSocketFactory,
                     final SSLParameters sslParameters, final HostnameVerifier hostnameVerifier) {
        URI uri = URI.create(host);
        if (JedisURIHelper.isValid(uri)) {
            jedisPool = new Pool<>(new GenericObjectPoolConfig(), new JedisFactory(uri,
                    Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, null, sslSocketFactory, sslParameters,
                    hostnameVerifier));
        } else {
            jedisPool = new Pool<>(new GenericObjectPoolConfig(), new JedisFactory(host,
                    Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, null,
                    Protocol.DEFAULT_DATABASE, null, false, null, null,
                    null));
        }
    }

    public JedisPool(final URI uri) {
        this(new GenericObjectPoolConfig(), uri);
    }

    public JedisPool(final URI uri, final SSLSocketFactory sslSocketFactory,
                     final SSLParameters sslParameters, final HostnameVerifier hostnameVerifier) {
        this(new GenericObjectPoolConfig(), uri, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public JedisPool(final URI uri, final int timeout) {
        this(new GenericObjectPoolConfig(), uri, timeout);
    }

    public JedisPool(final URI uri, final int timeout, final SSLSocketFactory sslSocketFactory,
                     final SSLParameters sslParameters, final HostnameVerifier hostnameVerifier) {
        this(new GenericObjectPoolConfig(), uri, timeout, sslSocketFactory, sslParameters,
                hostnameVerifier);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password) {
        this(poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final boolean ssl) {
        this(poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE, ssl);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final boolean ssl,
                     final SSLSocketFactory sslSocketFactory, final SSLParameters sslParameters,
                     final HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE, ssl,
                sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port) {
        this(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                     final boolean ssl) {
        this(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, ssl);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                     final boolean ssl, final SSLSocketFactory sslSocketFactory, final SSLParameters sslParameters,
                     final HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, ssl, sslSocketFactory, sslParameters,
                hostnameVerifier);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                     final int timeout) {
        this(poolConfig, host, port, timeout, null);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                     final int timeout, final boolean ssl) {
        this(poolConfig, host, port, timeout, null, ssl);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                     final int timeout, final boolean ssl, final SSLSocketFactory sslSocketFactory,
                     final SSLParameters sslParameters, final HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, timeout, null, ssl, sslSocketFactory, sslParameters,
                hostnameVerifier);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final int database) {
        this(poolConfig, host, port, timeout, password, database, null);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final int database, final boolean ssl) {
        this(poolConfig, host, port, timeout, password, database, null, ssl);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final int database, final boolean ssl,
                     final SSLSocketFactory sslSocketFactory, final SSLParameters sslParameters,
                     final HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, timeout, password, database, null, ssl, sslSocketFactory,
                sslParameters, hostnameVerifier);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final int database, final String clientName) {
        this(poolConfig, host, port, timeout, timeout, password, database, clientName);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final int database, final String clientName,
                     final boolean ssl) {
        this(poolConfig, host, port, timeout, timeout, password, database, clientName, ssl);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final int database, final String clientName,
                     final boolean ssl, final SSLSocketFactory sslSocketFactory,
                     final SSLParameters sslParameters, final HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, timeout, timeout, password, database, clientName, ssl,
                sslSocketFactory, sslParameters, hostnameVerifier);
    }

    /**
     * This constructor uses the apache2 pool there is no other way keeps backwards compatibility
     *
     * @param poolConfig
     * @param host
     * @param port
     * @param connectionTimeout
     * @param soTimeout
     * @param password
     * @param database
     * @param clientName
     * @param ssl
     * @param sslSocketFactory
     * @param sslParameters
     * @param hostnameVerifier
     */
    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     final int connectionTimeout, final int soTimeout, final String password, final int database,
                     final String clientName, final boolean ssl, final SSLSocketFactory sslSocketFactory,
                     final SSLParameters sslParameters, final HostnameVerifier hostnameVerifier) {
        jedisPool = new Pool<>(poolConfig, new JedisFactory(host, port, connectionTimeout, soTimeout, password,
                database, clientName, ssl, sslSocketFactory, sslParameters, hostnameVerifier));
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig) {
        this(poolConfig, Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    public JedisPool(final String host, final int port, final boolean ssl) {
        this(new GenericObjectPoolConfig(), host, port, ssl);
    }

    /**
     * This constructor uses the apache2 pool there is no other way keeps backwards compatibility
     *
     * @param poolConfig
     * @param host
     * @param port
     * @param connectionTimeout
     * @param soTimeout
     * @param password
     * @param database
     * @param clientName
     */
    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     final int connectionTimeout, final int soTimeout, final String password, final int database,
                     final String clientName) {
        jedisPool = new Pool<>(poolConfig, new JedisFactory(host, port, connectionTimeout, soTimeout, password,
                database, clientName));
    }

    public JedisPool(final String host, final int port, final boolean ssl,
                     final SSLSocketFactory sslSocketFactory, final SSLParameters sslParameters,
                     final HostnameVerifier hostnameVerifier) {
        this(new GenericObjectPoolConfig(), host, port, ssl, sslSocketFactory, sslParameters,
                hostnameVerifier);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                     final int connectionTimeout, final int soTimeout, final String password, final int database,
                     final String clientName, final boolean ssl) {
        this(poolConfig, host, port, connectionTimeout, soTimeout, password, database, clientName, ssl,
                null, null, null);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final URI uri) {
        this(poolConfig, uri, Protocol.DEFAULT_TIMEOUT);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final URI uri,
                     final SSLSocketFactory sslSocketFactory, final SSLParameters sslParameters,
                     final HostnameVerifier hostnameVerifier) {
        this(poolConfig, uri, Protocol.DEFAULT_TIMEOUT, sslSocketFactory, sslParameters,
                hostnameVerifier);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final URI uri, final int timeout) {
        this(poolConfig, uri, timeout, timeout);
    }

    public JedisPool(final GenericObjectPoolConfig poolConfig, final URI uri, final int timeout,
                     final SSLSocketFactory sslSocketFactory, final SSLParameters sslParameters,
                     final HostnameVerifier hostnameVerifier) {
        this(poolConfig, uri, timeout, timeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    /**
     * This constructor uses the apache2 pool there is no other way keeps backwards compatibility
     *
     * @param poolConfig
     * @param uri
     * @param connectionTimeout
     * @param soTimeout
     */
    public JedisPool(final GenericObjectPoolConfig poolConfig, final URI uri,
                     final int connectionTimeout, final int soTimeout) {
        jedisPool = new Pool<>(poolConfig, new JedisFactory(uri, connectionTimeout, soTimeout, null));
    }

    /**
     * This constructor uses the apache2 pool there is no other way keeps backwards compatibility
     *
     * @param poolConfig
     * @param uri
     * @param connectionTimeout
     * @param soTimeout
     * @param sslSocketFactory
     * @param sslParameters
     * @param hostnameVerifier
     */
    public JedisPool(final GenericObjectPoolConfig poolConfig, final URI uri,
                     final int connectionTimeout, final int soTimeout, final SSLSocketFactory sslSocketFactory,
                     final SSLParameters sslParameters, final HostnameVerifier hostnameVerifier) {
        jedisPool = new Pool<>(poolConfig, new JedisFactory(uri, connectionTimeout, soTimeout, null, sslSocketFactory,
                sslParameters, hostnameVerifier));
    }

    @Override
    public boolean isClosed() {
        return jedisPool.isClosed();
    }

    @Override
    public Jedis getResource() {
        Jedis jedis = jedisPool.getResource();
        jedis.setDataSource(jedisPool);
        return jedis;
    }

    @Override
    public void returnBrokenResource(final Jedis resource) {
        jedisPool.returnBrokenResource(resource);
    }

    @Override
    public void destroy() {
        jedisPool.destroy();
    }

    @Override
    public int getNumActive() {
        return jedisPool.getNumActive();
    }

    @Override
    public int getNumIdle() {
        return jedisPool.getNumIdle();
    }

    @Override
    public void addObjects(int count) {
        jedisPool.addObjects(count);
    }

    @Override
    public void returnResource(final Jedis resource) {
        try {
            resource.resetState();
            jedisPool.returnResource(resource);
        } catch (Exception e) {
            returnBrokenResource(resource);
            throw new JedisException("Resource is returned to the pool as broken", e);
        }
    }


    @Override
    public void close() throws IOException {
        jedisPool.close();
    }
}
