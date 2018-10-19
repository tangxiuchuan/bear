package org.yufan.front;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {


    @Test
    public void fun1(){

        Jedis jedis=new Jedis("localhost");
        jedis.set("xp","haha");
        String json=jedis.get("xp");

    }

    @Test
    public void test2(){

        JedisPool jedisPool=new JedisPool("localhost",6379);

        Jedis jedis = jedisPool.getResource();

        jedis.hset("001","xp1","sdsd1");
        jedis.hset("001","xp2","sdsd2");

        String json=jedis.hget("001","xp1");


    }

    @Test
    public void fun3(){

        JedisPool jedisPool=new JedisPool("localhost",6379);

        Jedis jedis = jedisPool.getResource();

        jedis.lpush("aa","aa1");
        jedis.lpush("aa","aa2");
        jedis.lpush("aa","aa3");
        jedis.lpush("aa","aa4");

        String json=jedis.lpop("aa");

    }


}
