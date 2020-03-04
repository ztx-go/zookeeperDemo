/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ZkApi
 * Author:   Administrator
 * Date:     2020-03-04 11:33
 * Description: zk客户端zpi封装工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.zookeeperDemo.configuration.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈zk客户端zpi封装工具类〉
 *
 * @author Administrator
 * @create 2020-03-04
 * @since 1.0.0
 */
@Component
public class ZkApi {

    private static final Logger logger = LoggerFactory.getLogger(ZkApi.class);

    @Autowired
    private ZooKeeper zkClient;


    /**
     * 判断指定节点是否存在
     *
     * @param path
     * @param needWatch 指定是否复用zookeeper中默认的Watcher
     * @return
     */
    public Stat exists(String path, boolean needWatch) {
        try {
            return zkClient.exists(path, needWatch);
        } catch (Exception e) {
            logger.error("【断指定节点是否存在异常】{},{}", path, e);
            return null;
        }
    }

    /**
     * 检测结点是否存在 并设置监听事件
     * 三种监听类型： 创建，删除，更新
     *
     * @param path
     * @param watcher 传入指定的监听类
     * @return
     */
    public Stat exists(String path, Watcher watcher) {
        try {
            return zkClient.exists(path, watcher);
        } catch (Exception e) {
            logger.error("【断指定节点是否存在异常】{},{}", path, e);
            return null;
        }
    }

    /**
     * 创建持久化节点
     *
     * @param path
     * @param data
     */
    public boolean createNode(String path, String data) {
        try {
            //ZooDefs.Ids.OPEN_ACL_UNSAFE 这个参数相当于对这个节点做权限控制
            //CreateMode四种节点类型，短暂的，短暂带序号的，持久的，持久带序号的
            String ss = zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(ss + ".......创建方法");
            return true;
        } catch (Exception e) {
            logger.error("【创建持久化节点异常】{},{},{}", path, data, e);
            return false;
        }
    }


    /**
     * 修改持久化节点
     *
     * @param path
     * @param data
     */
    public boolean updateNode(String path, String data) {
        try {
            //zk的数据版本是从0开始计数的。如果客户端传入的是-1，则表示zk服务器需要基于最新的数据进行更新。如果对zk的数据节点的更新操作没有原子性要求则可以使用-1.
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zkClient.setData(path, data.getBytes(), -1);
            return true;
        } catch (Exception e) {
            logger.error("【修改持久化节点异常】{},{},{}", path, data, e);
            return false;
        }
    }

    /**
     * 删除持久化节点
     *
     * @param path
     */
    public boolean deleteNode(String path) {
        try {
            //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查
            zkClient.delete(path, -1);
            System.out.println("删除方法");
            return true;
        } catch (Exception e) {
            logger.error("【删除持久化节点异常】{},{}", path, e);
            return false;
        }
    }

    /**
     * 获取当前节点的子节点(不包含孙子节点)
     *
     * @param path 父节点path
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> list = zkClient.getChildren(path, false);
        return list;
    }

    /**
     * 获取指定节点的值
     *
     * @param path
     * @return
     */
    public String getData(String path, Watcher watcher) {
        try {
            Stat stat = new Stat();
            byte[] bytes = zkClient.getData(path, watcher, stat);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 测试方法  初始化
     *
     * @PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
     */
    @PostConstruct
    public void init() {
        String path = "/zk-watcher-2";
        logger.info("【执行初始化测试方法。。。。。。。。。。。。】");
        createNode(path, "测试");
        String value = getData(path, new WatcherApi());
        logger.info("【执行初始化测试方法getData返回值。。。。。。。。。。。。】={}", value);

        // 删除节点出发 监听事件
        deleteNode(path);

    }
}