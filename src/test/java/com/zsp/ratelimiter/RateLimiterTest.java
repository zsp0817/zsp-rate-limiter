package com.zsp.ratelimiter;

import org.junit.Test;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class RateLimiterTest {

    @Test
    public void testLimit() throws Exception {
        RateLimiter rateLimit = new RateLimiter();

        String appId = "app-1";
        String api = "/v1/order";

        for (int i = 0; i < 100; i++) {
            System.out.println(i + ":" + rateLimit.limit(appId, api));
        }
    }

    @Test
    public void testLimitHasUnit() throws Exception {
        RateLimiter rateLimit = new RateLimiter();

        String appId = "app-1";
        String api = "/v1/user";

        for (int i = 0; i < 150; i++) {
            System.out.println(i + ":" + rateLimit.limit(appId, api));
        }

        // 休眠10s
        Thread.sleep(10000);
        System.out.println("final:" + rateLimit.limit(appId, api));
    }
}