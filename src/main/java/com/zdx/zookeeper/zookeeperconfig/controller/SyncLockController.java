package com.zdx.zookeeper.zookeeperconfig.controller;

import com.zdx.zookeeper.zookeeperconfig.config.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Vector;

/**
 * 多线程同步锁
 * Created by BF100177 on 2017/6/9.
 */
@RestController
public class SyncLockController {

    @Autowired
    private ZkConfig zkConfig;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private CuratorFramework framework;

    @RequestMapping("/lock")
    public String syncLock() throws InterruptedException {

            final InterProcessMutex lock = new InterProcessMutex(framework, "/myLock");
        Vector<Thread> threads = new Vector<Thread>();
        for (int i = 0; i < 20; i++) {
            Thread.sleep(500);
            final int id = i+1;
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.acquire();
                        System.out.println("====================================================================");
                        System.out.println("server: " + zkConfig.getZkUrl() + "线程[" + id + "]获得锁");
                        int money = (Integer) redisTemplate.opsForValue().get("MANDAO:ASSET:BIZ:LOCK:TESTMONEY");
                        System.out.println("取款前从redis获取余额为：" + money);
                        if(money - 5000 < 0){
                            System.out.println("取款失败，余额不足！");
                            return;
                        }
                        redisTemplate.opsForValue().set("MANDAO:ASSET:BIZ:LOCK:TESTMONEY",money-5000);
                        money = (Integer) redisTemplate.opsForValue().get("MANDAO:ASSET:BIZ:LOCK:TESTMONEY");
                        System.out.println("取款后从redis获取余额为" + money);
                        System.out.println("====================================================================");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread iThread : threads) {
            try {
                // 等待所有线程执行完毕
                iThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "success";
    }
}




