/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TestServiceImpl
 * Author:   Administrator
 * Date:     2020-01-16 14:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.zookeeperDemo.service.internal;

import com.example.zookeeperDemo.entity.TestEntity;
import com.example.zookeeperDemo.repository.TestRepository;
import com.example.zookeeperDemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-01-16
 * @since 1.0.0
 */
@Service("testService")
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;

    @Override
    public TestEntity findById(String id) {
        TestEntity testEntity = testRepository.findById(id).get();
        return testEntity;
    }

    @Override
    public TestEntity findByName(String name){
        String s;
        List<TestEntity> testEntityList = testRepository.findByName(name);
        if(null!=testEntityList){
            return testEntityList.get(0);
        }
        return null;
    }
}