package com.zsp.ratelimiter.domain;

import java.util.List;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class RuleConfig {

    private List<AppRuleConfig> configs;

    public RuleConfig() {
    }

    public List<AppRuleConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AppRuleConfig> configs) {
        this.configs = configs;
    }
}
