package com.springbootcache.service.impl;

import com.springbootcache.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {
    @Override
    @Async
    public void printAsync() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("异步任务测试......");
    }

    /**
     *  second(秒) minute(分) hour(时) day of month(日) month(月) day of week(周几)
     */
    @Override
//    周一到周6 每0秒的时候开启
//    @Scheduled(cron = "0 * * * * MON-SAT")
//    周一到周6 每0,1,2,3,4秒的时候开启
    @Scheduled(cron = "0,1,2,3,4 * * * * MON-SAT")
    public void printSchedule() {
        System.out.println("定时任务测试......");
    }
}
