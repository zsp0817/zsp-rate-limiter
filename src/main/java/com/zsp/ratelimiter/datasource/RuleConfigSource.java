package com.zsp.ratelimiter.datasource;

import com.zsp.ratelimiter.domain.RuleConfig;

import java.io.InputStream;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public interface RuleConfigSource {

    RuleConfig load();
}
