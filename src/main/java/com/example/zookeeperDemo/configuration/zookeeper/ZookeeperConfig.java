/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ZookeeperConfig
 * Author:   Administrator
 * Date:     2020-03-04 11:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.zookeeperDemo.configuration.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

/**
 * 〈一句话功能简述〉<br>
 * 〈参考博文：：https://blog.csdn.net/u010391342/article/details/100404588〉
 *
 * @author Administrator
 * @create 2020-03-04
 * @since 1.0.0
 */
@Configuration
public class ZookeeperConfig {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConfig.class);

    @Value("${zookeeper.address}")
    private String connectString;

    @Value("${zookeeper.timeout}")
    private int timeout;


    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zooKeeper = null;
        try {
            //CountDownLatch相当于一个计数器
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //连接成功后，会回调watcher监听，此连接操作是异步的，执行完new语句后，直接调用后续代码
            //  可指定多台服务地址 127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183
            zooKeeper = new ZooKeeper(connectString, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (Event.KeeperState.SyncConnected == event.getState()) {
                        //如果收到了服务端的响应事件,连接成功
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
            logger.info("【初始化ZooKeeper连接状态....】={}", zooKeeper.getState());

        } catch (Exception e) {
            logger.error("初始化ZooKeeper连接异常....】={}", e);
        }
        return zooKeeper;
    }
}