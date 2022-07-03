package com.mabo.dao;

import com.mabo.entity.Userbase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * (Userbase)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-29 20:18:16
 */
@Repository
public interface UserbaseDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Userbase queryById(@Param("id") String id);


    /**
     * 新增数据
     *
     * @param userbase 实例对象
     * @return 影响行数
     */
    int insert(Userbase userbase);

    /**
     * 修改数据
     *
     * @param userbase 实例对象
     * @return 影响行数
     */
    int update(Userbase userbase);




}

