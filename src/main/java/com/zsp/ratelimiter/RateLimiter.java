package com.zsp.ratelimiter;

import com.zsp.ratelimiter.alg.InternalErrorException;
import com.zsp.ratelimiter.alg.RateLimitAlg;
import com.zsp.ratelimiter.rule.ApiLimit;
import com.zsp.ratelimiter.rule.RateLimitRule;
import com.zsp.ratelimiter.rule.RuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class RateLimiter {
    private static final Logger log = LoggerFactory.getLogger(RateLimiter.class);

    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();
    private RateLimitRule rule;

    public RateLimiter() {
        InputStream in = null;
        RuleConfig ruleConfig = null;

        try {
            in = this.getClass().getResourceAsStream("/ratelimiter-rule.yaml");
            if (in != null) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(in, RuleConfig.class);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("close file error:{}", e);
                }
            }
        }

        this.rule = new RateLimitRule(ruleConfig);
    }

    public boolean limit(String appId, String api) throws InternalErrorException {
        ApiLimit apiLimit = rule.getLimit(appId, api);
        if (apiLimit == null) {
            return true;
        }

        String counterKey = appId + ":" + apiLimit.getApi();
        RateLimitAlg rateLimitCounter = counters.get(counterKey);
        if (rateLimitCounter == null) {
            RateLimitAlg newRateLimitCounter = new RateLimitAlg(apiLimit.getLimit(), apiLimit.getUnit());
            rateLimitCounter = counters.putIfAbsent(counterKey, newRateLimitCounter);
            if (rateLimitCounter == null) {
                rateLimitCounter = newRateLimitCounter;
            }
        }

        // 判断是否限流
        return rateLimitCounter.tryAcquire();
    }
}
