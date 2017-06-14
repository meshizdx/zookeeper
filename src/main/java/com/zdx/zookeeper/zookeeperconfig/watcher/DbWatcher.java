package com.zdx.zookeeper.zookeeperconfig.watcher;

import com.zdx.zookeeper.zookeeperconfig.config.ZkConfig;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by BF100177 on 2017/6/8.
 */
@Service
public class DbWatcher implements Watcher{

    @Autowired
    private ZkConfig zkConfig;

    private ZooKeeper zk;


    // client获取的数据库信息
    private String uRL;

    private String userName;

    private String passwd;

    // Auth认证
    public static String authType = "digest" ;
    public static String authPasswd = "dbAuth" ;



    /**
     * 获取zk中的配置信息
     * */
    @PostConstruct
    private void initValue(){
        try {
           if(zk == null){
               zk = getZk();
           }
            this.uRL = new String(zk.getData("/dbConf/urlNode",true,null));
            System.out.println("获取参数 - url："+this.uRL);
            this.userName = new String(zk.getData("/dbConf/userNameNode" , true, null));
            System.out.println("获取参数 - userName："+this.userName);
            this.passwd = new String(zk.getData("/dbConf/passwordNode" , true, null));
            System.out.println("获取参数 - passwd："+this.passwd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取 zk连接
     */
    public ZooKeeper getZk(){
        ZooKeeper zk = null;
        try {
            System.out.println("当前ZK服务器地址：" + zkConfig.getZkUrl());
            zk = new ZooKeeper(zkConfig.getZkUrl(),Integer.parseInt(zkConfig.getSessionTimeOut()),this);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 加权限
        zk.addAuthInfo( authType,authPasswd .getBytes());
        return zk;
    }

    @Override
    public void process(WatchedEvent event) {

        String message = "";

        Event.EventType type = event.getType();

        if (type .equals(Event.EventType.None)) {
            message = "连接 zk 成功!!!";
        } else if (type.equals(Event.EventType.NodeCreated)) {
            message = "znode 创建成功!!!";
        } else if (type.equals(Event.EventType.NodeChildrenChanged)) {
            message = "znode 子节点创建成功!!!";
        } else if (type.equals(Event.EventType.NodeDataChanged)) {
            message = "znode 数据改变，从新获取配置信息";
            initValue();
        } else if (type.equals(Event.EventType.NodeDeleted)) {
            message = "znode 删除成功!!!";
        }
        System. out.println(message );

    }
}
