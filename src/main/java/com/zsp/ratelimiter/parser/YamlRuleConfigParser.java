package com.zsp.ratelimiter.parser;

import com.zsp.ratelimiter.domain.RuleConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class YamlRuleConfigParser implements RuleConfigParser {

    @Override
    public RuleConfig parse(InputStream in) {
        Yaml yaml = new Yaml();
        return yaml.loadAs(in, RuleConfig.class);
    }
}
