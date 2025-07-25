package colepp.app.wealthwisebackend.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.UnifiedJedis;

public class JedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Value("${redis.user}")
    private String redisUser;

    @Value("${redis.pass}")
    private String redisPassword;

    @Bean
    UnifiedJedis jedisConnectionFactory(){
        var redisUrl = new HostAndPort(redisHost,redisPort);
        try {
            return new UnifiedJedis(redisUrl);
        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        var connection = jedisConnectionFactory();
        return redisTemplate;
    }
}
