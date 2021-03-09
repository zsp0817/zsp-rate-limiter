package com.zsp.ratelimiter.domain;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class ApiLimit {

    // 默认时间单位，1s
    private static final int DEFAULT_TIME_UNIT = 1;

    /**
     * API
     */
    private String api;

    /**
     * 限制数
     */
    private int limit;

    /**
     * 时间范围，默认1s
     */
    private int unit = DEFAULT_TIME_UNIT;

    public ApiLimit() {
    }

    public ApiLimit(String api, int limit) {
        this(api, limit, DEFAULT_TIME_UNIT);
    }

    public ApiLimit(String api, int limit, int unit) {
        this.api = api;
        this.limit = limit;
        this.unit = unit;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}