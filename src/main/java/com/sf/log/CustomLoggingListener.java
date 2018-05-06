package com.sf.log;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sf.log.define.AccessLoggerInfo;
import com.sf.log.define.AccessLoggerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author lijie.zh
 */
@Component
@Slf4j
public class CustomLoggingListener implements AccessLoggerListener {
    BlockingQueue<AccessLoggerInfo> queue = new LinkedBlockingQueue<AccessLoggerInfo>();

    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("loggingConsumer-%d").build();
    ExecutorService executor = new ThreadPoolExecutor(2, 10, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(5),
            namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    @Override
    public void onLogger(AccessLoggerInfo loggerInfo) {
        queue.add(loggerInfo);
        executor.execute(() -> {
            AccessLoggerInfo info = queue.poll();
            if (info != null) {
                log.info(Thread.currentThread().getName() + "获取到一个" + AccessLoggerInfo.class.getSimpleName() + "对象" + info.getUrl());
            }
        });
    }


}