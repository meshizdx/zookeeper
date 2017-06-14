package com.zdx.zookeeper.zookeeperconfig.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


/**
 * Created by BF100177 on 2017/6/8.
 */

@Configuration
public class ZkConfig {

    @Value("${zookeeper.url}")
    private String zkUrl;
    @Value("${zookeeper.session.timeout}")
    private String sessionTimeOut;

    public String getZkUrl() {
        return zkUrl;
    }

    public void setZkUrl(String zkUrl) {
        this.zkUrl = zkUrl;
    }

    public String getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(String sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }
}
