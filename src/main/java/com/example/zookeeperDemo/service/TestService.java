/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TestService
 * Author:   Administrator
 * Date:     2020-01-16 14:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.zookeeperDemo.service;


import com.example.zookeeperDemo.entity.TestEntity;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-01-16
 * @since 1.0.0
 */
public interface TestService {

    TestEntity findById(String id);

    TestEntity findByName(String name);

}