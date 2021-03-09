package com.zsp.ratelimiter.alg;


import com.google.common.base.Stopwatch;
import com.zsp.ratelimiter.exception.InternalErrorException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public interface RateLimitAlg {

    boolean tryAcquire() throws InternalErrorException;
}
