/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: ZookeeperController
 * Author:   Administrator
 * Date:     2020-03-04 11:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.zookeeperDemo.controller;

import com.example.zookeeperDemo.configuration.zookeeper.ZkApi;
import com.example.zookeeperDemo.controller.model.ResponseCode;
import com.example.zookeeperDemo.controller.model.ResponseModel;
import io.swagger.annotations.ApiOperation;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-03-04
 * @since 1.0.0
 */
@RestController
public class ZookeeperController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperController.class);

//    ZkApi zk = new ZkApi();

    @Autowired
    ZkApi zk;

    @ApiOperation(value = "create", notes = "111")
    @RequestMapping(value = "zk", method = RequestMethod.GET)
    public ResponseModel test2(String path, String data) {
        try {
            logger.info("LOG");

            zk.createNode(path, data);

//            String value=zk.getData(path,new WatcherApi());
//            logger.info("【执行初始化测试方法getData返回值。。。。。。。。。。。。】={}",value);

//            zk.deleteNode(path);

            ResponseModel result =
                    new ResponseModel(new Date().getTime(), "ddd", ResponseCode._200, "");
            return result;
        } catch (Exception e) {
            return this.buildHttpReslutForException(e);
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String test3(String path) throws Exception {

        zk.deleteNode(path);
        return "delete";
    }

    @RequestMapping(value = "getChild", method = RequestMethod.GET)
    public String test4(String path) throws Exception {

        List<String> children = zk.getChildren(path);
        for (String s : children) {

            System.out.println(s);
        }
        return "delete";
    }

    // 使用客户端进行分布式锁的实现
    private final Logger log = LoggerFactory.getLogger(ZookeeperController.class);
    @Autowired
    private CuratorFramework curatorFramework;
    /**
     * 临时节点名称
     */
    private static final String LOCK_NAME = "/lock";

    /**
     * 库存
     */
    private int kz = 5;

    /**
     * zookeeper分布式锁使用demo
     *
     * @param id
     * @return
     */
    @GetMapping("/add/{id}")
    public String add(@PathVariable String id) {

        String name = Thread.currentThread().getName();
        log.info("线程{}->开始进入add方法", name);
        InterProcessSemaphoreMutex mutex = new InterProcessSemaphoreMutex(curatorFramework, LOCK_NAME);
        log.info("线程{}->开始获取🔒", name);
        boolean acquire;
        try {
            acquire = mutex.acquire(6000, TimeUnit.SECONDS);
            if (acquire) {
                log.info("线程{}->获取🔒成功开始进行购买,剩余数量{}", name, kz);
                if (kz == 0) {
                    log.info("销售一空了~~~~~~~~~~~~~~~~~~~~~~~~");
                    return "销售一空";
                }
                kz--;
                Thread.sleep(1000);
                log.info("线程{}->购买完毕", name);
            }
        } catch (Exception e) {
            log.error("业务执行错误信息-->", e);
        } finally {
            log.info("线程{}->开始释放🔒", name);
            try {
                mutex.release();
                log.info("线程{}->释放🔒成功", name);
            } catch (Exception e) {
                log.error("释放🔒错误信息-->", e);
            }
        }
        return "SUCCESS";
    }

}