package com.sf;

import com.sf.log.CustomLoggingListener;
import com.sf.log.define.AccessLoggerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhonglj on 2017/12/2.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SysBootApplication.class)
public class Demo {


@Autowired
CustomLoggingListener myLoggingListener;


    @Test
    public void t() throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(3);
        for (int index = 0; index < 100; index++) {

            exec.execute(() -> {
                System.out.println("生产对象的线程"+Thread.currentThread().getName());
                AccessLoggerInfo info = new AccessLoggerInfo();
                myLoggingListener.onLogger(info);
            });
        }
        Thread.sleep(Integer.MAX_VALUE);
        System.out.println("哈哈哈");
    }
}
