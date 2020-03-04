/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: WatcherApi
 * Author:   Administrator
 * Date:     2020-03-04 11:37
 * Description: 实现Watcher监听
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.zookeeperDemo.configuration.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈一句话功能简述〉<br>
 * 〈实现Watcher监听〉
 *
 * @author Administrator
 * @create 2020-03-04
 * @since 1.0.0
 */
public class WatcherApi implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(WatcherApi.class);

    @Override
    public void process(WatchedEvent event) {
        logger.info("【Watcher监听事件】={}", event.getState());
        logger.info("【监听路径为】={}", event.getPath());
        logger.info("【监听的类型为】={}", event.getType()); //  三种监听类型： 创建，删除，更新
    }
}