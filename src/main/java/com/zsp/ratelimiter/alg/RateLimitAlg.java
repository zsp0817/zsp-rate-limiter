package com.zsp.ratelimiter.alg;


import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangshaopeng on 2021/3/9.
 */
public class RateLimitAlg {

    // 锁超时时间 200ms
    private static final long TRY_LOCK_TIMEOUT = 200L;

    // 来自guava类库，类似秒表
    private Stopwatch stopwatch;
    private AtomicInteger currentCount = new AtomicInteger(0);
    private final int limit;
    private final int unit;
    private Lock lock = new ReentrantLock();

    public RateLimitAlg(int limit, int unit) {
        this(limit, unit, Stopwatch.createStarted());
    }

    protected RateLimitAlg(int limit, int unit, Stopwatch stopwatch) {
        this.limit = limit;
        this.unit = unit;
        this.stopwatch = stopwatch;
    }

    /**
     * 尝试能否获取接口
     *
     * @return
     * @throws InternalErrorException
     */
    public boolean tryAcquire() throws InternalErrorException {
        int updatedCount = currentCount.incrementAndGet();
        if (updatedCount <= limit) {
            return true;
        }

        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMillis(unit)) {
                        currentCount.set(0);
                        stopwatch.reset();
                    }
                    updatedCount = currentCount.incrementAndGet();
                    return updatedCount <= limit;
                } finally {
                    lock.unlock();
                }
            } else {
                throw new InternalErrorException("tryAcquire() wait lock too long:" + TRY_LOCK_TIMEOUT + "ms");
            }
        } catch (InterruptedException e) {
            throw new InternalErrorException("tryAcquire() is interrupted by lock-time-out.", e);
        }
    }
}
