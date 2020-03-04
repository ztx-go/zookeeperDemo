/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: TestController
 * Author:   Administrator
 * Date:     2019-12-23 16:18
 * Description: test
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.zookeeperDemo.controller;

import com.example.zookeeperDemo.controller.model.ResponseCode;
import com.example.zookeeperDemo.controller.model.ResponseModel;
import com.example.zookeeperDemo.entity.TestEntity;
import com.example.zookeeperDemo.service.TestService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈test〉
 *
 * @author Administrator
 * @create 2019-12-23
 * @since 1.0.0
 */
@RestController
public class TestController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(TestController.class);


    @Autowired
    TestService testService;

    @ApiOperation(value = "测试hello", notes = "111")
    @RequestMapping(value = "hello2", method = RequestMethod.GET)
    public ResponseModel test2() {
        try {
            logger.info("LOG");
            String data = "hello";
            TestEntity test1 = testService.findById("1");
            ResponseModel result =
                    new ResponseModel(new Date().getTime(), test1, ResponseCode._200, "");
            return result;
//            return this.buildHttpReslut();
        } catch (Exception e) {

            return this.buildHttpReslutForException(e);
        }

    }
}