package com.mabo.dao;

import com.mabo.entity.Login;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * (Login)表数据库访问层
 *
 * @author makejava
 * @since 2022-06-05 00:53:37
 */
@Repository
public interface LoginDao {
    /**
     * 通过ID查询单条数据
     *
     * @param uname 主键
     * @return 实例对象
     */
    Login queryById(String uname);

    List<Login> queryLogin(Login login);
    /**
     * 统计总行数
     *
     * @param login 查询条件
     * @return 总行数
     */
    long count(Login login);

    /**
     * 新增数据
     *
     * @param login 实例对象
     * @return 影响行数
     */
    int insert(Login login);


    /**
     * 修改数据
     *
     * @param login 实例对象
     * @return 影响行数
     */
    int update(Login login);

    /**
     * 通过主键删除数据
     *
     * @param uname 主键
     * @return 影响行数
     */
    int deleteById(String uname);

}

