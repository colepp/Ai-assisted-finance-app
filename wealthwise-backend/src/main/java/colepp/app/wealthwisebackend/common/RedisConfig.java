package colepp.app.wealthwisebackend.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.user}")
    private String redisUser;

    @Value("${redis.pass}")
    private String redisPassword;

    @Bean
    public Jedis redisPool() {
        try{
            var jedisPool = new JedisPool(redisHost,redisPort);
            return jedisPool.getResource();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
