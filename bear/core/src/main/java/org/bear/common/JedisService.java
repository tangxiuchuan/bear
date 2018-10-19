package org.bear.common;

public interface JedisService {

    public String get(String key);//获取String的值
    public String set(String key, String value);//设置String的值
    public String hget(String hkey, String key);//获取hash的值
    public long hset(String hkey, String key, String value);//设置hash的值
    public long incr(String key);//获取递增的结果
    public long expire(String key, int time);//设置过期时间
    public long ttl(String key);//查询剩余时间
    public long del(String key);//删除指定key的value
    public long hdel(String hkey, String key);//删除hash某个值的key





}
