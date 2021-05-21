package com.example.redisutil;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisCom {

   public JedisPool getJedisPool(){
       JedisPoolConfig jcon = new JedisPoolConfig();
       jcon.setMaxTotal(200);
       jcon.setMaxIdle(50);
       jcon.setTestOnBorrow(true);
       jcon.setTestOnReturn(true);
       JedisPool jedisPool = new JedisPool(jcon,"127.0.0.1",6379,30000);
       return jedisPool;
   }

    // 获取键
    public boolean setnx(String key, String val) {
        Jedis jedis = null;
        try {
            JedisPool jedisPool = getJedisPool();
            jedis = jedisPool.getResource();
            if(jedis == null) {
                return false;
            }
            long flag = jedis.setnx(key, val);
            if(flag == 1l){
                jedis.expire(key, 60);
                return true;
            }else{
                return false;
            }
        } catch(Exception ex) {
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    // 删除键
    public int delnx(String key, String val) {
        Jedis jedis = null;
        try {
            JedisPool jedisPool = getJedisPool();
            jedis = jedisPool.getResource();
            if(jedis == null) {
                return 0;
            }
            // if redis.call('get','orderkey')=='1111' then return redis.call('del','orderkey') else return 0 end
            StringBuilder sbScript = new StringBuilder();
            sbScript.append("if redis.call('get','").append(key).append("')")
                    .append("=='").append(val).append("'"). append(" then ")
                    .append(" return redis.call('del','").append(key).append("')")
                    .append(" else "). append(" return 0"). append(" end");
            return Integer.valueOf(jedis.eval(sbScript.toString()).toString());
        } catch (Exception ex) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

}
