package com.zsp.ratelimiter.rule;

import com.zsp.ratelimiter.domain.ApiLimit;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public interface RateLimitRule {

    ApiLimit getLimit(String appId, String api);
}