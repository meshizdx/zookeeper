package com.zdx.zookeeper.zookeeperconfig.config;

import org.apache.zookeeper.*;

/**
 * 初始化zk配置节点（运行main方法，尚未做启动初始化）
 * Created by BF100177 on 2017/6/8.
 */
public class ConfigSetting {

    // zk的连接串
    private final static String CONNECT_STR = "127.0.0.1:2181";

    // 连接zk的超时时间
    private static final int SESSION_TIMEOUT = 30000;

    // 数据库连接
    private final static String uRLNode = "10.12.1.1";

    private final static String userNameNode = "admin";

    private final static String passwdNode = "password";

    // Auth认证
    public static String authType = "digest";
    public static String authPasswd = "dbAuth";

    public static void main(String[] args) {

        ZooKeeper zk = null;

        try {
            zk = new ZooKeeper(CONNECT_STR, SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("watchedEvent Type：" + watchedEvent.getType() + ",path：" + watchedEvent.getPath());
                }
            });

            //auth 权限
            zk.addAuthInfo(authType, authPasswd.getBytes());

            if (zk.exists("/dbConf", true) == null) {
                zk.create("/dbConf", uRLNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (zk.exists("/dbConf/urlNode", true) == null) {
                zk.create("/dbConf/urlNode", uRLNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (zk.exists("/dbConf/userNameNode", true) == null) {
                zk.create("/dbConf/userNameNode", userNameNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            if (zk.exists("/dbConf/passwordNode", true) == null) {
                zk.create("/dbConf/passwordNode", passwdNode.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zk != null) {
                try {
                    zk.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
