package org.bear.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisServiceImpl implements JedisService {

    private JedisPool jedisPool;

    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    @Override
    public String get(String key) {
        Jedis jedis = getJedis();
        String s=jedis.get(key);
        jedis.close();
        return s;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = getJedis();
        String s=jedis.set(key,value);
        jedis.close();
        return s;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = getJedis();
        String s=jedis.hget(hkey,key);
        jedis.close();
        return s;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = getJedis();
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }

    @Override
    public long incr(String key) {
        Jedis jedis = getJedis();
        long result=jedis.incr(key);
        jedis.close();
        return result;
    }

    @Override
    public long expire(String key, int time) {
        Jedis jedis = getJedis();
        long result=jedis.expire(key,time);
        jedis.close();
        return result;
    }

    @Override
    public long ttl(String key) {
        Jedis jedis = getJedis();
        long result=jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public long del(String key) {
        Jedis jedis = getJedis();
        long result=jedis.del(key);
        jedis.close();
        return result;
    }

    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis = getJedis();
        long result=jedis.hdel(hkey,key);
        jedis.close();
        return result;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
