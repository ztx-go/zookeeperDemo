/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: TestRepository
 * Author:   Administrator
 * Date:     2020-01-16 14:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.example.zookeeperDemo.repository;

import com.example.zookeeperDemo.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2020-01-16
 * @since 1.0.0
 */
@Repository("TestRepository")
public interface TestRepository extends JpaRepository<TestEntity, String>,
        JpaSpecificationExecutor<TestEntity> {

    @Query(value = "select * from test t where t.name_=:userName",nativeQuery = true)
    List<TestEntity> findByName(@Param("userName") String userName);
}