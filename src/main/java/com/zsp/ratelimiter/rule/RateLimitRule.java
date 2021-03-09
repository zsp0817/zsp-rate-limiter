package com.zsp.ratelimiter.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class RateLimitRule {

    Map<String, ApiLimit> apiLimitMap = new HashMap<>();

    public RateLimitRule(RuleConfig config) {
        for (AppRuleConfig appRuleConfig : config.getConfigs()) {
            String appId = appRuleConfig.getAppId();
            List<ApiLimit> apiLimits = appRuleConfig.getLimits();
            for (ApiLimit apiLimit : apiLimits) {
                String api = apiLimit.getApi();
                apiLimitMap.put(appId + "/" + api, apiLimit);
            }
        }
    }

    public ApiLimit getLimit(String appId, String api) {
        return apiLimitMap.get(appId + "/" + api);
    }
}