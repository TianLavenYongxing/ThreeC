package com.threec.utils;

import com.threec.redis.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


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
}

