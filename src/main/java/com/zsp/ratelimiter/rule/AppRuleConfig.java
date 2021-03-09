package com.zsp.ratelimiter.rule;

import java.util.List;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class AppRuleConfig {

    private String appId;
    private List<ApiLimit> limits;

    public AppRuleConfig() {
    }

    public AppRuleConfig(String appId, List limits) {
        this.appId = appId;
        this.limits = limits;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<ApiLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<ApiLimit> limits) {
        this.limits = limits;
    }
}
