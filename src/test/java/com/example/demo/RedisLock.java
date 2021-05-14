package com.example.distributedemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.awt.print.Book;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author zhuzhaoman
 * @date 2020/5/2 0002 17:05
 * @description 基于redis分布式锁
 */

@RestController
@Slf4j
public class RedisLockController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/redisLock")
    public String redisLock() throws InterruptedException {
        log.info("我进入了方法");

        String key = "redisKey";
        String value = UUID.randomUUID().toString();

        RedisCallback<Boolean> redisCallback = redisConnection -> {
            // 设置NX
            RedisStringCommands.SetOption setOption = RedisStringCommands.SetOption.ifAbsent();
            // 设置过期时间
            Expiration expiration = Expiration.seconds(30);

            // 序列化key和value
            byte[] redisKey = redisTemplate.getKeySerializer().serialize(key);
            byte[] redisValue = redisTemplate.getValueSerializer().serialize(value);

            // 执行set nx 操作
            Boolean result = redisConnection.set(redisKey, redisValue, expiration, setOption);
            return result;
        };

        // 获取分布式锁
        Boolean lock = (Boolean) redisTemplate.execute(redisCallback);
        if (lock) {
            log.info("我进入了锁！");
            Thread.sleep(15000);
        }

        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        List<String> keys = Arrays.asList(key);

        Boolean result = (Boolean) redisTemplate.execute(redisScript, keys, value);
        log.info("释放锁的结果：" + result);


        log.info("方法执行完成");
        return "方法执行完成";
    }
}