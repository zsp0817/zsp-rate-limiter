package com.zsp.ratelimiter;

import com.zsp.ratelimiter.alg.FixedTimeWinRateLimitAlg;
import com.zsp.ratelimiter.alg.RateLimitAlg;
import com.zsp.ratelimiter.datasource.FileRuleConfigSource;
import com.zsp.ratelimiter.datasource.RuleConfigSource;
import com.zsp.ratelimiter.domain.ApiLimit;
import com.zsp.ratelimiter.domain.RuleConfig;
import com.zsp.ratelimiter.exception.InternalErrorException;
import com.zsp.ratelimiter.rule.MapRateLimitRule;
import com.zsp.ratelimiter.rule.RateLimitRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class RateLimiter {
    private static final Logger log = LoggerFactory.getLogger(RateLimiter.class);

    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();
    private RateLimitRule rule;

    public RateLimiter() {
        RuleConfigSource configSource = new FileRuleConfigSource();
        RuleConfig ruleConfig = configSource.load();
        this.rule = new MapRateLimitRule(ruleConfig);
    }

    /**
     * @param appId
     * @param api
     * @return
     * @throws InternalErrorException
     */
    public boolean limit(String appId, String api) throws InternalErrorException {
        ApiLimit apiLimit = rule.getLimit(appId, api);
        if (apiLimit == null) {
            return true;
        }

        String counterKey = appId + ":" + apiLimit.getApi();
        RateLimitAlg rateLimitCounter = counters.get(counterKey);
        if (rateLimitCounter == null) {
            RateLimitAlg newRateLimitCounter = new FixedTimeWinRateLimitAlg(apiLimit.getLimit(), apiLimit.getUnit());
            rateLimitCounter = counters.putIfAbsent(counterKey, newRateLimitCounter);
            if (rateLimitCounter == null) {
                rateLimitCounter = newRateLimitCounter;
            }
        }

        // 判断是否限流
        return rateLimitCounter.tryAcquire();
    }
}
