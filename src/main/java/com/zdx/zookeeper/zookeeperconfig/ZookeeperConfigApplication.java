package com.zdx.zookeeper.zookeeperconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
public class ZookeeperConfigApplication {



	public static void main(String[] args) {
		SpringApplication.run(ZookeeperConfigApplication.class, args);
	}
}
