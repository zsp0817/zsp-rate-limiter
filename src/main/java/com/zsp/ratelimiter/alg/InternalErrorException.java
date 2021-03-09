package com.zsp.ratelimiter.alg;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class InternalErrorException extends Exception {

    public InternalErrorException(String message) {
        super(message);
    }

    public InternalErrorException(String message, Throwable e) {
        super(message, e);
    }
}
