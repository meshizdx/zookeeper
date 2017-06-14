package com.zdx.zookeeper.zookeeperconfig.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BF100177 on 2017/6/9.
 */
@Configuration
public class CuratorConfig {

    @Autowired
    private ZkConfig zkConfig;


    @Bean
    public RetryPolicy setRetryPolicy(){
        return new ExponentialBackoffRetry(Integer.parseInt(zkConfig.getSessionTimeOut()),3);
    }

    @Bean
    public CuratorFramework setCuratorFramework(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zkConfig.getZkUrl(),setRetryPolicy());
        curatorFramework.start();
        return curatorFramework;
    }


}
