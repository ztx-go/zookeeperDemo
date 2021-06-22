package com.example.zookeeperDemo.configuration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZkServerConfig {
    @Value("${zookeeper.address}")
    private String zkAddress;

    @Bean
    public CuratorFramework initCurator() {
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zkAddress, retry);
        curatorFramework.getCuratorListenable().addListener(new ZkListener() {
        });
        curatorFramework.start();
        return curatorFramework;

    }

    /**
     * 监听
     */
    public class ZkListener implements CuratorListener {

        @Override
        public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
            CuratorEventType eventType = event.getType();
            if (eventType == CuratorEventType.WATCHED) {
                WatchedEvent eventWatchedEvent = event.getWatchedEvent();
                String eventPath = eventWatchedEvent.getPath();
                if (null != eventPath) {
                    client.checkExists().watched().forPath(eventPath);
                }
            }
        }
    }
}
