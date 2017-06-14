package com.zdx.zookeeper.zookeeperconfig.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by BF100177 on 2017/6/8.
 */
@Configuration
public class ZkClient implements Watcher {

    @Autowired
    private ZkConfig zkConfig;

    @Bean
    public ZooKeeper initzk() throws IOException {
        return new ZooKeeper(zkConfig.getZkUrl(),Integer.parseInt(zkConfig.getSessionTimeOut()),this);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.toString());
    }
}
