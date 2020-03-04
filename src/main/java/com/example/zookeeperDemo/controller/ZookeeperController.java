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

import com.example.zookeeperDemo.configuration.zookeeper.WatcherApi;
import com.example.zookeeperDemo.configuration.zookeeper.ZkApi;
import com.example.zookeeperDemo.controller.model.ResponseCode;
import com.example.zookeeperDemo.controller.model.ResponseModel;
import io.swagger.annotations.ApiOperation;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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


}