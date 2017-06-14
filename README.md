# zookeeper
配置管理中心，分布式锁
### 初始化设置
    设置application.properties 完成 zookeeper 设置和 redis设置（redis作为分布式锁存放数值测试使用）
    ZookeeperConfigApplicationTests.contextLoads() 初始化 redis中 测试数值。（分布式锁使用）
    
### 配置中心
    首先运行 com.zdx.zookeeper.zookeeperconfig.config.ConfigSetting.main()方法初始化 zk 存放配置文件节点（尚未做spring启动自动化创建，需要手动运行main()）
    DbWatcher.java 为监听节点变化类
    ZkController.java 通过页面修改配置属性，观察控制台打印配置更新变化
### 分布式锁
    分布式锁用 curator 框架 实现 
    SyncLockController.syncLock() 模拟10个线程 测试分布式锁
    
