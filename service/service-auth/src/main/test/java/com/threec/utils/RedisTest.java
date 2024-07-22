package com.threec.utils;

import com.threec.redis.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;


/**
 * Class RedisTest.
 * <p>
 *
 * </p>
 *
 * @author laven
 * @version 1.0
 * @since 24/6/24
 */

@SpringBootTest
public class RedisTest {

    @Test
    void redisTest() {
        RedisUtils.StringOps.set("s", "women");
    }

    @Test
    void redisTest2() {
        RedisUtils.StringOps.setEx("ssss","asad",7, TimeUnit.SECONDS);
        for (int i = 1; i < 10; i++) {
            try {
                Thread.sleep(1000);  // 睡眠1秒钟
                System.out.println(i);
                boolean b = RedisUtils.KeyOps.hasKey("ssss");
                if(b){
                    System.out.println("********");
                }else {
                    System.out.println("--------");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Thread interrupted", e);
            }

        }
    }
}

